package com.example.dncatui.Layout_NoMain;

public class LichKham {

String Ngay,Gio,TenBS,IdBN;

public  LichKham(){

}

    public LichKham(String ngay, String gio, String TenBS, String IdBN) {
        Ngay = ngay;
        Gio = gio;
        this.TenBS = TenBS;
        this.IdBN = IdBN;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }

    public String getTenBS() {
        return TenBS;
    }

    public void setTenBS(String tenBS) {
        this.TenBS = TenBS;
    }

    public String getIdBN() {
        return IdBN;
    }

    public void setIdBN(String iDUser) {
        this.IdBN = IdBN;
    }
}
