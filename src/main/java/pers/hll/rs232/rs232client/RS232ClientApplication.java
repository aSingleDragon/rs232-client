package pers.hll.rs232.rs232client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * RS232串口客户端
 *
 * @author hll
 * @since 2024/03/21
 */
public class RS232ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RS232ClientApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("RS232 Client");
        stage.setScene(scene);
        stage.setMinWidth(750.0);
        stage.setMinHeight(460.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}