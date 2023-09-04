package com.juaracoding.IRspringbootrestapi.Model;


import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ModelC")
public class ModelC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDModelC")
    private Long IDModelC ;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 1")
    private Date CreatedBy;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date CreatedDate;

    @Column(name = "IsActive",columnDefinition = "SMALLINT")
    private Integer IsActive;

    @Column(name = "ModelA",columnDefinition = "CHAR(20) NOT NULL default 'Default model C'")
    private  String ModelA;

    @Column(name = "ModifiedBy",columnDefinition = "BIGINT")
    private Long ModifiedBy;

    @Column(name = "ModifiedDate",columnDefinition = "DATETIME")
    private Date ModifiedDate;

    @ManyToMany
    @JoinTable(name = "MapModelBModelC" /*nama table baru untuk mapping join*/,
            joinColumns = {@JoinColumn(name = "IDModelC" /*nama kolom yang akan di buat di table mapping join*/,referencedColumnName = "IDModelC",foreignKey =@ForeignKey(name = "fkMapToModelC"))},
            inverseJoinColumns  = {@JoinColumn(name = "IDModelB"  /*nama kolom yang akan di buat di table mapping join*/,referencedColumnName = "IDModelB",foreignKey = @ForeignKey(name = "fkMapToModelB"))}/*,
            uniqueConstraints = @UniqueConstraint(columnNames = {"IDModelC","IDModelB"})*/
    )
    private List<ModelB> modelB;

}
