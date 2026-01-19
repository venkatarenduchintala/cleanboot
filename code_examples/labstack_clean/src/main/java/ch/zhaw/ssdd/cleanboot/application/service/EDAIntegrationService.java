package ch.zhaw.ssdd.cleanboot.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.cleanboot.application.exception.ComponentNotAvailableException;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAAccessPartDetailsUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAListCategoriesUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAListCategoryEntriesUsecase;
import ch.zhaw.ssdd.cleanboot.application.port.out.LoggingAdapter;
import ch.zhaw.ssdd.cleanboot.application.port.out.StoragePersistanceAdapter;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;

@Service
public class EDAIntegrationService implements EDAListCategoriesUseCase, EDAListCategoryEntriesUsecase, EDAAccessPartDetailsUseCase{

    private final StoragePersistanceAdapter storagePersistenceAdapter;
    private final LoggingAdapter logger;

    public EDAIntegrationService(StoragePersistanceAdapter storagePersistenceAdapter, LoggingAdapter logger) {
        this.storagePersistenceAdapter = storagePersistenceAdapter;
        this.logger = logger;
    }

    @Override
    public Component invokeEDAPartDetails(InternalPartNumber ipn) {
        // *******************
        // *** Shell: I/O ***
        // *******************
        Component component = storagePersistenceAdapter.loadComponent(ipn).orElseThrow(ComponentNotAvailableException::new);
        logger.debug("EDAAccessPartDetailsUseCase: " + component.ipn().value());
        return component;
    }

    @Override
    public List<Component> invokeEDAListCategories(Category category) {
        // *******************
        // *** Shell: I/O ***
        // *******************
        List<Component> components = storagePersistenceAdapter.loadAllByCategory(category);
        logger.debug("EDAListCetegoryEntriesUsecase: " + category.name() + " loaded with " + components.size() + " parts");
        return components;
    }

    @Override
    public List<Category> invokeEDAListCategories() {
        // *******************
        // *** Shell: I/O ***
        // *******************
        List<Category> categories =  storagePersistenceAdapter.availableCategories();
        logger.debug("EDAListCategoriesUsecase loaded " + categories.size() + " categories");
        return categories;
    }
    

}
