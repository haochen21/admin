package com.km086.admin.model.security;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "DEVICE")
public class Device implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @NotNull
    @Column(name = "NO", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    protected String no;

    @NotNull
    @Column(name = "PHONE", unique = true, nullable = false)
    protected String phone;

    @Version
    @ToString.Exclude
    protected long version;

    @ToString.Exclude
    private static final long serialVersionUID = -5242433935424543234L;

}
