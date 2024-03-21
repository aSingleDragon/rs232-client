package pers.hll.rs232.rs232client.exception;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * 不支持的鼠标按键异常
 *
 * @author hll
 * @since 2023/12/06
 */
@Slf4j
public class UnSupportedKeyException extends RuntimeException {

    public UnSupportedKeyException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
    }
}
