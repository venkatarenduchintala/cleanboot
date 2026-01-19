package ch.zhaw.ssdd.cleanboot.domain.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public record Component(
        InternalPartNumber ipn,
        Category category,
        LibraryReference symbol,
        LibraryReference footprint,
        List<OrderableItem> orderableItems,
        Map<SpecifiedProperty, SpecificationItem> attributes) {

    public Component {
        if (ipn == null)
            throw new IllegalArgumentException(
                    "A component must have an internal part number");
        if (category == null)
            throw new IllegalArgumentException(
                    "A component must list a category");
        if (symbol == null)
            throw new IllegalArgumentException(
                    "A component must specify an EDA symbol");
        if (footprint == null)
            throw new IllegalArgumentException(
                    "A component must specify an EDA footprint");
        if (orderableItems == null)
            throw new IllegalArgumentException(
                    "A component must not have a null orderableItemList");
        else
            orderableItems = List.copyOf(orderableItems);

        if (orderableItems.size() < 1)
            throw new IllegalArgumentException(
                    "A component must at least be orderable from one source");
        if (attributes == null)
            attributes = Map.of();
        else
            attributes = Map.copyOf(attributes);

        for (OrderableItem orderableItem : orderableItems) {
            if (attributes.keySet().stream().anyMatch(orderableItem.attributes()::containsKey))
                throw new IllegalArgumentException("Conflicting specification found in component " + ipn.value());
        }

        // Since it is the same logic, we can reuse the checks from OrderableItem
        OrderableItem.validateAttributes(attributes, ipn.value());
    }

    public Component withOrderableItem(OrderableItem oi) {
        List<OrderableItem> itemList = new LinkedList<>(orderableItems);
        itemList.add(oi);
        return new Component(ipn, category, symbol, footprint, itemList, attributes);
    }

    public Component withSpecificationItem(SpecificationItem si) {
        Map<SpecifiedProperty, SpecificationItem> attributeMap = new HashMap<>(attributes);
        attributeMap.put(si.property(), si);
        return new Component(ipn, category, symbol, footprint, orderableItems, attributeMap);
    }
}
