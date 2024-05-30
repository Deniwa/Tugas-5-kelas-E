CREATE SCHEMA DB;

CREATE TABLE DB.MAHASISWA (
	NIM BIGINT NOT NULL PRIMARY KEY,
    NAMA VARCHAR(60),
    EMAIL VARCHAR(45),
    PRODI VARCHAR(45)
);

INSERT INTO DB.MAHASISWA
VALUES (235150407111066, 'Ridho Denindra Shobirin Cholil Soenarno', 'denindramigurd@student.ub.ac.id', 'Sistem Informasi'), 
(235150407111067, 'John Doe', 'John@student.ub.ac.id', 'Kedokteran');

SELECT *
FROM DB.MAHASISWA;

