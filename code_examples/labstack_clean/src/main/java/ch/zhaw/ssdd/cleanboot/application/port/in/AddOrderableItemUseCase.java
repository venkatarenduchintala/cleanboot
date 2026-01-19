package ch.zhaw.ssdd.cleanboot.application.port.in;

import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;
import ch.zhaw.ssdd.cleanboot.domain.model.OrderableItem;

public interface AddOrderableItemUseCase {
    Component invokeUcAddItem(InternalPartNumber internalPartNumber, OrderableItem orderableItem);
}
