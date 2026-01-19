package ch.zhaw.ssdd.cleanboot.application.port.in;

import java.util.List;

import ch.zhaw.ssdd.cleanboot.domain.model.Category;

public interface EDAListCategoriesUseCase {
    List<Category> invokeEDAListCategories();
}
