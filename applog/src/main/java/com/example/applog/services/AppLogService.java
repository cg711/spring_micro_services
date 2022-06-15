package com.example.applog.services;

import com.example.applog.model.AppLog;

public interface AppLogService {

    AppLog saveOrUpdate (AppLog student);

    AppLog findById(Long id);

    void deleteById(Long id);
}
