package com.devtony.app.services;

import com.devtony.app.repository.IUserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QRService {
    private final IUserRepository repoUser;

    public QRService(IUserRepository repoUser) {
        this.repoUser = repoUser;
    }

    public String getQRCodePath(String path) {
        return "";
    }
}
