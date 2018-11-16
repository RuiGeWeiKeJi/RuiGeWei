package com.huotu.scrm.service.repository.user;


import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    /**
     * 获取员工ID
     * @return
     */
    @Query(value = "select USE001 from rgwuse where id=(select max(id) from rgwuse)",nativeQuery = true)
    String findByCode();

    /**
     * 根据客户编号和联系人编号查询数据
     * @return
     */
    User findOneByUserId(String userId);

}
