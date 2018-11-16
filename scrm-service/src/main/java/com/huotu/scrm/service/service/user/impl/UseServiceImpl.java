package com.huotu.scrm.service.service.user.impl;

import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.repository.user.UseRepository;
import com.huotu.scrm.service.service.user.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UseServiceImpl implements UseService {

    @Autowired
    private UseRepository useRepository;

    /**
     * 获取员工id
     * @return
     */
    @Override
    public String findByCode() {
        String uid= useRepository.findByCode();
        uid= GenerateUid.getUid(uid);
        return uid;
    }

    /**
     * 保存数据
     * @param user
     */
    @Override
    public void insertUse(User user) {
        useRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAllBy() {
        return useRepository.findAll();
    }

    /**
     * 删除联系人
     * @return
     */
    @Override
    public void deleteByUserId(User user) {
        useRepository.delete(user);
    }

    /**
     * 查找联系人
     * @param userId
     * @return
     */
    @Override
    public User findOneByUserId(String userId) {
        return useRepository.findOneByUserId(userId);
    }

}
