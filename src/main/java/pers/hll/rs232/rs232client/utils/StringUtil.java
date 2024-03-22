package pers.hll.rs232.rs232client.utils;

import lombok.experimental.UtilityClass;

/**
 * 字符串工具类
 *
 * @author hll
 * @since 2024/03/21
 */
@UtilityClass
public class StringUtil {

    public final String EMPTY = "";

    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
