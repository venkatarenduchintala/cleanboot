package ch.zhaw.ssdd.cleanboot.adapter.out.relational.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.ssdd.cleanboot.adapter.out.relational.model.ComponentEntity;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;

public interface ComponentRepository extends JpaRepository<ComponentEntity, Long> {
    Optional<ComponentEntity> findByIpn(String ipn);

    boolean existsByIpn(String ipn);

    List<ComponentEntity> findByCategory(Category category);

    void deleteByIpn(String ipn);

    @Query("SELECT DISTINCT c.category FROM ComponentEntity c")
    List<Category> findDistinctCategories();
}
