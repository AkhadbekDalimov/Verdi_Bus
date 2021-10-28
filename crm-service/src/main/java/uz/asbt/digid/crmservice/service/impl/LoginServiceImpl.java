package uz.asbt.digid.crmservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.asbt.digid.common.models.entity.auth.Login;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.repository.LoginRepository;
import uz.asbt.digid.crmservice.service.LoginService;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    LoginRepository repository;

    @Override
    public Login save(final Login login) {
        return repository.save(login);
    }

    @Override
    public Login update(final Login login) {
        if (login == null || login.getId() == 0)
            return null;
        return repository.save(login);
    }

    @Override
    public Login findById(final long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Login findByUsernameAndPassword(final String username, final String password) {
        return repository.findByUsernameAndPasswordAndActive(username, password, 1);
    }

    @Override
    public GridResponse<List<Login>> findAll(final Pageable pageable) {
        final GridResponse<List<Login>> gridResponse = new GridResponse<>();
        final Page<Login> page = repository.findAll(pageable);
        gridResponse.setTotalItemsCount(page.getTotalElements());
        gridResponse.setTotalPages(page.getTotalPages());
        gridResponse.setList(page.getContent());
        return gridResponse;
    }

    @Override
    public Login findByUsername(final String username) {
        return repository.findByUsername(username);
    }
}
