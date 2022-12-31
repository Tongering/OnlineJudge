package com.tongering.backend.service;

import java.util.Map;

public interface LoginService {
    public Map<String,String> getToken(String username, String password, String ip);
}
