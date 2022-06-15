package com.example.applog.repository;

import com.example.applog.model.AppLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppLogRepository extends JpaRepository<AppLog, Long> {
}

