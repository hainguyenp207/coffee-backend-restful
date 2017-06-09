package com.coffeeinfinitive.service;

import java.util.List;

/**
 * Created by jinz on 6/8/17.
 */
public interface NotificationService {
    void getNotifies(String userId);
    void sendEmail(List<String> email, String content);
}
