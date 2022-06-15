package com.example.applog.services;

import com.example.applog.model.AppLog;
import com.example.applog.repository.AppLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppLogServiceImpl implements AppLogService {

    @Autowired
    AppLogRepository appLogRepository;

    @Override
    public AppLog saveOrUpdate(AppLog appLog) {
        if(appLog.getId() != null){
            AppLog appLogData = appLogRepository.getOne(appLog.getId());
            if(appLogData == null) {
                return null;
            }
            appLog.setTime(System.currentTimeMillis());
            return appLogRepository.save(appLogData);
        }
        return appLogRepository.save(appLog);
    }

    @Override
    public AppLog findById(Long id) {
        Optional<AppLog> appLogOptional = appLogRepository.findById(id);
        return appLogOptional.orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        appLogRepository.deleteById(id);
    }
}
