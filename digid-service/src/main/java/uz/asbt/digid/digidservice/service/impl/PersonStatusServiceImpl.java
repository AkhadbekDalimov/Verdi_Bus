package uz.asbt.digid.digidservice.service.impl;

import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.PersonStatus;
import uz.asbt.digid.digidservice.service.PersonStatusService;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonStatusServiceImpl implements PersonStatusService {

    private final Map<Integer, PersonStatus> personStatuses;

    public PersonStatusServiceImpl() {
        personStatuses = new HashMap<>();
        personStatuses.put(1, new PersonStatus(1, "ЖИВОЙ", "ЖИВОЙ", "ЖИВОЙ", "ALIVE"));
        personStatuses.put(0, new PersonStatus(0, "МЕРТВЫЙ", "МЕРТВЫЙ", "МЕРТВЫЙ", "DEAD"));
    }

    @Override
    public String getStatusName(final int state, final String lang) {
        final PersonStatus personStatus = personStatuses.get(state);
        final String personStatusName;
        switch (lang) {
            case "ru":
                personStatusName = personStatus.getRus();
                break;
            case "uz":
                personStatusName = personStatus.getUzb();
                break;
            case "lat":
                personStatusName = personStatus.getLat();
                break;
            case "en":
                personStatusName = personStatus.getEng();
                break;
            default:
                personStatusName = personStatus.getRus();
        }
        return personStatusName;
    }
}
