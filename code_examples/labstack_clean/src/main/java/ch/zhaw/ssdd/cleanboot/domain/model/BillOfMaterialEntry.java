package ch.zhaw.ssdd.cleanboot.domain.model;

public record BillOfMaterialEntry(
        InternalPartNumber ipn,
        Quantity quantity) {

    public BillOfMaterialEntry {
        if (ipn == null)
            throw new IllegalArgumentException("A BOM-Entry must have a valid ipn specified");
        if (quantity == null)
            throw new IllegalArgumentException("A BOM-Entry must have a valid quantity specified");
    }
}
