package com.example.applog.services;

import com.example.applog.exceptions.NotFoundException;
import com.example.applog.model.AppLog;
import com.example.applog.repository.AppLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppLogServiceImpl implements AppLogService {

    @Autowired
    AppLogRepository appLogRepository;

    /**
     * Updates or saves a Student object to the database.
     * @param appLog Student object to be updated or saved in the database.
     * @return Student object updated or saved in the database.
    */
    @Override
    public AppLog saveOrUpdate(AppLog appLog) throws NotFoundException {
        if(appLog.getId() != null){
            AppLog appLogData = findById(appLog.getId());
            if(appLogData != null) {
                appLogData.setMessage(appLog.getMessage());
                appLogData.setTime(appLog.getTime());
            }
        }
        return appLogRepository.save(appLog);
    }

    /**
     * Finds and returns a AppLog object matching the passed in id.
     * @param id Id of wanted AppLog.
     * @return AppLog object matching id.
     */
    @Override
    public AppLog findById(Long id) throws NotFoundException {
        Optional<AppLog> appLogOptional = appLogRepository.findById(id);
        return appLogRepository.findById(id).orElseThrow(() -> new NotFoundException(AppLog.class, id));
    }

}
