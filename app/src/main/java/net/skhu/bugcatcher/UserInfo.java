package net.skhu.bugcatcher;

public class UserInfo {
    public String email;
    public String name;
    public String phone;
    public String sex;

    public UserInfo(String email,String name, String phone, String sex) {
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.sex=sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
