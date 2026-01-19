package ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto;

import java.util.Map;
import java.util.Optional;

import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;

public record KicadComponentDetail(
                String id,
                String name,
                String description,
                String symbolIdStr,
                String exclude_from_bom,
                String exclude_from_board,
                String exclude_from_sim,
                Map<String, KicadFieldData> fields) {

        public static KicadComponentDetail fromDomain(Component c) {
                return new KicadComponentDetail(
                                c.ipn().value(),
                                c.ipn().value(),
                                c.ipn().value(),
                                c.symbol().value(),
                                "false",
                                "false", "false",
                                Map.of(
                                                "Value", new KicadFieldData(extractValue(c), "true"),
                                                "Footprint", new KicadFieldData(c.footprint().value(), "false")));
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
