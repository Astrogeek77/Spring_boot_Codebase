package com.chat.app.model;

public class ChatMessage {
 private String sender;
 private String content;
 private String timestamp;

 // 1. Default Constructor (Required by Jackson)
 public ChatMessage() {
 }

 // 2. Constructor with fields (Optional, but useful)
 public ChatMessage(String sender, String content, String timestamp) {
  this.sender = sender;
  this.content = content;
  this.timestamp = timestamp;
 }

 // 3. Getters and Setters
 public String getSender() {
  return sender;
 }

 public void setSender(String sender) {
  this.sender = sender;
 }

 public String getContent() {
  return content;
 }

 public void setContent(String content) {
  this.content = content;
 }

 public String getTimestamp() {
  return timestamp;
 }

 public void setTimestamp(String timestamp) {
  this.timestamp = timestamp;
 }

 // Optional: toString for easier debugging in server logs
 @Override
 public String toString() {
  return "ChatMessage{" +
          "sender='" + sender + '\'' +
          ", content='" + content + '\'' +
          ", timestamp='" + timestamp + '\'' +
          '}';
 }
}