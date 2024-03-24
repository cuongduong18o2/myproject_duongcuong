package com.example.dncatui.Admin;

public class BacSi {
    private String Name,Email,Exp,Key,Img;

    public BacSi(String name, String email, String exp, String key,String img) {
        Name = name;
        Email = email;
        Exp = exp;
        Key = key;
        Img=img;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }


    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public BacSi() {
    }
}
