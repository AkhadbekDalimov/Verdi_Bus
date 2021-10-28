package uz.asbt.digid.mipservice.service;

import uz.asbt.digid.common.models.rest.mip.GetAddressRequest;
import uz.asbt.digid.mipservice.models.*;
import uz.asbt.digid.mipservice.types.moi.getAddress.ResponseModel;
import uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.GetDistrictByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.GetDistrictsByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.GetPlaceByPlaceIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.GetPlacesByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.GetRegionByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegions.GetRegionsResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.GetStreetByStreetIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.GetStreetsByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.GetStreetsByPlaceIdResponse;

public interface AddressService {

    ResponseModel getAddress(GetAddressRequest request) throws Exception;
    GetDistrictByDistrictIdResponse getDistrictByDistrictId(GetDistrictByDistrictIdRequest value) throws Exception;
    GetDistrictsByRegionIdResponse getDistrictsByRegionId(GetDistrictsByRegionIdRequest value) throws Exception;
    GetPlaceByPlaceIdResponse getPlaceByPlaceId(GetPlaceByPlaceIdRequest value) throws Exception;
    GetPlacesByDistrictIdResponse getPlacesByDistrictId(GetPlacesByDistrictIdRequest value) throws Exception;
    GetRegionByRegionIdResponse getRegionByRegionId(GetRegionByRegionIdRequest value) throws Exception;
    GetRegionsResponse getRegions(GetRegionsRequest value) throws Exception;
    GetStreetByStreetIdResponse getStreetByStreetId(GetStreetByStreetIdRequest value) throws Exception;
    GetStreetsByDistrictIdResponse getStreetsByDistrictId(GetStreetsByDistrictIdRequest value) throws Exception;
    GetStreetsByPlaceIdResponse getStreetsByPlaceId(GetStreetsByPlaceIdRequest value) throws Exception;

}
