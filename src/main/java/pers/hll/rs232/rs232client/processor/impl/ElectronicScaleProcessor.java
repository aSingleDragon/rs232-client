package pers.hll.rs232.rs232client.processor.impl;

import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import pers.hll.rs232.rs232client.exception.UnSupportedKeyException;
import pers.hll.rs232.rs232client.parser.result.ElectronicScaleData;
import pers.hll.rs232.rs232client.processor.Processor;
import pers.hll.rs232.rs232client.utils.LocalDateTimeUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

/**
 * 电子秤/天平 RS232 串口数据帧 解析结果处理器
 *
 * @author hll
 * @since 2023/12/06
 */
@Slf4j
public class ElectronicScaleProcessor implements Processor<ElectronicScaleData> {

    private final Robot robot;

    private final TextArea dataTextArea;

    public ElectronicScaleProcessor(TextArea dataTextArea) throws AWTException {
        super();
        this.robot = new Robot();
        this.dataTextArea = dataTextArea;
    }

    @Override
    public void process(ElectronicScaleData result) {
        log.info("电子秤解析结果: {}", result.toString());
        robot.delay(1000);
        // 将解析数据追加到数据暂存区
        String now = LocalDateTimeUtil.formatNormal(LocalDateTime.now());
        String dataText = now + ":  " + result.toSimpleDescription() + "\n";
        dataTextArea.appendText(dataText);
        // 模拟键盘输入 将解析的电子秤数据输入到输入框
        String weightText = result.getWeight().toString();
        for (char c : weightText.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new UnSupportedKeyException("不支持的键盘输入: {0}(Key Code:{1})", c, keyCode);
            }
            robot.keyPress(keyCode);
            robot.delay(100);
            robot.keyRelease(keyCode);
            robot.delay(100);
        }
    }
}
