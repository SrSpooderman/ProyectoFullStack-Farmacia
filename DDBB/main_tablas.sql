CREATE DATABASE farmacia;

USE farmacia;

-- Doctor
CREATE TABLE doctor (
    mail VARCHAR(50) PRIMARY KEY,
    pass VARCHAR(100),
    name VARCHAR(100),
    last_log DATE,
    session INT(10)
);

-- Paciente
CREATE TABLE patient (
    mail VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100)
);

-- Medicina
CREATE TABLE medicine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    tmax FLOAT(50),
    tmin FLOAT(50)
);

-- Xip
CREATE TABLE xip (
    id INT(10) PRIMARY KEY,
    doctor_mail VARCHAR(50),
    id_medicine INT,
    id_patient VARCHAR(50),
    date DATE,
    FOREIGN KEY (doctor_mail) REFERENCES doctor(mail),
    FOREIGN KEY (id_medicine) REFERENCES medicine(id),
    FOREIGN KEY (id_patient) REFERENCES patient(mail)
);
