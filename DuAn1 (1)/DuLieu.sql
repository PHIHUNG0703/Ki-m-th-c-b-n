USE QLBH
GO
INSERT INTO NHANVIEN(MANV,TENNV,SDT,EMAIL,MATKHAU,DIACHI,NGAYSINH,GIOITINH,VAITRO,HINH) values 
('NV001',N'Võ Khánh Toàn','0763898371','toanvkpc05433@fpt.edu.vn','12345678',N'Cần Thơ','07-01-2004',1,1,'avt1.png'),
('NV002',N'Phan Minh Sang','0785748662','sangpmpc05409@fpt.edu.vn','12345678',N'Cần Thơ','02-02-2004',1,0,'avt2.png'),
('NV003',N'Hà Thanh Nhẹ','0757873543','toanvkpc05433@fpt.edu.vn','12345678',N'Cần Thơ','06-11-2004',1,0,'avt3.png'),
('NV004',N'Trần Thiện Phát','0783472574','sangpmpc05409@fpt.edu.vn','12345678',N'Cần Thơ','11-03-2004',1,0,'avt4.png'),
('NV005',N'Võ Minh Thư','0761649425','thuvmpc05450@fpt.edu.vn','12345678',N'Cần Thơ','07-01-2004',0,0,'avt5.png'),
('NV006',N'Nguyễn Văn Kiệt','0769414246','kietnvpc05499@fpt.edu.vn','12345678',N'Cần Thơ','07-06-2004',1,0,'avt6.png'),
('NV007',N'Phan Quý Trọng','0762614247','trongpqpc0596@fpt.edu.vn','12345678',N'Cần Thơ','10-09-2004',1,0,'avt7.png'),
('NV008',N'Nguyễn Thái Nguyên','0729412458','nguyenntpc05697@fpt.edu.vn','12345678',N'Cần Thơ','11-05-2004',1,0,'avt8.png')
GO
INSERT INTO KHACHHANG(MAKH,TENKH,SDT,GHICHU) values 
('KH001',N'Trần Quốc Thịnh','0717483285',N'Khách Quen'),
('KH002',N'Đỗ Tuấn Khải','0772904607',N'Khách Quen'),
('KH003',N'Đổ Thị Yến','0707831783',N'Khách Quen'),
('KH004',N'Võ Hữu Dư','0774277048',N'Khách Quen'),
('KH005',N'Phạm Minh Thư','0778729783',N'Khách Quen'),
('KH006',N'Nguyễn Thảo Nguyên','0748846754',N'Khách Quen'),
('KH007',N'Nguyễn Trường Thịnh','0768721833',N'Khách Quen'),
('KH008',N'Đỗ Thanh Nam','0709817069',N'Khách Quen'),
('KH009',N'Đỗ Thanh Nam','0768721839',N'Khách Quen'),
('KH010',N'Đỗ Thanh Nam','0709817012',N'Khách Quen')
GO

INSERT INTO NHACUNGCAP(MANCC,TENNCC,DIACHI,SDT,EMAIL) values 
('NCC001',N'Công ty Acecook',N'Vĩnh Long','0763909112','acecook@gmail.com'),
('NCC002',N'Công ty Heineken N.V.',N'Thành Phố HCM','0737418016','heineken@gmail.com'),
('NCC003',N'Công Ty TNHH CJ Vina Agri',N'Long An','0764883681','vianagri@gmail.com'),
('NCC004',N'Công ty TNHH Emivest Feedmill VN',N'Cần Thơ','0765590056','emfee@gmail.com'),
('NCC005',N'Công ty CPTM Bách Hóa Xanh',N'Thành Phố HCM','0773460059','bhx@gmail.com'),
('NCC006',N'Công ty Langbiang Farm',N'Lâm Đồng','0760713057','langbiang@gmail.com'),
('NCC007',N'Công Ty Cổ Phần Dầu Ăn Tường An',N'Cần Thơ','0727484924','truongan@gmail.com'),
('NCC008',N'Công ty TNHH Suntory PepsiCo Việt Nam',N'Thành Phố HCM','0785154253','pepsico@gmail.com')
GO
INSERT INTO LOAISANPHAM(MALOAI,TENLOAI) values 
('LSP001',N'Thịt'),
('LSP002',N'Cá'),
('LSP003',N'Trứng'),
('LSP004',N'Hải Sản'),
('LSP005',N'Rau'),
('LSP006',N'Củ'),
('LSP007',N'Nấm'),
('LSP008',N'Trái cây'),
('LSP009',N'Mì'),
('LSP010',N'Hủ tiếu'),
('LSP011',N'Dầu ăn'),
('LSP012',N'Sữa'),
('LSP013',N'Nước tăng lực'),
('LSP014',N'Bia')
GO
INSERT INTO DVT(MADVT,TENDVT) values 
('DVT001',N'Lon'),
('DVT002',N'Kg'),
('DVT003',N'Gram'),
('DVT004',N'Hộp'),
('DVT005',N'Thùng'),
('DVT006',N'Lóc'),
('DVT007',N'Chai'),
('DVT008',N'Gói'),
('DVT009',N'Bịch')
GO

INSERT INTO SANPHAM(MASP,TENSP,MALOAI,SOLUONG,GIABAN,GIANHAP,MADVT,MANCC,HINH,PTGIAMGIA,TIENGIAM) values 
('SP001',N'Thùng mì Hảo Hảo','LSP009','1000','116000','110000','DVT005','NCC001','thungmihaohao.png','0','116000'),
('SP002',N'Thùng mì Thanh Long','LSP009','1000','150000','145000','DVT005','NCC002','thungmithanhlong.png','0','150000'),
('SP003',N'Thịt gà','LSP001','1000','49500','48000','DVT004','NCC006','thitga.png','0','49500'),
('SP004',N'Thịt heo','LSP001','1000','39000','38000','DVT004','NCC006','thitheo.png','0','153000'),
('SP005',N'Sữa cô gái Hà Lan','LSP012','1000','7200','6000','DVT009','NCC001','cogaihalan.png','0','7200'),
('SP006',N'Bia Tiger','LSP014','1000','17900','16000','DVT001','NCC004','biatiger.png','0','17900'),
('SP007',N'Gói mì Hảo Hảo','LSP009','1000','4400','4000','DVT008','NCC001','mihaohao.png','0','4400'),
('SP008',N'Red Bull','LSP013','1000','10000','9500','DVT001','NCC008','redbull.png','0','9500')
GO
INSERT INTO PHIEUNHAP(MAPN,NGAYNHAP,MANCC,MANV) VALUES
('pn01','2023-11-24','ncc01','nv01')
GO
INSERT INTO PHIEUNHAPCT(MAPNCT,MAPN,MASP,MALOAI,SOLUONG) VALUES
('pnct01','pn01','sp01','lsp01','125000','1','thung','125000')
GO
INSERT INTO HOADON(MAHD,MANV,MAKH,THANHTIEN) VALUES
('HD001','NV001','KH001','0')
GO
INSERT INTO HOADONCT(MAHDCT,MASP,MALOAI,GIABAN,SOLUONG,MADVT,PTGIAMGIA,THANHTIEN,MAHD) VALUES
('HDCT001','SP001','LSP001','116000','1','DVT005','0','116000','HD001')