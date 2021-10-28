package uz.asbt.digid.crmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.ClientSended;

import java.util.List;

@Repository
public interface ClientSendRepository extends JpaRepository<ClientSended, Long> {

//    @Query(value = "SELECT c from ClientSended c where c.status = 1 " +
//            "and not exists(select c2 from ClientSended c2 " +
//            "where c2.client = c.client and c2.status = 2)")
//    List<ClientSended> findNotSend();

    @Query(value = "select c from ClientSended c where c.status = 1 and c.needSend = 1")
    List<ClientSended> findNotSend();

}
