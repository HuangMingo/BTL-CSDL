SELECT ct.MaThuoc, t.TenThuoc, ct.SoLuong, t.ChiPhi, t.chiphi * ct.SoLuong thanhtien
FROM donthuoc don
JOIN chitietdonthuoc ct
ON don.MaDon = ct.MaDon
JOIN thuoc t
ON t.MaThuoc = ct.MaThuoc
WHERE MaHoaDon = 'HD0001';

SELECT *
FROM chitietdonthuoc ct
JOIN thuoc t
ON  ct.MaThuoc = t.MaThuoc;

--           LAY HO TEN BENH NHAN
SELECT distinct p.HoVaTen
FROM lichsukham l
JOIN benhnhan p
ON l.MaBenhNhan = p.MaBenhNhan
WHERE l.MaKham IN(
	SELECT don.MaKham
	FROM donthuoc don
	WHERE don.MaHoaDon = 'HD0001'
)
