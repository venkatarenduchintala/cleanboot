package ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto;

import ch.zhaw.ssdd.cleanboot.domain.model.Component;

public record KicadComponent(
        String id,
        String name,
        String description) {

    public static KicadComponent fromEntity(Component c) {
        return new KicadComponent(c.ipn().value(), c.ipn().value(), c.ipn().value());
    }
}
