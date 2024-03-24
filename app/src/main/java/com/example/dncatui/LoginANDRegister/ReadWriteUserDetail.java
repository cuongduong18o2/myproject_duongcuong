package com.example.dncatui.LoginANDRegister;

public class ReadWriteUserDetail {

   public String email,doB,phone,gender;
   private  int UserType;

   public  int getUserType(){
       return  UserType;
   }
   public  void SetUserType(int Usertype){this.UserType=Usertype;}

    public ReadWriteUserDetail(String email, String doB, String phone, String gender,int Usertype) {
        this.email = email;
        this.doB = doB;
        this.phone = phone;
        this.gender = gender;
        this.UserType=Usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public ReadWriteUserDetail(){}
}
