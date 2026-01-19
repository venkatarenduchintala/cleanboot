package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecifiedProperty;
import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;
import ch.zhaw.ssdd.cleanboot.domain.model.Unit;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;

class OrderableItemTest {

        private static ManufacturerPartNumber validMpn() {
                return new ManufacturerPartNumber("MPN123");
        }

        private static StockKeepingUnit validSku() {
                return new StockKeepingUnit("SKU123");
        }

        private static Quantity validMinOrder() {
                return new Quantity(10);
        }

        private static Multiplier validMultiplier() {
                return new Multiplier(5);
        }

        private static UnitPrice validPrice() {
                return new UnitPrice(BigDecimal.valueOf(1.5), Currency.CHF);
        }

        private static Map<SpecifiedProperty, SpecificationItem> validAttributes() {
                SpecificationItem spec = new SpecificationItem(SpecifiedProperty.SUPPLY_VOLTAGE_MAX,
                                BigDecimal.valueOf(5),
                                Unit.VOLT);
                return Map.of(SpecifiedProperty.SUPPLY_VOLTAGE_MAX, spec);
        }

        @Test
        void nullFieldsThrow() {
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(null, validSku(), Distributor.MOUSER, validMinOrder(),
                                                validMultiplier(),
                                                validPrice(), validAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), null, Distributor.MOUSER, validMinOrder(),
                                                validMultiplier(),
                                                validPrice(), validAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), null, validMinOrder(),
                                                validMultiplier(), validPrice(),
                                                validAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, null,
                                                validMultiplier(),
                                                validPrice(), validAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, validMinOrder(),
                                                null, validPrice(),
                                                validAttributes()));
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, validMinOrder(),
                                                validMultiplier(),
                                                null, validAttributes()));
        }

        @Test
        void orderMultipleGreaterThanMinOrderThrows() {
                Multiplier bigMultiplier = new Multiplier(20);
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, new Quantity(10),
                                                bigMultiplier,
                                                validPrice(), validAttributes()));
        }

        @Test
        void attributeKeyMismatchThrows() {
                SpecificationItem spec = new SpecificationItem(SpecifiedProperty.OPERATING_VOLTAGE_MAX,
                                BigDecimal.valueOf(5),
                                Unit.VOLT);
                Map<SpecifiedProperty, SpecificationItem> badAttributes = Map.of(SpecifiedProperty.SUPPLY_VOLTAGE_MAX,
                                spec);
                assertThrows(IllegalArgumentException.class,
                                () -> new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, validMinOrder(),
                                                validMultiplier(),
                                                validPrice(), badAttributes));
        }

        @Test
        void validOrderableItemIsAccepted() {
                OrderableItem item = new OrderableItem(validMpn(), validSku(), Distributor.MOUSER, validMinOrder(),
                                validMultiplier(), validPrice(), validAttributes());
                assertNotNull(item);
                assertEquals(1, item.attributes().size());
                assertThrows(UnsupportedOperationException.class,
                                () -> item.attributes().put(SpecifiedProperty.POWER_DISSIPATION_MAX, null));
        }
}
