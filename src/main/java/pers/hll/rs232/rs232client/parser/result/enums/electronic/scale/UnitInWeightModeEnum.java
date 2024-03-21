package pers.hll.rs232.rs232client.parser.result.enums.electronic.scale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pers.hll.rs232.rs232client.exception.ParseException;

/**
 * 电子秤-计重模式({@link WorkMode#WEIGHT})下的单位
 * 第 6 帧 0 到 7 比特
 *
 * @author hll
 * @since 2023/12/08
 */
@Getter
@RequiredArgsConstructor
public enum UnitInWeightModeEnum {

    /**
     * 磅
     */
    POUND(1, "磅"),
    /**
     * 公斤
     */
    KILOGRAM(0, "公斤");

    /**
     * 单位编码
     */
    private final int code;

    /**
     * 单位描述
     */
    private final String description;

    public static UnitInWeightModeEnum of(int code) throws ParseException {
        for (UnitInWeightModeEnum unitInWeightModeEnum : UnitInWeightModeEnum.values()) {
            if (unitInWeightModeEnum.getCode() == code) {
                return unitInWeightModeEnum;
            }
        }
        throw new ParseException("重量单位解析异常:{0}", code);
    }
}
