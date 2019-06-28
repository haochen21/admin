package com.km086.admin.model.account;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "AGENTBILL")
public class AgentBill implements Serializable {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @NotNull
    @Column(name = "NO", unique = true, nullable = false)
    protected String no;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "STATBEGINDATE")
    @EqualsAndHashCode.Include
    protected Date statBeginDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "STATENDDATE")
    @EqualsAndHashCode.Include
    protected Date statEndDate;

    @Column(name = "OPENID")
    protected String openId;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    protected BillStatus status;

    @NotNull
    @Column(name = "EARNING")
    protected BigDecimal earning;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @EqualsAndHashCode.Include
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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final long serialVersionUID = -8171286405526564567L;

    private double round(BigDecimal value) {
        return value.setScale(2, 4).doubleValue();
    }


    public String toString() {
        return "AgentBill [statBeginDate=" + dateFormat.format(this.statBeginDate) + ", statEndDate=" + dateFormat.format(this.statEndDate) + ", name=" + this.name + ", earning=" + round(this.earning) + "]";
    }
}
