package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface RegisterService {
    List<Register> getRegisters();
    Register findRegister(String id);
    Register save(Register register);
    Register update(Register register);
    void delete(String id);
    boolean checkUserRegisterActivity(String userId, String activityId);
}
