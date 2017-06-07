package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface RegisterService {
    List<Register> getRegisters();
    Page<Register> getRegistersByPage(Pageable pageable);
    long count();
    Register findRegister(String id);
    Register save(Register register);
    Register update(Register register);
    void delete(String id);
    boolean checkUserRegisterActivity(String userId, String activityId);
    List<Register> getRegistersByUser(String userId);
}
