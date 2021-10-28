package uz.asbt.digid.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.auth.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    @Query(value = "select l from Login l where l.username = ?1 and password = ?2 and active = ?3")
    Login findByUsernameAndPasswordAndActive(String username, String password, int active);
    Login findByUsername(String deviceCrmId);

}
