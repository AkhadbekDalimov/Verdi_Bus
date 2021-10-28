package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;

import java.util.Locale;

public interface SaveAllDataService {

  void saveAllDataToDB(ModelPersonAnswere person,  Locale locale);
}
