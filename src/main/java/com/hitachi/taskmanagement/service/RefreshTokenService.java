package com.hitachi.taskmanagement.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hitachi.taskmanagement.exception.TokenRefreshException;
import com.hitachi.taskmanagement.model.RefreshToken;
import com.hitachi.taskmanagement.repository.RefreshTokenRepository;
import com.hitachi.taskmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
    @Value("${refresh.token.expiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userID) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found")));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(
                userRepository.findById(userId).orElseThrow(() -> new TokenRefreshException(null, "User not found")));
    }

}