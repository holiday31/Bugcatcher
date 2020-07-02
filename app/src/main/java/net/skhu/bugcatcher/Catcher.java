package net.skhu.bugcatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Catcher implements Serializable {
    String phone;
    String name;
    double distance;
    float score;
    int reviewcount;
    HashMap<String,String> apply=new HashMap<>();


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Catcher(String phone, String name, double distance, float score, int reviewcount) {
        this.name = name;
        this.distance = distance;
        this.score = score;
        this.reviewcount = reviewcount;
        this.phone=phone;
    }

    public HashMap<String, String> getApply() {
        return apply;
    }

    public void setApply(HashMap<String, String> apply) {
        this.apply = apply;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getReviewcount() {
        return reviewcount;
    }

    public void setReviewcount(int reviewcount) {
        this.reviewcount = reviewcount;
    }

    @Override
    public String toString(){
        String s="CatcherInfo name: "+name+" ,distance: "+distance+" ,score: "+score+" ,reviewcount:"+reviewcount;
        return s;
    }
}
