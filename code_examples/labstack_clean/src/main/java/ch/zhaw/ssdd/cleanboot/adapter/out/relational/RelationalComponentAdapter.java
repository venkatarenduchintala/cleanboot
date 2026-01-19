package ch.zhaw.ssdd.cleanboot.adapter.out.relational;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.zhaw.ssdd.cleanboot.adapter.out.relational.model.AttributeEntity;
import ch.zhaw.ssdd.cleanboot.adapter.out.relational.model.ComponentEntity;
import ch.zhaw.ssdd.cleanboot.adapter.out.relational.model.OrderableItemEntity;
import ch.zhaw.ssdd.cleanboot.adapter.out.relational.repository.ComponentRepository;
import ch.zhaw.ssdd.cleanboot.application.port.out.StoragePersistanceAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.LibraryReference;
import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;
import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RelationalComponentAdapter implements StoragePersistanceAdapter {

    private ComponentRepository componentRepository;

    @Override
    public void persistNewCompnent(Component component) {

        if (componentRepository.existsByIpn(component.ipn().value())) {
            throw new IllegalArgumentException("Component must not yet exist to be saved");
        }

        ComponentEntity ce = fromDomain(component);
        componentRepository.save(ce);
    }

    @Override
    @Transactional
    public void updateComponent(InternalPartNumber original, Component replacement) {
        ComponentEntity existing = componentRepository.findByIpn(original.value())
                .orElseThrow(() -> new IllegalArgumentException("Component not found"));

        // update fields
        existing.setCategory(replacement.category());

        // update orderable items
        existing.getOrderableItems().clear();
        existing.getOrderableItems().addAll(
                replacement.orderableItems().stream()
                        .map(this::fromDomain)
                        .collect(Collectors.toList()));

        // update attributes
        existing.getAttributes().clear();
        existing.getAttributes().addAll(
                replacement.attributes().entrySet().stream()
                        .map(e -> fromDomain(e.getValue()))
                        .collect(Collectors.toList()));

        // save is optional if inside @Transactional
        componentRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Component> loadComponent(InternalPartNumber ipn) {
        return componentRepository.findByIpn(ipn.value())
                .map(ce -> toDomain(ce));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Component> loadAllByCategory(Category category) {
        return componentRepository.findByCategory(category)
                .stream()
                .map(ce -> toDomain(ce)).toList();
    }

    @Override
    public List<Component> loadAll() {
        return componentRepository.findAll()
                .stream()
                .map(ce -> toDomain(ce)).toList();
    }
    
    @Override
    public List<Category> availableCategories() {
        return componentRepository.findDistinctCategories();
    }

    private ComponentEntity fromDomain(Component c) {
        return ComponentEntity.builder()
                .ipn(c.ipn().value())
                .category(c.category())
                .symbolRef(c.symbol().value())
                .footprintRef(c.footprint().value())
                .orderableItems(
                        c.orderableItems()
                                .stream()
                                .map(oi -> fromDomain(oi))
                                .collect(Collectors.toList()))
                .attributes(c.attributes()
                        .entrySet()
                        .stream()
                        .map(e -> fromDomain(e.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderableItemEntity fromDomain(OrderableItem oi) {
        return OrderableItemEntity.builder()
                .mpn(oi.mpn().value())
                .sku(oi.sku().value())
                .distributor(oi.distributor())
                .minOrderQuantity(oi.minOrderQuantity().value())
                .orderMultiple(oi.orderMultiple().value())
                .price(oi.price().value())
                .currency(oi.price().currency())
                .attributes(oi.attributes()
                        .entrySet()
                        .stream()
                        .map(e -> fromDomain(e.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }

    private AttributeEntity fromDomain(SpecificationItem i) {
        return AttributeEntity.builder()
                .property(i.property())
                .value(i.value())
                .unit(i.unit())
                .build();
    }

    private Component toDomain(ComponentEntity ce) {
        Component c = new Component(
                new InternalPartNumber(ce.getIpn()),
                ce.getCategory(),
                new LibraryReference(ce.getSymbolRef()),
                new LibraryReference(ce.getFootprintRef()),
                ce.getOrderableItems().stream().map(e -> toDomain(e)).toList(),
                Map.of());
        for (AttributeEntity ae : ce.getAttributes()) {
            c = c.withSpecificationItem(toDomain(ae));
        }
        return c;
    }

    private OrderableItem toDomain(OrderableItemEntity oe) {
        OrderableItem oi = new OrderableItem(
                new ManufacturerPartNumber(oe.getMpn()),
                new StockKeepingUnit(oe.getSku()),
                oe.getDistributor(),
                new Quantity(oe.getMinOrderQuantity()),
                new Multiplier(oe.getOrderMultiple()),
                new UnitPrice(oe.getPrice(), oe.getCurrency()),
                Map.of());
        for (AttributeEntity ae : oe.getAttributes()) {
            oi = oi.withSpecificationItem(toDomain(ae));
        }
        return oi;
    }

    private SpecificationItem toDomain(AttributeEntity ae) {
        return new SpecificationItem(
                ae.getProperty(),
                ae.getValue(),
                ae.getUnit());
    }
}
