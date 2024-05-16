package com.example.khachsan;

public class HoaDon {
        int Id;
        String HoTen;
        int SoPhong;
        int SoNgay;
        double DonGia;

    public HoaDon(int id, String hoTen, int soPhong, int soNgay, double donGia) {
        Id = id;
        HoTen = hoTen;
        SoPhong = soPhong;
        SoNgay = soNgay;
        DonGia = donGia;
    }

    public int getId() {
        return Id;
    }

    public String getHoTen() {
        return HoTen;
    }

    public int getSoPhong() {
        return SoPhong;
    }

    public int getSoNgay() {
        return SoNgay;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setSoPhong(int soPhong) {
        SoPhong = soPhong;
    }

    public void setSoNgay(int soNgay) {
        SoNgay = soNgay;
    }

    public void setDonGia(double donGia) {
        DonGia = donGia;
    }
}
