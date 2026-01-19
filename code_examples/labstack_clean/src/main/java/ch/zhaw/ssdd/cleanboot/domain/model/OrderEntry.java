package ch.zhaw.ssdd.cleanboot.domain.model;

public record OrderEntry(
        StockKeepingUnit sku,
        ManufacturerPartNumber mpn,
        Quantity quantity) {

    public OrderEntry {
        if (mpn == null)
            throw new IllegalArgumentException("An OrderEntry must have a valid mpn specified");
        if (sku == null)
            throw new IllegalArgumentException("An OrderEntry must have a valid sku specified");
        if (quantity == null)
            throw new IllegalArgumentException("An OrderEntry must have a valid quantity specified");
    }
}

