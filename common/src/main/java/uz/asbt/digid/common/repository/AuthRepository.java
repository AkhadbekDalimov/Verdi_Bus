package uz.asbt.digid.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.auth.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Auth findByLoginAndPasswordAndStatus(String login, String password, int status);

}
