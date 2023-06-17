package com.finalpbl.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;


    @Column(name = "product_description", length = 255)
    @NotNull
    private String productDescription;

    @Column(name = "product_name", length = 80)
    @NotNull
    private String productName;

    @Column(name = "product_image")
    @NotNull
    private String productImage;

    @Column(name = "update_date")
    @NotNull
    private LocalDateTime updateDate;

    @Column(name = "product_price")
    @NotNull
    private double productPrice;

    @Column(nullable = false)
    private Boolean isDeleted;

    @NotNull
    private int quantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> cartItems;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Cart> carts;
    
}


