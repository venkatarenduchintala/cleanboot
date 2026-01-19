package ch.zhaw.ssdd.cleanboot.application.port.out;

import java.util.List;
import java.util.Optional;

import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;

public interface StoragePersistanceAdapter {
    public void persistNewCompnent(Component component);
    public void updateComponent(InternalPartNumber original, Component replacement);
    public Optional<Component> loadComponent(InternalPartNumber ipn);
    public List<Component> loadAllByCategory(Category category);
    public List<Component> loadAll();
    public List<Category> availableCategories();
}
