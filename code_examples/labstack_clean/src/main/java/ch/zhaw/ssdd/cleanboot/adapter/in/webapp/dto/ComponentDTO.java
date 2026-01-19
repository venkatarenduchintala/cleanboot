package ch.zhaw.ssdd.cleanboot.adapter.in.webapp.dto;

import java.util.List;
import java.util.Optional;

import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;

public record ComponentDTO(
        String ipn,
        String category,
        String value,
        String symbol,
        String footprint,
        List<OrderableItemDTO> orderableItems) {

    public static ComponentDTO fromDomain(Component c) {
        return new ComponentDTO(
                c.ipn().value(),
                c.category().name(),
                extractValue(c),
                c.symbol().value(),
                c.footprint().value(),
                c.orderableItems().stream()
                        .map(OrderableItemDTO::fromDomain).toList());
    }

    private static String extractValue(Component c) {
                Optional<SpecificationItem> nominalItem = c.attributes().entrySet().stream()
                                .filter(e -> e.getValue().property().name().contains("NOMINAL")).findFirst()
                                .map(e -> e.getValue());
                if (nominalItem.isEmpty()) {
                        return c.orderableItems().get(0).mpn().value();
                }
                return nominalItem.get().value() + nominalItem.get().unit().symbol();
        }
}
