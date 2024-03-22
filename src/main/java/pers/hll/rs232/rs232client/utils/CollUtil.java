package pers.hll.rs232.rs232client.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author hll
 * @since 2024/03/21
 */
@UtilityClass
public class CollUtil {

    public boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
