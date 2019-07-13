package com.km086.admin.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.km086.admin.model.store.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "CARTITEM")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @EqualsAndHashCode.Include
    protected Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity;

    @NotNull
    @Column(name = "UNITPRICE", nullable = false)
    protected BigDecimal unitPrice;

    @NotNull
    @Column(name = "TOTALPRICE", nullable = false)
    protected BigDecimal totalPrice;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ToString.Exclude
    protected Product product;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    protected Cart cart;

    private static final long serialVersionUID = 6852793237053469465L;

}
