package com.huotu.scrm.service.service.user;

import com.huotu.scrm.service.entity.User.User;

import java.util.List;

public interface UseService {

    /**
     * 获取员工ID
     * @return
     */
    String findByCode();

    /**
     * 保存数据
     * @param user
     */
    void insertUse(User user);

    /**
     * 查询所有数据
     * @return
     */
    List<User> findAllBy();

    /**
     * 删除联系人
     * @return
     */
    void deleteByUserId(User user);

    /**
     * 根据编号查询数据
     * @param userId
     * @return
     */
    User findOneByUserId(String userId);

    /**
     * 获取所有员工
     * @return
     */
    List<String> getAllBy();

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    int updateUserPass(String username,String password);

}
