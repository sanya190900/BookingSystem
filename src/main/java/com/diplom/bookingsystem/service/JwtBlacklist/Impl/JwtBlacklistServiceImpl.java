package com.diplom.bookingsystem.service.JwtBlacklist.Impl;

import com.diplom.bookingsystem.repository.JwtBlacklistRepository;
import com.diplom.bookingsystem.service.JwtBlacklist.JwtBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void purgeExpired() {
        LocalDateTime now = LocalDateTime.now();
        jwtBlacklistRepository.deleteByExpiryDateLessThan(now);
    }
}
