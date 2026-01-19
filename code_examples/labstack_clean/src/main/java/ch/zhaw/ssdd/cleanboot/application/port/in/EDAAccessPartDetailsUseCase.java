package ch.zhaw.ssdd.cleanboot.application.port.in;


import ch.zhaw.ssdd.cleanboot.domain.model.Component;
import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;

public interface EDAAccessPartDetailsUseCase {
    Component invokeEDAPartDetails(InternalPartNumber ipn);
}
