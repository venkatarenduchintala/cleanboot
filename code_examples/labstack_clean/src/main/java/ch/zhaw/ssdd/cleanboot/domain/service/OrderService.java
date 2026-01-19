package ch.zhaw.ssdd.cleanboot.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.ssdd.cleanboot.domain.model.BillOfMaterial;
import ch.zhaw.ssdd.cleanboot.domain.model.BillOfMaterialEntry;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.Distributor;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Order;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderEntry;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;

public class OrderService {

    public static List<Order> generateOrders(
            BillOfMaterial bom,
            Map<InternalPartNumber, Component> componentMap,
            Distributor preferredDistributor) {

        Map<Distributor, Order> orderMap = new HashMap<>();

        for (BillOfMaterialEntry bomEntry : bom.lines()) {
            Component component = componentMap.get(bomEntry.ipn());
            if (component == null) 
                throw new IllegalArgumentException("All BOM-IPNs must be present in the component map");
            /*  Since having at least one orderableItem is an invariant
                we can search the list to find the preferred supplier.
                If that is not present, we fall back to the first one 
                specified. */
            OrderableItem orderableItem = component.orderableItems().stream()
                    .filter(item -> item.distributor().equals(preferredDistributor))
                    .findFirst()
                    .orElse(component.orderableItems().get(0));

            Distributor distributor = orderableItem.distributor();

            OrderEntry entry = new OrderEntry(
                    orderableItem.sku(),
                    orderableItem.mpn(),
                    bomEntry.quantity());

            Order existing = orderMap.get(distributor);

            Order updated = (existing == null)
                    ? new Order(distributor, List.of(entry))
                    : existing.withLine(entry);

            orderMap.put(distributor, updated);
        }

        return List.copyOf(orderMap.values());
    }
}
