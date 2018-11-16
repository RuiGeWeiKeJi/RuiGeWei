package com.huotu.scrm.service.repository.Power;

import com.huotu.scrm.service.entity.Power.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    /**
     * 获取ID
     * @return
     */
    @Query(value = "select ROL004 from rgwrol where id=(select max(id) from rgwrol)",nativeQuery = true)
    String findByCode();

    /**
     * 删除
     * @param oddNum
     */
    void deleteByOddNum(String oddNum);

}
