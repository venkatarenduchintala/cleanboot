package ch.zhaw.ssdd.cleanboot.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.cleanboot.adapter.out.relational.RelationalComponentAdapter;
import ch.zhaw.ssdd.cleanboot.application.exception.ComponentNotAvailableException;
import ch.zhaw.ssdd.cleanboot.application.port.in.ListAllComponentsUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.RetrieveComponentInfoUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.out.LoggingAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;

@Service
public class ComponentsQueryService implements RetrieveComponentInfoUseCase, ListAllComponentsUseCase {

    private final RelationalComponentAdapter relationalComponentAdapter;
    private final LoggingAdapter logger;

    public ComponentsQueryService(RelationalComponentAdapter relationalComponentAdapter, LoggingAdapter logger) {
        this.relationalComponentAdapter = relationalComponentAdapter;
        this.logger = logger;
    }

    @Override
    public Component invokeRetrieveComponent(InternalPartNumber ipn) {
        // *******************
        // *** Shell: I/O ***
        // *******************
        Component component = relationalComponentAdapter.loadComponent(ipn).orElseThrow(ComponentNotAvailableException::new);
        logger.debug("RetrieveComponentInfoUseCase loaded: " + component.ipn().value());
        return component;
    }

    @Override
    public List<Component> invokeListAllComponents() {
        // *******************
        // *** Shell: I/O ***
        // *******************
        List<Component> components = relationalComponentAdapter.loadAll();
        logger.debug("ListAllComponentsUseCase loaded: " + components.size() + " components");
        return components;
    }

}
