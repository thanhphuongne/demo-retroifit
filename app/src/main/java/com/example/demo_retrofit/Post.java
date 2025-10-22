package com.example.demo_retrofit;

public class Post {
    private int id;
    private int userId;
    private String title;
    private String body;

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getBody() { return body; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
}