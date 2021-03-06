package com.stortor.spring.web.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "pricePerProduct")
    private BigDecimal pricePerProduct;

    @Column(name = "price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderItem(Product product, Order order, Integer quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Product product;
        private Order order;
        private Integer quantity;
        private BigDecimal pricePerProduct;
        private BigDecimal price;

        public OrderItemBuilder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder setPricePerProduct(BigDecimal pricePerProduct) {
            this.pricePerProduct = pricePerProduct;
            return this;
        }

        public OrderItemBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(product, order, quantity, pricePerProduct, price);
        }
    }
}
