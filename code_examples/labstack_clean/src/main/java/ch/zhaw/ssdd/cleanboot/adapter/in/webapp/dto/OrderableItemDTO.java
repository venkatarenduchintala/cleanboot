package ch.zhaw.ssdd.cleanboot.adapter.in.webapp.dto;

import java.math.BigDecimal;
import java.util.Map;

import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;
import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;

public record OrderableItemDTO(
        String mpn,
        String sku,
        String distributor,
        int minQuantity,
        int orderMultiple,
        BigDecimal price,
        String currency) {

    public OrderableItem toDomain() {
        return new OrderableItem(
                new ManufacturerPartNumber(mpn),
                new StockKeepingUnit(sku),
                Distributor.fromString(distributor), new Quantity(minQuantity),
                new Multiplier(orderMultiple),
                new UnitPrice(price, Currency.parseString(currency)), Map.of());
    }

    public static OrderableItemDTO fromDomain(OrderableItem oi) {
        return new OrderableItemDTO(
                oi.mpn().value(),
                oi.sku().value(), 
                oi.distributor().name(), 
                oi.minOrderQuantity().value(), 
                oi.orderMultiple().value(),
                oi.price().value(), 
                oi.price().currency().toString());
    }
}
