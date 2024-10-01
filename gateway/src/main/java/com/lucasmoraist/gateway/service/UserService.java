package com.lucasmoraist.gateway.service;

import com.lucasmoraist.gateway.domain.dto.TokenDTO;
import com.lucasmoraist.gateway.domain.model.LoginRequest;
import com.lucasmoraist.gateway.domain.model.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request) throws Exception;
    TokenDTO login(LoginRequest request) throws Exception;
}
