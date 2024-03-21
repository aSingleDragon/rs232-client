module pers.hll.rs.rs232client {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fazecast.jSerialComm;
    requires java.desktop;
    requires hutool.all;
    requires org.slf4j;

    opens pers.hll.rs232.rs232client to javafx.fxml;
    exports pers.hll.rs232.rs232client;
    exports pers.hll.rs232.rs232client.controller;
    opens pers.hll.rs232.rs232client.controller to javafx.fxml;
    exports pers.hll.rs232.rs232client.manager;
    opens pers.hll.rs232.rs232client.manager to javafx.fxml;
}