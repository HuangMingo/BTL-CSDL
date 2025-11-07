-- Khoa
CREATE TABLE Khoa (
    MaKhoa CHAR(5) PRIMARY KEY,
    TenKhoa VARCHAR(100)
);
-- Phòng
CREATE TABLE Phong (
    MaPhong CHAR(5) PRIMARY KEY,
    TenPhong VARCHAR(100),
    MaKhoa CHAR(5),
    FOREIGN KEY (MaKhoa) REFERENCES Khoa(MaKhoa)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Bác sĩ
CREATE TABLE BacSi (
    MaBacSi CHAR(5) PRIMARY KEY,
    HoVaTen VARCHAR(100),
    SoNamKinhNghiem INT,
    SoDienThoai CHAR(10), 
    MaKhoa CHAR(5),
    FOREIGN KEY (MaKhoa) REFERENCES Khoa(MaKhoa)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Bệnh nhân
CREATE TABLE BenhNhan (
    MaBenhNhan CHAR(5) PRIMARY KEY,
    CCCD CHAR(12),
    HoVaTen VARCHAR(100),
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    DiaChi VARCHAR(255),
    SoDienThoai CHAR(10),
    SoDienThoaiNGH CHAR(10)
);
-- Lịch làm việc 
CREATE TABLE LichLamViec (
    MaBacSi CHAR(5),
    MaPhong CHAR(5),
    NgayLamViec DATE,
    KhungGio VARCHAR(50),
    PRIMARY KEY (MaBacSi, MaPhong, NgayLamViec, KhungGio),
    FOREIGN KEY (MaBacSi) REFERENCES BacSi(MaBacSi)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Lịch sử khám 
CREATE TABLE LichSuKham (
    MaKham CHAR(5) PRIMARY KEY,
    MaBenhNhan CHAR(5),
    MaBacSi CHAR(5),
    NgayKham DATE,
    ChanDoan TEXT,
    FOREIGN KEY (MaBenhNhan) REFERENCES BenhNhan(MaBenhNhan)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (MaBacSi) REFERENCES BacSi(MaBacSi)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Xét nghiệm
CREATE TABLE XetNghiem (
    MaXetNghiem CHAR(5) PRIMARY KEY,
    TenXetNghiem VARCHAR(100),
    KetQuaXetNghiem TEXT,
    ChiPhi DECIMAL(10,2),
    MaKham CHAR(5),
    FOREIGN KEY (MaKham) REFERENCES LichSuKham(MaKham)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Thuốc
CREATE TABLE Thuoc (
    MaThuoc CHAR(5) PRIMARY KEY,
    TenThuoc VARCHAR(100),
    NhaSanXuat VARCHAR(100),
    ChiPhi DECIMAL(10,2)
);
-- Đơn thuốc
CREATE TABLE DonThuoc (
    MaDon CHAR(5) PRIMARY KEY,
    MaKham CHAR(5),
    NgayKeDon DATE,
    FOREIGN KEY (MaKham) REFERENCES LichSuKham(MaKham)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
-- Chi tiết đơn thuốc
CREATE TABLE ChiTietDonThuoc (
    MaDon CHAR(5),
    MaThuoc CHAR(5),
    LieuLuong VARCHAR(100),
    CachDung VARCHAR(100),
    PRIMARY KEY (MaDon, MaThuoc),
    FOREIGN KEY (MaDon) REFERENCES DonThuoc(MaDon)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
