CREATE DATABASE QLBH
GO 
USE QLBH
GO
CREATE TABLE NHANVIEN(
MANV CHAR(10) PRIMARY KEY,
TENNV NVARCHAR(30) NOT NULL,
SDT NVARCHAR(15) NOT NULL,
EMAIL NVARCHAR(50) NOT NULL,
MATKHAU NVARCHAR(255) NOT NULL,
DIACHI NVARCHAR(255) NOT NULL,
NGAYSINH DATE,
GIOITINH BIT NOT NULL,
VAITRO BIT NOT NULL,
HINH NVARCHAR(50)
)
GO
CREATE TABLE KHACHHANG(
MAKH CHAR(10) PRIMARY KEY,
TENKH NVARCHAR(30) NOT NULL,
SDT NVARCHAR(15) NOT NULL,
DIACHI NVARCHAR(255),
GHICHU NVARCHAR(255)
)
GO 
CREATE TABLE NHACUNGCAP(
MANCC CHAR(10) PRIMARY KEY,
TENNCC NVARCHAR(30) NOT NULL,
DIACHI NVARCHAR(255) NOT NULL ,
SDT NVARCHAR(15) NOT NULL,
EMAIL NVARCHAR(50) NOT NULL
)
GO
CREATE TABLE LOAISANPHAM(
MALOAI CHAR(10) PRIMARY KEY,
TENLOAI NVARCHAR(30) NOT NULL
)
GO
CREATE TABLE SANPHAM(
MASP CHAR(10) PRIMARY KEY,
TENSP NVARCHAR(30) NOT NULL,
MALOAI CHAR(10),
GIABAN FLOAT NOT NULL,
SOLUONG INT NOT NULL,
DVTINH NVARCHAR(15) NOT NULL,
MANCC CHAR(10),
GHICHU NVARCHAR(255) NOT NULL,
HINH NVARCHAR(255) NOT NULL,
PTGIAMGIA FLOAT,
TIENGIAMGIA FLOAT,
TONGTIEN FLOAT,
FOREIGN KEY (MALOAI) REFERENCES LOAISANPHAM(MALOAI),
FOREIGN KEY (MANCC) REFERENCES NHACUNGCAP(MANCC)
)
GO
CREATE TABLE PHIEUNHAP(
MAPN CHAR(10) PRIMARY KEY,
NGAYNHAP DATE NOT NULL,
MANCC CHAR(10),
MANV CHAR(10),
FOREIGN KEY (MANCC) REFERENCES NHACUNGCAP(MANCC),
FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV)
)
GO
CREATE TABLE PHIEUNHAPCT(
MAPNCT CHAR(10) PRIMARY KEY,
MAPN CHAR(10),
MASP CHAR(10),
MALOAI CHAR(10),
DONGIANHAP FLOAT,
SOLUONG INT,
DVTINH NVARCHAR(20),
TONGTIEN FLOAT,
FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),
FOREIGN KEY (MAPN) REFERENCES PHIEUNHAP(MAPN),
FOREIGN KEY (MALOAI) REFERENCES LOAISANPHAM(MALOAI)
)
GO
CREATE TABLE HOADON(
MAHD INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
MANV CHAR(10),
MAKH CHAR(10),
NGAYBAN DATE DEFAULT GETDATE(),
TONGTIEN FLOAT,
FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV),
FOREIGN KEY (MAKH) REFERENCES KHACHHANG(MAKH)
)
GO
CREATE TABLE HOADONCT(
MAHDCT INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
MASP CHAR(10),
MALOAI CHAR(10),
GIABAN FLOAT,
SOLUONG INT,
DVTINH NVARCHAR(20),
PTGIAMGIA FLOAT,
TIENGIAMGIA FLOAT,
TONGTIEN FLOAT,
MAHD INT,
FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP),
FOREIGN KEY (MAHD) REFERENCES HOADON(MAHD),
FOREIGN KEY (MALOAI) REFERENCES LOAISANPHAM(MALOAI)
)
