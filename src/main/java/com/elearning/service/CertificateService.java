package com.elearning.service;

import com.elearning.model.Certificate;
import com.elearning.model.Course;
import com.elearning.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CertificateService {
    private List<Certificate> certificates = new ArrayList<>();
    private static int nextId;

    public void issueCertificate(Student student, Course course) {
        if (student.getProgress() < 100.0) {
            System.out.println("Course not completed. Current progress: " + student.getProgress() + "%");
            return;
        }

        boolean alreadyExists = false;
        for (Certificate c : certificates) {
            if (c.getStudent().getId() == student.getId() && c.getCourse().getId() == course.getId()) {
                alreadyExists = true;
                break;
            }
        }

        if(alreadyExists) {
//            System.out.println("Certificate already exists for " + student.getName());
//            return;
            throw new IllegalArgumentException("Certificate already exists for " + student.getName());
        }

        Certificate certificate = new Certificate(++nextId, course, student);
        certificates.add(certificate);
        System.out.println(certificate);
    }

    public void listCertificatesById(Student student) {
        for (Certificate certificate : certificates) {
            if (certificate.getStudent().getId() == student.getId()) {
                System.out.println(certificate);
            }
        }
    }
}
