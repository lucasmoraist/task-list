package com.lucasmoraist.gateway.service;

import com.lucasmoraist.gateway.domain.entity.User;

public interface TokenService {
    String generateToken(User user);
    String validateToken(String token);
}
