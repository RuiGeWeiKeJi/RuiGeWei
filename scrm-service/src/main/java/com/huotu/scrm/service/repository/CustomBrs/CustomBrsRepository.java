package com.huotu.scrm.service.repository.CustomBrs;

import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomBrsRepository extends JpaRepository<CustomBRS, Long>, JpaSpecificationExecutor<CustomBRS> {

    /**
     * 根据客户编号获取记录单单号
     */
    @Query(value = "select BRS001 from rgwbrs where id=(select max(id) from rgwbrs)",nativeQuery = true)
    String findOneByCode( );

    /**
     * 获取系统日期
     *
     * 思路：在mysql里面增加一个视图,读取当前系统时间,然后查视图即可
     *
     * @return
     */
    @Query(value = "select t from systemdate",nativeQuery = true)
    Date findBy();

    /**
     * 依据客户编号查询该客户所有联系人
     * @param customId
     * @return
     */
    @Query(value = "select t.CUR002 from rgwcur t where t.CUR010=?1",nativeQuery = true)
    List<String> findAllByCustomId(String customId);

    /**
     * 根据客户编号获取所有联系人
     *
     * @return
     */
    Page<CustomBRS> findByCustomId(String customId, Pageable pageable);

    /**
     * 删除一条联系单记录
     * @param oddNum
     */
    void deleteByOddNum(String oddNum);

    /**
     * 修改联系日期
     * @param startDate
     * @param endDate
     * @param customId
     */
    @Query(value = "update rgwcus set CUS009=?1,CUS011=?2 where CUS001=?3",nativeQuery = true)
    @Modifying
    void updateCustom(String startDate,String endDate,String customId);

}
