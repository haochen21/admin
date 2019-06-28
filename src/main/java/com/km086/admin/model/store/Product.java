package com.km086.admin.model.store;

import com.km086.admin.model.security.Merchant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @EqualsAndHashCode.Include
    protected Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "UNITPRICE", nullable = false)
    protected BigDecimal unitPrice;

    @Column(name = "DESCRIPTION")
    @Size(max = 255)
    protected String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    protected Merchant merchant;

    @Version
    protected long version;

    private static final long serialVersionUID = 3277060162706927687L;


}