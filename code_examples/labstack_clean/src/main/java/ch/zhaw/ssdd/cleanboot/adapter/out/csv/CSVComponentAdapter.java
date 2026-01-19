package ch.zhaw.ssdd.cleanboot.adapter.out.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.LibraryReference;
import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecifiedProperty;
import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;
import ch.zhaw.ssdd.cleanboot.domain.model.Unit;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;

@Service
public class CSVComponentAdapter {

    private static final int[][] SPEC_INDICES = {
            {7, 8, 9},
            {10, 11, 12},
            {13, 14, 15}
    };

    public List<Component> loadAll(Reader reader) throws IOException {
        try (BufferedReader csvReader = new BufferedReader(reader)) {
            List<Component> components = new ArrayList<>();

            // skip header
            String line = csvReader.readLine();

            while ((line = csvReader.readLine()) != null) {
                Component component = parseLine(line);
                components.add(component);
            }

            return components;
        }
    }

    private Component parseLine(String line) {
        String[] col = line.split(",");

        Component c = new Component(
                new InternalPartNumber(col[3]),
                Category.parseString(col[0]),
                new LibraryReference(col[1]),
                new LibraryReference(col[2]),
                List.of(new OrderableItem(
                        new ManufacturerPartNumber(col[4]),
                        new StockKeepingUnit(col[5]),
                        Distributor.LCSC,
                        new Quantity(1),
                        new Multiplier(1),
                        new UnitPrice(new BigDecimal("0.0001"), Currency.USD),
                        Map.of())),
                Map.of()
        );

        for (int[] indices : SPEC_INDICES) {
            SpecificationItem s = parseAttribute(col, indices[0], indices[1], indices[2]);
            if (s != null) {
                c = c.withSpecificationItem(s);
            }
        }

        return c;
    }

    private SpecificationItem parseAttribute(String[] col, int idxProperty, int idxValue, int idxUnit) {
        // Ensure indices exist
        if (col.length <= Math.max(idxProperty, Math.max(idxValue, idxUnit))) {
            return null;
        }

        // Skip empty cells
        if (col[idxProperty].isBlank() || col[idxValue].isBlank() || col[idxUnit].isBlank()) {
            return null;
        }

        try {
            return new SpecificationItem(
                    SpecifiedProperty.parseString(col[idxProperty]),
                    new BigDecimal(col[idxValue]),
                    Unit.parseString(col[idxUnit])
            );
        } catch (Exception e) {
            return null; // ignore parsing errors
        }
    }
}
