package com.huotu.scrm.service.repository.ReportInfo;

import com.huotu.scrm.service.entity.Power.UserRole;
import com.huotu.scrm.service.model.customtrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReportInfoRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    /**
     * 获取登录人权限
     *
     * @param username
     * @return
     */
    @Query(value = "select DISTINCT ROL003 from rgwrol where ROL002=?1", nativeQuery = true)
    List<String> getAllByROL002(String username);

    /**
     * 获取所有有客户信息的业务员
     * @return
     */
    @Query(value = "select DISTINCT CUS012 from rgwcus",nativeQuery = true)
    List<String> getSalesManForUser( );

    /**
     * 获取所有行业
     * @return
     */
    @Query(value = "select BAS002 from rgwbas where BAS001='类别'",nativeQuery = true)
    List<String> getAllBy();

}
