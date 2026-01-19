package ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto.KicadCategory;
import ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto.KicadComponent;
import ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto.KicadComponentDetail;
import ch.zhaw.ssdd.cleanboot.adapter.in.eda.kicad.dto.KicadEndpoint;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAAccessPartDetailsUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAListCategoriesUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.EDAListCategoryEntriesUsecase;
import ch.zhaw.ssdd.cleanboot.domain.model.Category;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/kicad-api/v1")
@AllArgsConstructor
public class KiCadControllerREST {

    private final EDAAccessPartDetailsUseCase accessPartDetailsUseCase;
    private final EDAListCategoryEntriesUsecase listCetegoryEntriesUsecase;
    private final EDAListCategoriesUseCase listCategoriesUsecase;

    @GetMapping("/")
    public KicadEndpoint get() {
        return new KicadEndpoint("", "");
    }

    @GetMapping("/categories.json")
    public List<KicadCategory> categories() {
        return listCategoriesUsecase.invokeEDAListCategories()
                .stream()
                .map(c -> new KicadCategory(c.name(), c.name(), c.name())).toList();
    }

    @GetMapping("/parts/category/{id}.json")
    public ResponseEntity<List<KicadComponent>> category(@PathVariable String id) {
        return new ResponseEntity<>(
                listCetegoryEntriesUsecase.invokeEDAListCategories(Category.parseString(id))
                        .stream()
                        .map(KicadComponent::fromEntity).toList(),
                HttpStatus.OK);

    }

    @GetMapping("/parts/{id}.json")
    public ResponseEntity<KicadComponentDetail> part(@PathVariable String id) {
        return ResponseEntity.ok(
                KicadComponentDetail.fromDomain(
                        accessPartDetailsUseCase.invokeEDAPartDetails(new InternalPartNumber(id))));
    }

}
