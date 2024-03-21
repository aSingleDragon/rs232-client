package pers.hll.rs232.rs232client.parser.impl;



import pers.hll.rs232.rs232client.exception.ParseException;
import pers.hll.rs232.rs232client.parser.ParseConstant;
import pers.hll.rs232.rs232client.parser.Parser;
import pers.hll.rs232.rs232client.parser.result.ElectronicScaleData;
import pers.hll.rs232.rs232client.parser.result.enums.electronic.scale.UnitInWeightModeEnum;
import pers.hll.rs232.rs232client.parser.result.enums.electronic.scale.WorkMode;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 电子秤串口(RS232)数据帧解析器
 * <p>数据帧格式请参考 ./images/电子天平-说明书.pdf
 *
 * @author hll
 * @since 2023/12/06
 */
public class ElectronicScaleParser implements Parser<ElectronicScaleData> {

    /**
     * 解析电子天平 RS232 串口数据帧
     *
     * @param data 数据帧
     * @return 重量
     */
    @Override
    public ElectronicScaleData parse(byte[] data) throws ParseException {

        // 校验数据帧长度及起始位
        check(data);

        // 第2帧
        int frame2 = data[1];

        // 0-2 小数位数
        int decimals = (frame2 & 0x07) - 1;
        if (decimals > 5) {
            throw new ParseException("小数位数解析异常:{0}", decimals);
        }

        // 3-4 工作模式 0-计重模式 1-计数模式 2-百分比模式
        WorkMode workMode = WorkMode.of((frame2 & 0x18) >> 3);

        // 5 符号位 1-重量为负 0-重量为正
        boolean isNegative = ((frame2 >> 5) & 1) == ParseConstant.TRUE;

        // 6 稳定位 1-重量稳定 0-重量不稳定
        boolean isStable = ((frame2 >> 6) & 1) == ParseConstant.TRUE;

        // 7 溢出位 1-重量溢出 0-重量未溢出
        boolean isOverFlow = frame2 < 0;

        // 第3帧 0-7 BCD1(数值的最低字节) 第4帧 0-7 BCD2(数值的中间字节) 第5帧 0-7 BCD3(数值的最高字节)
        int bcdResult = bcd(data[2], data[3], data[4]);

        // 第6帧 0-7 计重模式下的单位 1-磅 0-公斤
        UnitInWeightModeEnum unitInWeightMode = UnitInWeightModeEnum.of(data[5]);

        // 防止精度丢失
        BigDecimal weight = new BigDecimal(bcdResult)
                .divide(BigDecimal.valueOf(Math.pow(10, decimals)), decimals, RoundingMode.HALF_UP);

        return ElectronicScaleData
                .builder()
                .data(data)
                .decimals(decimals)
                .workMode(workMode)
                .isNegative(isNegative)
                .isStable(isStable)
                .isOverFlow(isOverFlow)
                .bcdResult(bcdResult)
                .unitInWeightMode(unitInWeightMode)
                .weight(isNegative ? weight.negate() : weight)
                .build();
    }

    /**
     * 计算单个bdc值
     * 103 => (0110 0111) => (6 7) => 6 * 10 + 7 = 67
     *
     * @param num 数
     * @return 值
     */
    private static int bcd(byte num) {
        return ((num >> 4) & 0x0F) * 10 + (num & 0x0F);
    }

    /**
     * 求从低位到高位不定数量bcd值的和
     *
     * @param bcdBytes 不定数量的bcd值
     * @return 和
     */
    private static int bcd(byte... bcdBytes) {
        int result = 0;
        int carry = 1;
        for (byte num : bcdBytes) {
            result += bcd(num) * carry;
            carry *= 100;
        }
        return result;
    }

    /**
     * 校验数据帧是否合法，不合法抛出异常
     *
     * @param data 数据帧
     */
    private static void check(byte[] data) {
        // 每次发送数据帧长度为6
        if (data.length != 6) {
            throw new IllegalArgumentException(String.format("数据帧长度为:%s, 不合法(正常应为6)", data.length));
        }
        // 第1帧 0-7 0xFF 起始位
        if ((data[0] & 0xFF) != 0xFF) {
            throw new IllegalArgumentException(String.format("数据帧起始位:%s, 不合法(正常应为OxFF)", data[0]));
        }
    }
}
