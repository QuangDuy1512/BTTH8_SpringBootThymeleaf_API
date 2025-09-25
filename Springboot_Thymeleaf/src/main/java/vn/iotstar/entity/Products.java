package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Products implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Column(length = 500, columnDefinition = "nvarchar(500) not null")
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(length = 200)
    private String images;

    @Column(columnDefinition = "nvarchar(500) not null")
    private String description;

    @Column(nullable = false)
    private double discount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @Column(nullable = false)
    private short status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false) 
    private Category category;
}

