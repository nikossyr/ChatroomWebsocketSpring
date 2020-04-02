package com.example.chatroom.pojo;

import java.util.Objects;

public class OnlineMessage {

    private String username;
    private String connectionStatus;

    public OnlineMessage(String username, String connectionStatus) {
        this.username = username;
        this.connectionStatus = connectionStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnlineMessage)) return false;
        OnlineMessage that = (OnlineMessage) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, connectionStatus);
    }
}
