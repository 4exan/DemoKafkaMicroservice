package ua.kusakabe.payload;

public class Like {
    private String username;
    private long post_id;

    public Like() {
    }

    public Like(String username, long post_id) {
        this.username = username;
        this.post_id = post_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }
}
