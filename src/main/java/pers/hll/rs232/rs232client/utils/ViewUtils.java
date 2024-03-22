package pers.hll.rs232.rs232client.utils;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 打印服务连接状态显示
 *
 * @author hll
 * @since 2023/10/25
 */
public class ViewUtils {

    private ViewUtils() {}

    public static void alertError(String headerText, String contentText) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("系统错误");
        if (StringUtil.isNotEmpty(headerText)) {
            errorAlert.setHeaderText(headerText);
        }
        errorAlert.setContentText(contentText);
        errorAlert.show();
    }

    public static void alertError(String headerText) {
        alertError(headerText, null);
    }

    public static void alertInformation(String headerText) {
        alertInformation(headerText, null);
    }

    public static void alertInformation(String headerText, String contentText) {
        Alert errorAlert = new Alert(AlertType.INFORMATION);
        errorAlert.setTitle("提示");
        if (StringUtil.isNotEmpty(headerText)) {
            errorAlert.setHeaderText(headerText);
        }
        errorAlert.setContentText(contentText);
        errorAlert.show();
    }
}
