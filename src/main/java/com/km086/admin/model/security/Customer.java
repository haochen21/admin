package com.km086.admin.model.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CUSTOMER")
public class Customer  implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @EqualsAndHashCode.Include
    protected Long id;

    @Column(name = "LOGINNAME")
    @JsonSerialize(using = NameDecodeSerializer.class, as = String.class)
    protected String loginName;

    @Column(name = "OPENID")
    protected String openId;

    @NotNull
    @Column(name = "NAME")
    @JsonSerialize(using = NameDecodeSerializer.class, as = String.class)
    protected String name;

    @Column(name = "CARDNO", unique = true)
    protected String cardNo;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "MAIL")
    protected String mail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    protected Date createdOn;

    private static final long serialVersionUID = 89421814089152615L;
}
