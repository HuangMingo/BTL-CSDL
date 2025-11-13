package model;

public class Doctor {
    private String maBacSi, hoTen, soDienThoai;
    private int soNamKinhNghiem;
    private Khoa chuyenKhoa;
    public Doctor(String maBacSi, String hoTen, String soDienThoai, int soNamKinhNghiem, Khoa chuyenKhoa){
        this.maBacSi = maBacSi;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.soNamKinhNghiem = soNamKinhNghiem;
        this.chuyenKhoa = chuyenKhoa;
    }
    public Doctor(){

    }
//        ---------------------Getter-----------------
    public Khoa getChuyenKhoa() {
        return chuyenKhoa;
    }



    public String getMaBacSi() {
        return maBacSi;
    }

    public String getSo_dien_thoai() {
        return soDienThoai;
    }

    public Khoa getChuyen_khoa() {
        return chuyenKhoa;
    }

    public int getSoNamKinhNghiem() {
        return soNamKinhNghiem;
    }

    public String getHo_ten() {
        return hoTen;
    }
//        ----------------Setter--------------------
    public void setChuyenKhoa(Khoa chuyenKhoa) {
        this.chuyenKhoa = chuyenKhoa;
    }

    public void setMaBacSi(String maBacSi) {
        this.maBacSi = maBacSi;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setSoNamKinhNghiem(int soNamKinhNghiem) {
        this.soNamKinhNghiem = soNamKinhNghiem;
    }
}
