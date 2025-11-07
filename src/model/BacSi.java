package model;

public class BacSi {
    private String maBacSi, ho_ten, chuyen_khoa, so_dien_thoai;
    private int soNamKinhNghiem;
    public BacSi(String maBacSi, String ho_ten, String so_dien_thoai, int soNamKinhNghiem){
        this.maBacSi = maBacSi;
        this.ho_ten = ho_ten;
        this.so_dien_thoai = so_dien_thoai;
        this.soNamKinhNghiem = getSoNamKinhNghiem();
    }
    public BacSi(){

    }

    public String getMaBacSi() {
        return maBacSi;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public String getChuyen_khoa() {
        return chuyen_khoa;
    }

    public int getSoNamKinhNghiem() {
        return soNamKinhNghiem;
    }

    public String getHo_ten() {
        return ho_ten;
    }
}
