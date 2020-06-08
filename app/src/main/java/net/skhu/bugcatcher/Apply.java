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
    HashMap<String,String> catcherlist=new HashMap<>();

    public HashMap<String, String> getCatcherlist() {
        return catcherlist;
    }

    public void setCatcherlist(HashMap<String, String> catcherlist) {
        this.catcherlist = catcherlist;
    }

    public Apply(){

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
