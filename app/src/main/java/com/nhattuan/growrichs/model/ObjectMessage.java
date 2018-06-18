package com.nhattuan.growrichs.model;

public class ObjectMessage {
    private String FullMessage;
    private int isOpen;
    private String linkURL;

    public ObjectMessage() {
    }

    public ObjectMessage(String fullMessage, String linkURL) {
        FullMessage = fullMessage;
        this.linkURL = linkURL;
    }

    public ObjectMessage(String fullMessage,  String linkURL, int isOpen) {
        FullMessage = fullMessage;
        this.isOpen = isOpen;
        this.linkURL = linkURL;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getFullMessage() {
        return FullMessage;
    }

    public void setFullMessage(String fullMessage) {
        FullMessage = fullMessage;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }
}
