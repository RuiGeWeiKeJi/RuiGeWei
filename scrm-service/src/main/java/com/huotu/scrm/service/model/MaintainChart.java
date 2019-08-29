package com.huotu.scrm.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//@NamedNativeQuery(
//        name = "MaintainReport.test",
//        query = "SELECT id1,id2,name FROM test",
//        resultSetMapping = "test")
@SqlResultSetMapping(
        name="MaintainChart",
        entities = {
                @EntityResult(entityClass = MaintainChart.class,
                        fields = {
                                @FieldResult(name = "opera",column = "opera"),
                                @FieldResult(name = "speed",column = "speed"),
                                @FieldResult(name = "date",column = "date"),
                                @FieldResult(name = "coun",column = "coun")
                        })
        }
)

@Entity
@Setter
@Getter

public class MaintainChart  implements Serializable {

    /**
     * 操作
     */
    private String opera;

    /**
     * 状态
     */
    private String speed;

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private String coun;

}
