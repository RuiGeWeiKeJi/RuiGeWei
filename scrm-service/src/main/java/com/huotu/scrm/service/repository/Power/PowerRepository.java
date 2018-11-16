package com.huotu.scrm.service.repository.Power;

import com.huotu.scrm.service.entity.Power.PowerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerRepository extends JpaRepository<PowerRole, Long>, JpaSpecificationExecutor<PowerRole> {

    /**
     * 获取ID
     * @return
     */
    @Query(value = "select POW008 from rgwpow where id=(select max(id) from rgwpow)",nativeQuery = true)
    String findByCode();

    /**
     * 删除数据
     * @param oddNum
     */
    void deleteByOddNum(String oddNum);

}
