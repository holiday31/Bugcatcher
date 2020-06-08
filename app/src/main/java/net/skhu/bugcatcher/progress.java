package net.skhu.bugcatcher;

public class progress {

    private String call_success;
    private String contact;
    private String catch_success;
    private String name;

    public String getCall_success() {
        return call_success;
    }


    public void setCall_success(String call_success) {
        this.call_success = call_success;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCatch_success() {
        return catch_success;
    }

    public void setCatch_success(String catch_success) {
        this.catch_success = catch_success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public progress(String call_success, String contact, String catch_success, String name) {
        this.call_success = call_success;
        this.contact = contact;
        this.catch_success = catch_success;
        this.name = name;
    }

}
