package uz.asbt.digid.crmservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.entity.Client;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientUtil {

  public static Optional<Client> getNotDeletedClient(final List<Client> clients){
    if(clients == null) {
      return Optional.empty();
    }
    return clients
      .stream()
      .filter(c -> c.getIsDeleted() == 0)
      .findFirst();
  }
}
