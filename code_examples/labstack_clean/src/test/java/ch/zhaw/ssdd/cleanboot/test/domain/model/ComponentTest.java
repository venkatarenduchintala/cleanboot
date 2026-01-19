package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

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

class ComponentTest {

        private static OrderableItem validOrderableItem() {
                return new OrderableItem(
                                new ManufacturerPartNumber("MPN1"),
                                new StockKeepingUnit("SKU1"),
                                Distributor.MOUSER,
                                new Quantity(10),
                                new Multiplier(5),
                                new UnitPrice(BigDecimal.valueOf(1.5), Currency.USD),
                                Map.of(SpecifiedProperty.POWER_DISSIPATION_MAX,
                                                new SpecificationItem(SpecifiedProperty.POWER_DISSIPATION_MAX,
                                                                BigDecimal.valueOf(10), Unit.WATT)));
        }

        private static Map<SpecifiedProperty, SpecificationItem> validComponentAttributes() {
                return Map.of(SpecifiedProperty.SUPPLY_VOLTAGE_MAX, new SpecificationItem(
                                SpecifiedProperty.SUPPLY_VOLTAGE_MAX, BigDecimal.valueOf(5), Unit.VOLT));
        }

        private static LibraryReference validSymbolReference = new LibraryReference("ABC:123");

        private static LibraryReference validFootprintReference = new LibraryReference("DEF:456");

        @Test
        void nullFieldsThrow() {
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(null, Category.RESISTOR, validSymbolReference,
                                                validFootprintReference, List.of(validOrderableItem()),
                                                validComponentAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN1"), null, validSymbolReference,
                                                validFootprintReference, List.of(validOrderableItem()),
                                                validComponentAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN1"), Category.RESISTOR, null,
                                                validFootprintReference, List.of(validOrderableItem()),
                                                validComponentAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN1"), Category.RESISTOR,
                                                validSymbolReference, null, List.of(validOrderableItem()),
                                                validComponentAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN1"), Category.RESISTOR,
                                                validSymbolReference, validFootprintReference, null,
                                                validComponentAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN1"), Category.RESISTOR,
                                                validSymbolReference, validFootprintReference, List.of(),
                                                validComponentAttributes()));
        }

        @Test
        void conflictingAttributesWithOrderableItemThrows() {
                // The orderable item already has POWER_DISSIPATION_MAX
                Map<SpecifiedProperty, SpecificationItem> attributes = Map.of(
                                SpecifiedProperty.POWER_DISSIPATION_MAX,
                                new SpecificationItem(SpecifiedProperty.POWER_DISSIPATION_MAX, BigDecimal.valueOf(5),
                                                Unit.WATT));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN2"), Category.IC, validSymbolReference,
                                                validFootprintReference,
                                                List.of(validOrderableItem()), attributes));
        }

        @Test
        void attributeKeyMismatchThrows() {
                Map<SpecifiedProperty, SpecificationItem> badAttributes = Map.of(
                                SpecifiedProperty.SUPPLY_VOLTAGE_MAX,
                                new SpecificationItem(SpecifiedProperty.OPERATING_VOLTAGE_MAX, BigDecimal.valueOf(5),
                                                Unit.VOLT));
                assertThrows(IllegalArgumentException.class,
                                () -> new Component(new InternalPartNumber("IPN3"), Category.IC, validSymbolReference,
                                                validFootprintReference,
                                                List.of(validOrderableItem()), badAttributes));
        }

        @Test
        void validComponentIsAccepted() {
                Component component = new Component(
                                new InternalPartNumber("IPN4"),
                                Category.CAPACITOR, validSymbolReference, validFootprintReference,
                                List.of(validOrderableItem()),
                                validComponentAttributes());

                assertNotNull(component);
                assertEquals(1, component.orderableItems().size());
                assertEquals(1, component.attributes().size());
        }

        @Test
        void validComponentWithoutAttributesIsAccepted() {
                Component component = new Component(
                                new InternalPartNumber("IPN4"),
                                Category.CAPACITOR, validSymbolReference, validFootprintReference,
                                List.of(validOrderableItem()),
                                Map.of());

                assertNotNull(component);
                assertEquals(1, component.orderableItems().size());
                assertEquals(0, component.attributes().size());
        }
}
