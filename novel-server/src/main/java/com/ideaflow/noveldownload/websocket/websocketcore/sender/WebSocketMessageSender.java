package com.ideaflow.noveldownload.websocket.websocketcore.sender;


import cn.hutool.json.JSONUtil;

/**
 * WebSocket 消息的发送器接口
 *
 */
public interface WebSocketMessageSender {

    /**
     * 发送消息给指定用户
     *
     * @param userType 用户类型
     * @param userId 用户编号
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    void send(Integer userType, Long userId, String messageType, String messageContent);

    /**
     * 发送消息给指定用户类型
     *
     * @param userType 用户类型
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    void send(Integer userType, String messageType, String messageContent);

    /**
     * 发送消息给指定 Session
     *
     * @param sessionId Session 编号
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    void send(String sessionId, String messageType, String messageContent);

    default void sendObject(Integer userType, Long userId, String messageType, Object messageContent) {
        send(userType, userId, messageType, JSONUtil.toJsonStr(messageContent));
    }

    default void sendObject(Integer userType, String messageType, Object messageContent) {
        send(userType, messageType, JSONUtil.toJsonStr(messageContent));
    }

    default void sendObject(String sessionId, String messageType, Object messageContent) {
        send(sessionId, messageType, JSONUtil.toJsonStr(messageContent));
    }

}
