package com.unidev.rabbittask.service;

import org.springframework.stereotype.Service;

@Service
public class CertifierService {

    public String certifyTask(String task) {
        return task + "-certified";
    }
}
