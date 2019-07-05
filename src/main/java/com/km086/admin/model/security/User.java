package com.km086.admin.model.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @NotNull
    @Column(name = "NAME", unique = true, nullable = false)
    protected String name;

    @NotNull
    @Column(name = "LOGINNAME", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    protected String loginName;

    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    protected String password;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "RATE")
    protected Float rate;

    @Column(name = "TRANSFEROPENID")
    protected String transferOpenId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PROFILE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Profile profile;

    @JsonIgnore
    @Transient
    private String publicSecret;

    @JsonIgnore
    @Transient
    private String privateSecret;

    @Transient
    @JsonSerialize
    private List<String> authorities;

    @Transient
    protected Collection<Merchant> merchants = new ArrayList();

    @Version
    protected long version;

    private static final long serialVersionUID = 7632043933582497185L;

}
