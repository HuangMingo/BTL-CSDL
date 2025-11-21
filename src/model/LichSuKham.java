package model;

import java.util.Date;
import java.util.Objects;

public class LichSuKham {
    private String maKham;
    private Patient benhNhan;
    private Doctor bacSi;
    private Date ngayKham;
    private String chanDoan;

    public LichSuKham() {
    }

    public LichSuKham(String maKham, Patient benhNhan, Doctor doctor, Date ngayKham, String chanDoan) {
        this.maKham = maKham;
        this.benhNhan = benhNhan;
        this.bacSi = doctor;
        this.ngayKham = ngayKham;
        this.chanDoan = chanDoan;
    }

    // Getter and Setter methods
    public String getMaKham() {
        return maKham;
    }

    public void setMaKham(String maKham) {
        this.maKham = maKham;
    }

    public Patient getBenhNhan() {
        return benhNhan;
    }

    public void setBacSi(Doctor bacSi) {
        this.bacSi = bacSi;
    }

    public void setBenhNhan(Patient benhNhan) {
        this.benhNhan = benhNhan;
    }

    public Doctor getBacSi() {
        return bacSi;
    }

    public Date getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(Date ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LichSuKham that = (LichSuKham) o;
        return Objects.equals(maKham, that.maKham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKham);
    }
}