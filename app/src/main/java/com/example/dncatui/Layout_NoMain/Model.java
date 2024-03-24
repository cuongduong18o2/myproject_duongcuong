package com.example.dncatui.Layout_NoMain;

public class Model {

    String name,Faculty,email,turl;

    public Model() {
    }

    public Model(String name, String faculty, String email, String turl) {
        this.name = name;
        Faculty = faculty;
        this.email = email;
        this.turl = turl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
