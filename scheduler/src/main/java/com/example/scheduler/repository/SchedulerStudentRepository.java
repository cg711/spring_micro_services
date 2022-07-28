package com.example.scheduler.repository;

import com.example.scheduler.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerStudentRepository extends JpaRepository<Student, Long> {
}
