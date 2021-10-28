package uz.asbt.digid.crmservice.service;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.entity.auth.Login;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.util.List;

public interface LoginService {

    Login save(Login login);
    Login update(Login login);
    Login findById(long id);
    Login findByUsernameAndPassword(String username, String password);
    GridResponse<List<Login>> findAll(Pageable pageable);
    Login findByUsername(String username);


}
