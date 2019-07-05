package com.km086.admin.model.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MERCHANT")
public class Merchant implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @ToString.Include
    protected Long id;

    @NotNull
    @Column(name = "LOGINNAME", unique = true, nullable = false)
    @JsonSerialize(using = NameDecodeSerializer.class, as = String.class)
    protected String loginName;

    @Column(name = "DEVICENO", unique = true)
    protected String deviceNo;

    @Column(name = "PRINTNO")
    @Size(max = 255)
    protected String printNo;

    @Column(name = "APPROVED")
    protected Boolean approved;

    @Column(name = "OPENID")
    @ToString.Include
    protected String openId;

    @Column(name = "TRANSFEROPENID")
    protected String transferOpenId;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "REALNAME")
    protected String realName;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "MAIL")
    protected String mail;

    @Column(name = "OPEN")
    protected Boolean open;

    @Column(name = "DISCOUNT")
    protected Float discount;

    @Column(name = "TAKEBYPHONE")
    protected Boolean takeByPhone;

    @Column(name = "TAKEBYPHONESUFFIX")
    protected Boolean takeByPhoneSuffix;

    @Column(name = "RATE")
    protected Float rate;

    @Column(name = "AUTOPAYMENT")
    protected Boolean autoPayment;

    @Column(name = "TRASH")
    protected Boolean trash;

    @Column(name = "TAKEOUT")
    protected Boolean takeOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    private static final long serialVersionUID = 8463132928250348321L;
}
