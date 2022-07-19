package com.example.common_service.service;

import javax.servlet.http.HttpServletResponse;
public class CommonServiceImpl implements CommonService {
    @Override
    public int handleException(String e) {
        switch (e) {
            case "NotFoundException":
                return HttpServletResponse.SC_NOT_FOUND;
            case "UnauthorizedUserException":
                return HttpServletResponse.SC_UNAUTHORIZED;
        }
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
