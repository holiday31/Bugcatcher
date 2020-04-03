package net.skhu.bugcatcher;

public class Apply {
    String email;
    String content;
    String size;
    String reward;

    Apply(String email,String content, String size,String reward){
        this.email=email;
        this.content=content;
        this.size=size;
        this.reward=reward;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
