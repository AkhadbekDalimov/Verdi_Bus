package uz.asbt.digid.mipservice.service;

import uz.asbt.digid.mipservice.models.GetTaxInfoByIdRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByPinRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByTinRequest;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.GetTaxInfobyIdRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.GetTaxInfobyPinRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.GetTaxInfobyTinRes;

public interface GniService {

    GetTaxInfobyIdRes getTaxInfoById(GetTaxInfoByIdRequest value) throws Exception;
    GetTaxInfobyPinRes getTaxInfoByPin(GetTaxInfoByPinRequest value) throws Exception;
    GetTaxInfobyTinRes getTaxInfoByTin(GetTaxInfoByTinRequest value) throws Exception;

}
