package net.skhu.bugcatcher;

public class Apply {
    String email;
    String content;
    String size;
    String reward;
    //double latitude;
    //double longitude;
    int state; // 0 : 대기  1: 매칭완료
    int maxreward;

    public int getMaxreward() {
        return maxreward;
    }

    public void setMaxreward(int maxreward) {
        this.maxreward = maxreward;
    }

    Apply(String email, String content, String size, String reward,int maxreward){
        this.email=email;
        this.content=content;
        this.size=size;
        this.reward=reward;
        this.state=0;
        this.maxreward=maxreward;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
