package ch.zhaw.ssdd.cleanboot.domain.model;

import java.util.ArrayList;
import java.util.List;

public record Order(
        Distributor distributor,
        List<OrderEntry> lines
) {
    public Order {
        if (distributor == null)
            throw new IllegalArgumentException("An Order must have a valid distributor specified");
        if (lines == null)
            throw new IllegalArgumentException("An Order must have an entry list");
        lines = List.copyOf(lines);
        if (lines.size() < 1)
            throw new IllegalArgumentException("An Order must have at least one entry");
    }

    public Order withLine(OrderEntry line) {
        if (line == null)
            throw new IllegalArgumentException("OrderEntry must not be null");
        
        List<OrderEntry> copy = new ArrayList<>(lines);
        copy.add(line);
        return new Order(distributor, copy);
    }
}
