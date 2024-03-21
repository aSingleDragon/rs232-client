package pers.hll.rs232.rs232client.parser.result.enums.electronic.scale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pers.hll.rs232.rs232client.exception.ParseException;

/**
 * 电子秤-工作模式
 * 第 2 帧 0 到 2 比特
 *
 * @author hll
 * @since 2023/12/08
 */
@Getter
@RequiredArgsConstructor
public enum WorkMode {

    /**
     * 计重模式
     */
    WEIGHT(0,"计重模式"),
    /**
     * 计数模式
     */
    COUNTING(1, "计数模式"),
    /**
     * 百分比模式
     */
    PERCENTAGE(2, "百分比模式");

    /**
     * 工作模式编码
     */
    private final int code;

    /**
     * 工作模式名称
     */
    private final String description;

    public static WorkMode of(int code) throws ParseException {
        for (WorkMode workMode : WorkMode.values()) {
            if (workMode.getCode() == code) {
                return workMode;
            }
        }
        throw new ParseException("工作模式解析异常:{0}", code);
    }
}
