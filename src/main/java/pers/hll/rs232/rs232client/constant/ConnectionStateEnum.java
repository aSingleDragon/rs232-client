package pers.hll.rs232.rs232client.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 连接状态枚举
 *
 * @author hll
 * @since 2023/10/25
 */
@Getter
@RequiredArgsConstructor
public enum ConnectionStateEnum {

    /**
     * 未连接
     */
    NOT_CONNECTED("未连接", "-fx-text-fill: gray;"),
    /**
     * 已断开
     */
    DISCONNECTED("已断开", "-fx-text-fill: gray;"),
    /**
     * 已连接
     */
    CONNECTED("已连接", "-fx-text-fill: green;"),
    /**
     * 连接中
     */
    CONNECTING("连接中", "-fx-text-fill: yellow;"),
    /**
     * 连接失败
     */
    CONNECT_FAIL("连接失败", "-fx-text-fill: red;");

    /**
     * 描述
     */
    private final String description;

    /**
     * 字体颜色
     */
    private final String textColor;
}
