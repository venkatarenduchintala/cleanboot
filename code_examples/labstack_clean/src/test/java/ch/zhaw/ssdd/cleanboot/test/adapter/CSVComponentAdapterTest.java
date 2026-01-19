package ch.zhaw.ssdd.cleanboot.test.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.adapter.out.csv.CSVComponentAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecifiedProperty;
import ch.zhaw.ssdd.cleanboot.domain.model.Unit;

class CSVComponentAdapterTest {

    private static final String CSV_DATA =
            "CATEGORY,DEVICE,FOOTPRINT,Internal_Part_Number,LCSC_MPN,LCSC,Manufacturer,Property,Value,Unit,Property,Value,Unit,Property,Value,Unit\n" +
            "Resistor,Device:R,Resistor_SMD:R_0603_1608Metric,R_SMD_0603_0R,0603WAF0000T5E,C21189,UNI-ROYAL,NOMINAL_RESISTANCE,0,OHM,OPERATING_VOLTAGE_MAX,75,VOLT,,,\n";
    
    @Test
    void testLoadAll() throws Exception {
        CSVComponentAdapter adapter = new CSVComponentAdapter();
        List<Component> components = adapter.loadAll(new StringReader(CSV_DATA));

        assertNotNull(components, "Components list should not be null");
        assertEquals(1, components.size(), "Should parse 3 components");

        // Check first component
        Component first = components.get(0);
        assertEquals("R_SMD_0603_0R", first.ipn().value());
        assertEquals(Category.RESISTOR, first.category());
        assertEquals(2, first.attributes().size());

        SpecificationItem spec1 = first.attributes().get(SpecifiedProperty.NOMINAL_RESISTANCE);
        assertEquals(SpecifiedProperty.NOMINAL_RESISTANCE, spec1.property());
        assertEquals(new BigDecimal("0"), spec1.value());
        assertEquals(Unit.OHM, spec1.unit());

        SpecificationItem spec2 = first.attributes().get(SpecifiedProperty.OPERATING_VOLTAGE_MAX);
        assertEquals(SpecifiedProperty.OPERATING_VOLTAGE_MAX, spec2.property());
        assertEquals(new BigDecimal("75"), spec2.value());
        assertEquals(Unit.VOLT, spec2.unit());

    }
}


