package pers.hll.rs232.rs232client.exception;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * 串口(RS232)数据帧解析异常
 *
 * @author hll
 * @since 2023/12/06
 */
@Slf4j
public class ParseException extends Exception {

    public ParseException() {
        super();
    }

    public ParseException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
    public ParseException(Throwable cause) {
        super(cause);
    }
}
