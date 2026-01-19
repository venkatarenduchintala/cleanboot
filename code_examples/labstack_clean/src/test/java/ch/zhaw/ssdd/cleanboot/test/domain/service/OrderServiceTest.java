package ch.zhaw.ssdd.cleanboot.test.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.zhaw.ssdd.cleanboot.domain.model.BillOfMaterial;
import ch.zhaw.ssdd.cleanboot.domain.model.BillOfMaterialEntry;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.LibraryReference;
import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;
import ch.zhaw.ssdd.cleanboot.domain.model.Order;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;
import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;
import ch.zhaw.ssdd.cleanboot.domain.service.OrderService;

class OrderServiceTest {

        @Test
        void generatesOrdersGroupedByDistributorWithPreference() {
                Distributor distA = Distributor.MOUSER;
                Distributor distB = Distributor.FARNELL;

                InternalPartNumber ipn1 = new InternalPartNumber("IPN-1");
                InternalPartNumber ipn2 = new InternalPartNumber("IPN-2");

                OrderableItem itemA1 = new OrderableItem(
                                new ManufacturerPartNumber("MPN-A1"),
                                new StockKeepingUnit("SKU-A1"),
                                distA,
                                new Quantity(1),
                                new Multiplier(1),
                                new UnitPrice(BigDecimal.valueOf(1.0), Currency.USD),
                                Map.of());

                OrderableItem itemB1 = new OrderableItem(
                                new ManufacturerPartNumber("MPN-B1"),
                                new StockKeepingUnit("SKU-B1"),
                                distB,
                                new Quantity(1),
                                new Multiplier(1),
                                new UnitPrice(BigDecimal.valueOf(2.0), Currency.USD),
                                Map.of());

                Component component1 = new Component(
                                ipn1,
                                Category.IC,
                                new LibraryReference("ABC:123"),
                                new LibraryReference("DEF:456"),
                                List.of(itemA1, itemB1),
                                Map.of());

                Component component2 = new Component(
                                ipn2,
                                Category.IC,
                                new LibraryReference("ABC:123"),
                                new LibraryReference("DEF:456"),
                                List.of(itemB1), // only distB available
                                Map.of());

                BillOfMaterial bom = new BillOfMaterial(List.of(
                                new BillOfMaterialEntry(ipn1, new Quantity(10)),
                                new BillOfMaterialEntry(ipn2, new Quantity(5))));

                Map<InternalPartNumber, Component> componentMap = Map.of(
                                ipn1, component1,
                                ipn2, component2);

                // act
                List<Order> orders = OrderService.generateOrders(
                                bom,
                                componentMap,
                                distA // preferred
                );

                // assert
                assertEquals(2, orders.size());

                Order orderA = orders.stream()
                                .filter(o -> o.distributor().equals(distA))
                                .findFirst()
                                .orElseThrow();

                Order orderB = orders.stream()
                                .filter(o -> o.distributor().equals(distB))
                                .findFirst()
                                .orElseThrow();

                // Order for preferred distributor contains only ipn1
                assertEquals(1, orderA.lines().size());
                assertEquals(new Quantity(10), orderA.lines().get(0).quantity());

                // Fallback distributor contains ipn2
                assertEquals(1, orderB.lines().size());
                assertEquals(new Quantity(5), orderB.lines().get(0).quantity());
        }

        @Test
        void missingComponentInMapThrowsException() {
                Distributor dist = Distributor.MOUSER;

                InternalPartNumber ipn = new InternalPartNumber("IPN-1");

                BillOfMaterial bom = new BillOfMaterial(List.of(
                                new BillOfMaterialEntry(ipn, new Quantity(5))));

                Map<InternalPartNumber, Component> emptyMap = Map.of();

                assertThrows(IllegalArgumentException.class,
                                () -> OrderService.generateOrders(bom, emptyMap, dist));
        }
}
