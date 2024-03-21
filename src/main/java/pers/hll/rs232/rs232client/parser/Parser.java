package pers.hll.rs232.rs232client.parser;

import pers.hll.rs232.rs232client.exception.ParseException;

/**
 * 数据帧解析接口
 *
 * @author hll
 * @since 2023/12/06
 */
public interface Parser<T> {

    /**
     * 解析数据帧
     *
     * @param data 数据帧
     * @return 返回解析结果
     */
    T parse(byte[] data) throws ParseException;
}
