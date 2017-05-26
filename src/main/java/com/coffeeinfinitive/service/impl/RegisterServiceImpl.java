package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;
import com.coffeeinfinitive.dao.reponsitories.ActivityRepository;
import com.coffeeinfinitive.dao.reponsitories.RegisterRepository;
import com.coffeeinfinitive.service.ActivityService;
import com.coffeeinfinitive.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("registerService")
@Transactional
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private RegisterRepository registerRepository;

    @Override
    public Page<Register> getRegistersByPage(Pageable pageable) {
        return registerRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return registerRepository.count();
    }

    @Override
    public List<Register> getRegisters() {
        return registerRepository.findAll();
    }

    @Override
    public Register findRegister(String id) {
        return registerRepository.findOne(id);
    }

    @Override
    public Register save(Register register) {
        return registerRepository.save(register);
    }

    @Override
    public Register update(Register register) {
        return registerRepository.save(register);
    }

    @Override
    public void delete(String id) {
        registerRepository.delete(id);
    }

    @Override
    public boolean checkUserRegisterActivity(String userId, String activityId) {
        return registerRepository.checkUserRegisteredActivity(userId,activityId)>0? true:  false;
    }
}