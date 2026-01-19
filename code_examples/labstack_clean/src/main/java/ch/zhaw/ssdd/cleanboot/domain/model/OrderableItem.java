package ch.zhaw.ssdd.cleanboot.domain.model;

import java.util.HashMap;
import java.util.Map;

public record OrderableItem(
        ManufacturerPartNumber mpn,
        StockKeepingUnit sku,
        Distributor distributor,
        Quantity minOrderQuantity,
        Multiplier orderMultiple,
        UnitPrice price,
        Map<SpecifiedProperty, SpecificationItem> attributes) {

    public OrderableItem {
        if (mpn == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid MPN");
        if (sku == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid SKU");
        if (distributor == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid Distributor");
        if (minOrderQuantity == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid minOrderQuantity");
        if (orderMultiple == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid orderMultiple");
        if (orderMultiple.value() > minOrderQuantity.value())
            throw new IllegalArgumentException(
                    "minOrder Quantity cannot be smaller than orderMultiple");
        if (price == null)
            throw new IllegalArgumentException(
                    "An OrderableItem must have a valid Price");
        if (attributes == null)
            attributes = Map.of();

        attributes = Map.copyOf(attributes);

        validateAttributes(attributes, mpn.value());
    }

    // pacakage internal validation helper
    static void validateAttributes(
            Map<SpecifiedProperty, SpecificationItem> attributes,
            String ownerId) {
        for (var entry : attributes.entrySet()) {
            if (entry.getKey() != entry.getValue().property()) {
                throw new IllegalArgumentException(
                        "Consistency error in attributes: " +
                                entry.getKey().name() + " differs from " +
                                entry.getValue().property().name() +
                                " for " + ownerId);
            }
        }
    }

    public OrderableItem withSpecificationItem(SpecificationItem si) {
        Map<SpecifiedProperty, SpecificationItem> attributeMap = new HashMap<>(attributes);
        attributeMap.put(si.property(), si);
        return new OrderableItem(mpn,sku,distributor,minOrderQuantity,orderMultiple,price,attributeMap);
    }
}
