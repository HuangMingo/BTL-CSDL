package model;

import java.sql.Date;

public class Patient {
    private String ma_benh_nhan, ho_ten, gioiTinh, cccd, so_dien_thoai, so_dien_thoai_ngh, diaChi;
    private Date ngaySinh;
     public Patient(String ma_benh_nhan, String ho_ten, Date ngaySinh, String gioiTinh, String cccd, String so_dien_thoai, String so_dien_thoai_ngh, String diaChi){
         this.ma_benh_nhan = ma_benh_nhan;
         this.ho_ten = ho_ten;
         this.ngaySinh = ngaySinh;
         this.gioiTinh = gioiTinh;
         this.cccd = cccd;
         this.so_dien_thoai = so_dien_thoai;
         this.so_dien_thoai_ngh = so_dien_thoai_ngh;
         this.diaChi = diaChi;
     }
     public Patient(){

     }

    public String getMa_benh_nhan() {
        return ma_benh_nhan;
    }
    public String getHo_ten() {
        return ho_ten;
    }
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setMa_benh_nhan(String ma_benh_nhan) {
        this.ma_benh_nhan = ma_benh_nhan;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public void setSo_dien_thoai_ngh(String so_dien_thoai_ngh) {
        this.so_dien_thoai_ngh = so_dien_thoai_ngh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public String getSo_dien_thoai_ngh() {
        return so_dien_thoai_ngh;
    }

    public String getDiaChi() {
        return diaChi;
    }
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s ", this.ma_benh_nhan, this.ho_ten,
            this.ngaySinh, this.gioiTinh, this.cccd, this.so_dien_thoai, this.so_dien_thoai_ngh, this.diaChi);
    }
}
