package uz.asbt.digid.updaterservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.updaterservice.model.Update;

import java.util.Optional;

/**
 * Created by User on 15.01.2020.
 */
@Repository
public interface UpdateRepository extends JpaRepository<Update, Long>{

    boolean existsById(Long id);

    @Query(value = "select * from (select u.* from tb_digid_updates u " +
            "order by to_number(replace(u.version, '.', '')) desc)  " +
            "where rownum = 1", nativeQuery = true)
    Optional<Update> findLatestUpdate();

    Page<Update> findAll(Pageable pageable);

}
