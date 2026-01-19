package ch.zhaw.ssdd.cleanboot.application.service;

import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.cleanboot.adapter.out.relational.RelationalComponentAdapter;
import ch.zhaw.ssdd.cleanboot.application.exception.ComponentNotAvailableException;
import ch.zhaw.ssdd.cleanboot.application.port.in.AddOrderableItemUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.out.LoggingAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;

@Service
public class ComponentsUpdateService implements AddOrderableItemUseCase {

    private final RelationalComponentAdapter relationalComponentAdapter;
    private final LoggingAdapter logger;

    public ComponentsUpdateService(RelationalComponentAdapter relationalComponentAdapter, LoggingAdapter logger) {
        this.relationalComponentAdapter = relationalComponentAdapter;
        this.logger = logger;
    }

    @Override
    public Component invokeUcAddItem(InternalPartNumber internalPartNumber,
            OrderableItem orderableItem) {
        // *******************
        // *** Shell: I/O ***
        // *******************
        Component component = relationalComponentAdapter.loadComponent(
                internalPartNumber).orElseThrow(ComponentNotAvailableException::new);

        // *******************
        // *** Pure Core ***
        // *******************
        component = component.withOrderableItem(orderableItem);

        // *******************
        // *** Shell: I/O ***
        // *******************
        relationalComponentAdapter.updateComponent(internalPartNumber, component);
        logger.info("AddOrderableItemUseCase added: " + orderableItem.mpn() + " to Component " + component.ipn() );
        return component;
    }
}
