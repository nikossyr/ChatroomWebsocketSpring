package com.example.chatroom.pojo.stompsockettransfer;

public class OutputMessage extends Message {

    private String from;
    private String time;


    public OutputMessage(final String from, final String text, final String time) {
        this.from = from;
        setText(text);
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
