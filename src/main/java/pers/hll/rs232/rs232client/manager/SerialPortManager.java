package pers.hll.rs232.rs232client.manager;

import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;
import pers.hll.rs232.rs232client.listener.SerialPortListener;
import pers.hll.rs232.rs232client.parser.Parser;
import pers.hll.rs232.rs232client.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 串口管理器
 *
 * @author hll
 * @since 2023/12/06
 */
@Slf4j
public class SerialPortManager {

    private SerialPortManager() {
    }

    /**
     * 获得当前计算机所有的串口的名称列表
     *
     * @return 串口名称列表
     */
    public static List<String> findPorts() {
        // 获得当前所有可用串口
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        List<String> portNameList = new ArrayList<>();
        // 将可用串口名添加到List并返回该List
        for (SerialPort serialPort : serialPorts) {
            portNameList.add(serialPort.getSystemPortName());
        }
        // 去重
        portNameList = portNameList.stream().distinct().collect(Collectors.toList());
        return portNameList;
    }

    /**
     * 打开串口
     *
     * @param portName 端口名称
     * @param baudRate 波特率
     * @return 串口对象
     */
    public static SerialPort openPort(String portName, int baudRate) {
        SerialPort serialPort = SerialPort.getCommPort(portName);
        if (baudRate > 0) {
            serialPort.setBaudRate(baudRate);
        }
        if (!serialPort.isOpen()) {
            serialPort.openPort(1000);
        } else {
            return serialPort;
        }
        // 设置串口的控制流，可以设置为disabled，或者CTS, RTS/CTS, DSR, DTR/DSR, Xon, Xoff, Xon/Xoff等
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        // 第一个参数为波特率，默认9600；
        // 第二个参数为每一位的大小，默认8，可以输入5到8之间的值；
        // 第三个参数为停止位大小，只接受内置常量，可以选择(ONE_STOP_BIT, ONE_POINT_FIVE_STOP_BITS, TWO_STOP_BITS)；
        // 第四位为校验位，同样只接受内置常量，可以选择 NO_PARITY, EVEN_PARITY, ODD_PARITY, MARK_PARITY,SPACE_PARITY。
        serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        // 超时设置
        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING,
                1000, 1000);
        return serialPort;
    }

    /**
     * 关闭串口
     *
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort 串口对象
     * @param content    待发送数据
     */
    public static void sendToPort(SerialPort serialPort, byte[] content) {
        if (!serialPort.isOpen()) {
            return;
        }
        serialPort.writeBytes(content, content.length);
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        byte[] resultData = null;
        try {
            if (!serialPort.isOpen()) {
                return new byte[0];
            }
            int i = 0;
            while (serialPort.bytesAvailable() > 0 && i++ < 5) {
                Thread.sleep(20);
            }
            byte[] readBuffer = new byte[serialPort.bytesAvailable()];
            int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
            if (numRead > 0) {
                resultData = readBuffer;
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException: ", e);
            Thread.currentThread().interrupt();
        }
        return resultData;
    }

    /**
     * 添加监听器
     *
     * @param serialPort 串口对象
     * @param processor  数据帧解析器
     */
    public static <T> void addListener(SerialPort serialPort, Parser<T> parser, Processor<T> processor) {
        try {
            serialPort.addDataListener(new SerialPortListener<>(serialPort, parser, processor));
        } catch (Exception e) {
            log.error("给串口添加监听器失败: ", e);
        }
    }
}