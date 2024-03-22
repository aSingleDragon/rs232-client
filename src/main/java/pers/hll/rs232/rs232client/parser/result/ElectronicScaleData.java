package pers.hll.rs232.rs232client.parser.result;

import lombok.Builder;
import lombok.Data;
import pers.hll.rs232.rs232client.parser.result.enums.electronic.scale.UnitInWeightModeEnum;
import pers.hll.rs232.rs232client.parser.result.enums.electronic.scale.WorkMode;
import pers.hll.rs232.rs232client.utils.StringUtil;

import java.math.BigDecimal;

/**
 * 电子秤解析结果
 *
 * @author hll
 * @since 2023/12/08
 */
@Data
@Builder
public class ElectronicScaleData {

    /**
     * 原始数据
     */
    private byte[] data;

    /**
     * 小数位数
     */
    private int decimals;

    /**
     * 工作模式
     */
    private WorkMode workMode;

    /**
     * 重量是否为负
     */
    private boolean isNegative;

    /**
     * 重量是否稳定
     */
    private boolean isStable;

    /**
     * 重量是否溢出 (电子秤称量范围[20克, 30千克])
     */
    private boolean isOverFlow;

    /**
     * 计重模式({@link WorkMode#WEIGHT})下的单位
     */
    private UnitInWeightModeEnum unitInWeightMode;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 重量的bcd值
     * 第3帧 0-7 BCD1(数值的最低字节)
     * 第4帧 0-7 BCD2(数值的中间字节)
     * 0-7 BCD3(数值的最高字节)
     */
    private int bcdResult;

    /**
     * 数据的简单描述，用于向暂存区输出
     *
     * @return 简单描述
     */
    public String toSimpleDescription() {
        String unit;
        switch (workMode) {
            case WEIGHT:
                unit = unitInWeightMode == UnitInWeightModeEnum.KILOGRAM
                        ? UnitInWeightModeEnum.KILOGRAM.getDescription()
                        : UnitInWeightModeEnum.POUND.getDescription();
                break;
            case COUNTING:
                unit = "个";
                break;
            case PERCENTAGE:
                unit = "%";
                break;
            default:
                unit = "";
        }
        String warningInfo = "";
        if (isOverFlow || !isStable) {
            warningInfo = "警告: " +
                    (isOverFlow ? "重量溢出" : StringUtil.EMPTY) +
                    (!isStable ? "重量不稳定" : StringUtil.EMPTY);
        }
        return weight + " " + unit + " " + warningInfo;
    }
}
