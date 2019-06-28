package com.km086.admin.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.km086.admin.model.security.Customer;
import com.km086.admin.model.security.Merchant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "CART")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @EqualsAndHashCode.Include
    protected Long id;

    @Column(name = "NO", unique = true, nullable = false)
    protected String no;

    @Column(name = "TRANSACTIONID")
    protected String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    @ToString.Exclude
    protected Merchant merchant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @ToString.Exclude
    protected Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected CartStatus status;

    @Column(name = "TOTALPRICE")
    protected BigDecimal totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYTIME")
    protected Date payTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    protected Date createdOn;

    @Column(name = "REMARK")
    @Size(max = 255)
    protected String remark;

    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    @ToString.Exclude
    protected Collection<CartItem> cartItems = new ArrayList();

    private static final long serialVersionUID = 7038158240467068226L;


}
