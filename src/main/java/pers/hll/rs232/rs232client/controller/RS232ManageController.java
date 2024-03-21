package pers.hll.rs232.rs232client.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import pers.hll.rs232.rs232client.manager.SerialPortManager;
import pers.hll.rs232.rs232client.constant.ConnectionStateEnum;
import pers.hll.rs232.rs232client.parser.impl.ElectronicScaleParser;
import pers.hll.rs232.rs232client.processor.impl.ElectronicScaleProcessor;
import pers.hll.rs232.rs232client.utils.ViewUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 串口通信管理Tab页签控制器
 *
 * @author hll
 * @since 2023/10/25
 */
@Slf4j
public class RS232ManageController implements Initializable {

    /**
     * 串口名称选择框
     */
    @FXML
    private ChoiceBox<String> serialPortNameChoiceBox;

    /**
     * 刷新本机当前串口信息按钮
     */
    @FXML
    private Button refreshSerialPortInfoButton;

    /**
     * 波特率复合框
     */
    @FXML
    private ComboBox<String> baudRateComboBox;

    /**
     * 连接按钮
     */
    @FXML
    private Button connectButton;

    /**
     * 断开按钮
     */
    @FXML
    private Button disconnectButton;

    /**
     * 串口连接状态标签
     */
    @FXML
    private Label connectStateLabel;

    /**
     * 清除按钮 点击清除暂存区数据
     */
    @FXML
    private Button clearButton;

    /**
     * 日志按钮 点击查看当天日志
     */
    @FXML
    private Button logButton;

    /**
     * 接收数据暂存区文本域
     */
    @FXML
    private TextArea dataTextArea;

    /**
     * 串口对象
     */
    private SerialPort serialPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeSerialPortNameChoiceBox();

        setRefreshSerialPortInfoButtonOnActionEventHandler();

        initializeBaudRateComboBox();

        // 初始化串口连接状态
        connectStateLabel.setText(ConnectionStateEnum.NOT_CONNECTED.getDescription());

        setConnectButtonEventHandlerOnAction();

        setDisconnectButtonOnActionEventHandler();

        // 当点击清除按钮时 清空数据暂存区的数据
        clearButton.setOnAction(event -> dataTextArea.clear());

        setLogButtonOnActionEventHandler();
    }

    /**
     * 初始化复合框: 过滤输入内容和设置默认值
     */
    private void initializeBaudRateComboBox() {
        // 初始化常用波特率列表
        baudRateComboBox.getItems().addAll("9600", "4800", "2400", "1200");
        // 设置波特率默认值
        baudRateComboBox.setValue("9600");
        // 波特率 不接受非数值输入
        baudRateComboBox.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> {
            // 获取键入的字符
            String character = event.getCharacter();
            // 如果不是数值或删除 则消耗这个事件
            if (!(character.matches("\\d") || character.isEmpty())) {
                ViewUtils.alertError("只能输入数字(0-9)!");
                event.consume();
            }
        });
    }


    /**
     * 初始化串口名称选择框: 设置选择列表和默认值
     */
    private void initializeSerialPortNameChoiceBox() {
        // 初始化当前计算机串口信息
        serialPortNameChoiceBox.getItems().addAll(SerialPortManager.findPorts());
        if (!serialPortNameChoiceBox.getItems().isEmpty()) {
            serialPortNameChoiceBox.setValue(serialPortNameChoiceBox.getItems().get(0));
        }
    }

    /**
     * 设置连接按钮被点击时的事件处理器: 连接串口，更新连接状态
     */
    private void setConnectButtonEventHandlerOnAction() {
        connectButton.setOnAction(event -> {
            connectStateLabel.setText(ConnectionStateEnum.CONNECTING.getDescription());
            connectStateLabel.setStyle(ConnectionStateEnum.CONNECTING.getTextColor());
            String serialPortName = serialPortNameChoiceBox.getValue();
            String baudRate = baudRateComboBox.getValue();
            try {
                SerialPortManager.closePort(serialPort);
                serialPort = SerialPortManager.openPort(serialPortName, Integer.parseInt(baudRate));
                SerialPortManager.addListener(
                        serialPort, new ElectronicScaleParser(), new ElectronicScaleProcessor(dataTextArea));
                connectStateLabel.setText(ConnectionStateEnum.CONNECTED.getDescription());
                connectStateLabel.setStyle(ConnectionStateEnum.CONNECTED.getTextColor());
            } catch (Exception e) {
                log.error("串口名称: {}, 波特率: {}, 连接失败:", serialPortName, baudRate, e);
                connectStateLabel.setText(ConnectionStateEnum.CONNECT_FAIL.getDescription());
                connectStateLabel.setStyle(ConnectionStateEnum.CONNECT_FAIL.getTextColor());
            }
        });
    }

    /**
     * 设置断开连接按钮点击时的事件处理器: 断开串口连接
     */
    private void setDisconnectButtonOnActionEventHandler() {
        disconnectButton.setOnAction(event -> {
            SerialPortManager.closePort(serialPort);
            connectStateLabel.setText(ConnectionStateEnum.DISCONNECTED.getDescription());
            connectStateLabel.setStyle(ConnectionStateEnum.DISCONNECTED.getTextColor());
        });
    }

    /**
     * 设置日志按钮被点击时的事件处理器: 打开最新的日志文件
     */
    private void setLogButtonOnActionEventHandler() {
        logButton.setOnAction(event -> {
            String logFilePath = System.getProperty("user.dir") + "/logs";
            List<File> logFiles = FileUtil.loopFiles(logFilePath);
            if (CollUtil.isEmpty(logFiles)) {
                ViewUtils.alertError("未找到日志文件");
                return;
            }
            File recentlyModifiedFile = logFiles
                    .stream()
                    .max(Comparator.comparing(File::lastModified))
                    .orElse(null);
            try {
                Desktop.getDesktop().open(recentlyModifiedFile);
            } catch (IOException e) {
                log.error("日志文件打开失败:{}", FileUtil.file(logFilePath).getAbsolutePath(), e);
            }
        });
    }

    /**
     * 设置刷新串口信息按钮被点击时的事件处理器: 重新获取本机当前串口信息
     */
    private void setRefreshSerialPortInfoButtonOnActionEventHandler() {
        refreshSerialPortInfoButton.setOnAction(event -> {
            ObservableList<String> items = serialPortNameChoiceBox.getItems();
            items.clear();
            items.addAll(SerialPortManager.findPorts());
        });
    }
}
