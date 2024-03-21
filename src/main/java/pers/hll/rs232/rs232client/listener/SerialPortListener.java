package pers.hll.rs232.rs232client.listener;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import lombok.extern.slf4j.Slf4j;
import pers.hll.rs232.rs232client.manager.SerialPortManager;
import pers.hll.rs232.rs232client.exception.ParseException;
import pers.hll.rs232.rs232client.parser.Parser;
import pers.hll.rs232.rs232client.processor.Processor;

/**
 * 串口监听
 *
 * @author hll
 * @since 2023/12/06
 */
@Slf4j
public class SerialPortListener<T> implements SerialPortDataListener {

    private final Parser<T> parser;

    private final Processor<T> parseProcessor;

    private final SerialPort serialPort;

    public SerialPortListener(SerialPort serialPort, Parser<T> parser, Processor<T> processor) {
        this.serialPort = serialPort;
        this.parser = parser;
        this.parseProcessor = processor;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (parser != null) {
            try {
                T parseResult = parser.parse(SerialPortManager.readFromPort(serialPort));
                log.info("解析结果:{}", parseResult.toString());
                parseProcessor.process(parseResult);
            } catch (ParseException e) {
                log.error("解析异常:", e);
            }
        }
    }
}
