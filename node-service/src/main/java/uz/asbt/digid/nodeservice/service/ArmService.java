package uz.asbt.digid.nodeservice.service;

import uz.asbt.digid.nodeservice.model.Arm;

import java.util.List;

public interface ArmService {

    List<Arm> list() throws Exception;
    Arm start(Arm arm) throws Exception;
    List<Arm> startAll() throws Exception;

}
