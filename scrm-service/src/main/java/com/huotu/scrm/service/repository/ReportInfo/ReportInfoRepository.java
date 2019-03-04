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
     * 根据业务员和日期  查询该业务员本月内的联系客户
     * @return
     */
//    @Query(value = "select new com.huoto.scrm.service.model.customtrans(a.CUS001,a.CUS002,a.CUS004,b.BRS005,b.BRS006,b.BRS007) from " +
////            "rgwcus a inner join rgwbrs b on a.CUS001=b.BRS002 order by ?#(#pageable)",nativeQuery = true)
    @Query(value = "select A.CUS001,A.CUS002,A.CUS004,B.BRS005,B.BRS006,B.BRS007 from rgwcus A inner join rgwbrs B on A.CUS001=B.BRS002 where A.CUS012=?1 AND YEAR(B.BRS003)=?2 AND MONTH(B.BRS003)=?3  order by A.CUS001 ASC ?#(#pageable)",
            countQuery = "select count(*) from rgwcus A inner join rgwbrs B on A.CUS001=B.BRS002 where A.CUS012=?1 AND YEAR(B.BRS003)=?2 AND MONTH(B.BRS003)=?3 ",
            nativeQuery = true)
    Page<customtrans> getInfoForsale(String name,int year,int month, Pageable pageable);

}
