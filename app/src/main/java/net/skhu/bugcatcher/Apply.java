package net.skhu.bugcatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Apply implements Serializable {

    String email;
    String content;
    String size;
    String reward;

    double latitude;
    double longitude;
    String address1;
    String address2;
    int state; // 0 : 대기  1: 매칭완료
    int maxreward;
    HashMap<String,String> catcher=new HashMap<>();
    String match;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public HashMap<String, String> getCatcher() {
        return catcher;
    }

    public void setCatcher(HashMap<String, String> catcherlist) {
        this.catcher = catcherlist;
    }


    public Apply(String email, String content, String size, String reward,int maxreward,double latitude,double longitude,String address1,String address2,int state){
        this.email=email;
        this.content=content;
        this.size=size;
        this.reward=reward;

        this.state=state;
        this.maxreward=maxreward;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address1=address1;
        this.address2=address2;
        this.match=null;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getMaxreward() {
        return maxreward;
    }
    public void setMaxreward(int maxreward) {
        this.maxreward = maxreward;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

}
