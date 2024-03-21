package pers.hll.rs232.rs232client.processor;

/**
 * 解析结果处理器接口
 *
 * @author hll
 * @since 2023/12/06
 */
public interface Processor<T> {

    /**
     * 处理解析结果
     *
     * @param result 解析结果
     */
    void process(T result);
}
