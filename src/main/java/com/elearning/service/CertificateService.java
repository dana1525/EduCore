package com.elearning.service;

import com.elearning.model.Certificate;
import com.elearning.model.Course;
import com.elearning.model.Student;
import com.elearning.repository.CertificateRepository;

import java.sql.SQLException;
import java.util.List;

public class CertificateService {
    private CertificateRepository certificateRepository = new CertificateRepository();

    public void issueCertificate(Student student, Course course) throws SQLException {
        if (student.getProgress() < 100.0) {
            System.out.println("Course not completed. Current progress: " + student.getProgress() + "%");
            return;
        }

        try {
            Certificate existing = certificateRepository.findByStudentAndCourse(student.getId(), course.getId());

            if (existing != null) {
                throw new IllegalArgumentException("Certificate already exists for " + student.getName());
            }
            Certificate certificate = new Certificate(0, course, student);
            Certificate savedCertificate = certificateRepository.save(certificate);
            System.out.println(savedCertificate);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listCertificatesById(Student student) {
        try {
            List<Certificate> certificates = certificateRepository.findByStudentId(student.getId());
            for (Certificate certificate : certificates) {
                System.out.println(certificate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
