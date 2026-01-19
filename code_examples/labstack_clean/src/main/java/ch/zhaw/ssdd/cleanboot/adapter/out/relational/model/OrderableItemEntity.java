package ch.zhaw.ssdd.cleanboot.adapter.out.relational.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderableItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mpn;
    private String sku;
    
    @Enumerated(EnumType.STRING)
    private Distributor distributor;
    private int minOrderQuantity;
    private int orderMultiple;
    @Column(precision = 8, scale = 4)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AttributeEntity> attributes = new ArrayList<>();
}
