package ch.zhaw.ssdd.cleanboot.adapter.in.webapp;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.ssdd.cleanboot.adapter.in.webapp.dto.ComponentDTO;
import ch.zhaw.ssdd.cleanboot.adapter.in.webapp.dto.OrderableItemDTO;
import ch.zhaw.ssdd.cleanboot.application.port.in.AddOrderableItemUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.ListAllComponentsUseCase;
import ch.zhaw.ssdd.cleanboot.application.port.in.RetrieveComponentInfoUseCase;
import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/components")
public class ComponentRESTController {

    // Use-cases driven by this controller
    private final AddOrderableItemUseCase addOrderabelITemUseCase;
    private final RetrieveComponentInfoUseCase retrieveComponentInfoUseCase;
    private final ListAllComponentsUseCase listAllComponentsUseCase;

    @GetMapping("/{ipn}")
    ResponseEntity<ComponentDTO> getComponent(@PathVariable String ipn) {
        InternalPartNumber internalPartNumber = new InternalPartNumber(ipn);
        Component component = retrieveComponentInfoUseCase.invokeRetrieveComponent(internalPartNumber);
        return ResponseEntity.ok(ComponentDTO.fromDomain(component));
    }

    @GetMapping()
    ResponseEntity<List<ComponentDTO>> getAllComponent() {
        List<Component> components = listAllComponentsUseCase.invokeListAllComponents();
        return ResponseEntity.ok(components.stream().map(ComponentDTO::fromDomain).toList());
    }

    @PostMapping("/{ipn}/orderableItems")
    ResponseEntity<ComponentDTO> addOrderableItem(@PathVariable String ipn,
            @RequestBody OrderableItemDTO orderableItemDTO) {

        InternalPartNumber internalPartNumber = new InternalPartNumber(ipn);
        OrderableItem orderableItem = orderableItemDTO.toDomain();
        Component newComponent = addOrderabelITemUseCase.invokeUcAddItem(internalPartNumber, orderableItem);

        return new ResponseEntity<>(
                ComponentDTO.fromDomain(newComponent),
                HttpStatus.OK);
    }

}
