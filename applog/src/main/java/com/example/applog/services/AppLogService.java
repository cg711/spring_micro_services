package com.example.applog.services;

import com.example.applog.exceptions.NotFoundException;
import com.example.applog.model.AppLog;

public interface AppLogService {

    AppLog saveOrUpdate (AppLog student) throws NotFoundException;

    AppLog findById(Long id) throws NotFoundException;
}
