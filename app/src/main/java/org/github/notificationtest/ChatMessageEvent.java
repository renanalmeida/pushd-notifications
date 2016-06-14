package org.github.notificationtest;

/**
 * Created by renan on 03/05/16.
 */
public class ChatMessageEvent {
    public String getMsg() {
        return msg;
    }

    private String msg;
    public ChatMessageEvent(String msg){
        this.msg = msg;
    }


}
