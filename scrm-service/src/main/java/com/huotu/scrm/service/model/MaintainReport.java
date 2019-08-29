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
        name="MaintainReport",
        entities = {
                @EntityResult(entityClass = MaintainReport.class,
                        fields = {
                                @FieldResult(name = "DEV002",column = "DEV002"),
                                @FieldResult(name = "DEV008",column = "DEV008"),
                                @FieldResult(name = "DEV009",column = "DEV009"),
                                @FieldResult(name = "add",column = "add"),
                                @FieldResult(name = "edit",column = "edit"),
                                @FieldResult(name = "ad",column = "ad"),
                                @FieldResult(name = "bug",column = "bug"),
                                @FieldResult(name = "other",column = "other")
                        })
        }
)

@Entity
@Setter
@Getter

public class MaintainReport implements Serializable {

    private String DEV002;
    private String DEV008;
    private String DEV009;
    private Integer  add;
    private Integer  edit;
    private Integer  ad;
    private Integer  bug;
    private Integer  other;
}
