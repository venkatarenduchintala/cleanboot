package ch.zhaw.ssdd.cleanboot.domain.model;

import java.util.List;

public record BillOfMaterial(
        List<BillOfMaterialEntry> lines) {

    public BillOfMaterial {
        if (lines == null)
            throw new IllegalArgumentException("A BOM must have an entry list");
        lines = List.copyOf(lines);
        if (lines.size() < 1)
            throw new IllegalArgumentException("A BOM must have at least one entry");
    }
}
