package com.km086.admin.model.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.km086.admin.model.security.Merchant;
import com.km086.admin.model.security.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "BILL")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @NotNull
    @Column(name = "NO", unique = true, nullable = false)
    protected String no;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "STATDATE")
    @EqualsAndHashCode.Include
    protected Date statDate;

    @Column(name = "OPENID")
    @EqualsAndHashCode.Include
    protected String openId;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected BillStatus status;

    @NotNull
    @Column(name = "TOTALPRICE")
    protected BigDecimal totalPrice;

    @NotNull
    @Column(name = "RATE")
    protected Float rate;

    @NotNull
    @Column(name = "SERVICECHARGE")
    protected BigDecimal serviceCharge;

    @NotNull
    @Column(name = "PAYMENT")
    protected BigDecimal payment;

    @NotNull
    @Column(name = "AGENTRATE")
    protected Float agentRate;

    @NotNull
    @Column(name = "WEIXINEARNING")
    protected BigDecimal weixinEarning;

    @NotNull
    @Column(name = "AGENTEARNING")
    protected BigDecimal agentEarning;

    @NotNull
    @Column(name = "TICKETEARNING")
    protected BigDecimal ticketEarning;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    @JsonIgnore
    protected Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    protected User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    protected Date updatedOn;

    @Version
    protected long version;

    @Transient
    private Boolean error = Boolean.valueOf(false);

    @Transient
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final long serialVersionUID = 5000076244041489880L;

    private double round(BigDecimal value) {
        return value.setScale(2, 4).doubleValue();
    }

    public String toString() {
        return "Bill [statDate=" + dateFormat.format(this.statDate) + ", name=" + this.name + ", status: " + this.status + ", totalPrice=" + this.totalPrice + ", rate=" + this.rate + ", serviceCharge=" + round(this.serviceCharge) + ", payment=" + round(this.payment) + "]";
    }
}
