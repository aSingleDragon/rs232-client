package pers.hll.rs232.rs232client.constant;

/**
 * 全局常量
 *
 * @author hll
 * @since 2023/10/25
 */
public interface GlobalConstant {

    /**
     * 打印机数据文件路径
     */
    String PRINTER_FILE_PATH = "data/printer.xml";

    /**
     * 打印机数据文件root节点名称
     */
    String PRINTER_LIST_NODE_NAME = "PrinterList";

    /**
     * 打印机数据文件打印机节点名称
     */
    String PRINTER_NODE_NAME = "Pinter";

    /**
     * 打印机数据文件打印机仓库编码节点名称
     */
    String PRINTER_WAREHOUSE_CODE_NODE_NAME = "warehouseCode";

    /**
     * 打印机数据文件打印机业务编码节点名称
     */
    String PRINTER_BIZ_CODE_NODE_NAME = "bizCode";

    /**
     * 打印机数据文件打印机编码节点名称
     */
    String PRINTER_NO_NODE_NAME = "printerNo";

    /**
     * 打印机数据文件打印机名称节点名称
     */
    String PRINTER_NAME_NODE_NAME = "printerName";

    /**
     * 连接地址数据文件路径
     */
    String WS_ADDRESS_FILE_PATH = "data/wsAddress.xml";

    /**
     * 连接地址数据文件root节点名称
     */
    String WS_ADDRESS_LIST_NODE_NAME = "AddressList";

    /**
     * 连接地址数据文件节点名称
     */
    String WS_ADDRESS_NODE_NAME = "Address";

    /**
     * 连接地址数据文件地址名称节点名称
     */
    String WS_ADDRESS_NAME_NODE_NAME = "name";

    /**
     * 连接地址数据文件地址主机节点名称
     */
    String WS_ADDRESS_URL_NODE_NAME = "url";

    /**
     * 应用标题
     */
    String APPLICATION_TITLE = "益海顺丰小助手";

    /**
     * 应用icon图标地址
     */
    String APPLICATION_ICON_FILE_PATH = "static/images/printer.png";

    /**
     * 启动应用时是否自动连接属性key
     */
    String AUTO_CONNECT = "autoConnect";

    /**
     * 三方打印组件数据文件路径
     */
    String TPC_FILE_PATH = "data/third-party-component.xml";

    /**
     * 三方打印组件root节点名称
     */
    String TPC_LIST_NODE_NAME = "ThirdPrintComponentList";

    /**
     * 三方打印组件节点名称
     */
    String TPC_NODE_NAME = "ThirdPrintComponent";

    /**
     * 三方打印组件平台节点名称
     */
    String TPC_PLATFORM_NODE_NAME = "platformNameAndCode";

    /**
     * 三方打印组件地址节点名称
     */
    String TPC_HOST_NODE_NAME = "host";

    /**
     * 三方打印组件端口节点名称
     */
    String TPC_PORT_NODE_NAME = "port";

    /**
     * 三方打印组件状态节点名称
     */
    String TPC_STATUS_NODE_NAME = "status";
}
