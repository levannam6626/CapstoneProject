package com.finalpbl.Model;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_order")
@Getter
@Setter
@NoArgsConstructor
public class ProductOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_id")
    private long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "paid_status")
    private Boolean paidStatus;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "productOrder")
    private List<OrderItem> orderItem;

}
