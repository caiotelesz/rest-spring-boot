package br.com.caio.model;

public class FirstTime {

    private final long id;
    private final String content;

    public FirstTime(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}