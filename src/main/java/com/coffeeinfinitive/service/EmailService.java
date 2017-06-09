package com.coffeeinfinitive.service;

import java.util.List;

/**
 * Created by jinz on 6/8/17.
 */
public interface EmailService {
    void sendEmail(String email, String content);
    void sendEmail(List<String> email, String content);
}
