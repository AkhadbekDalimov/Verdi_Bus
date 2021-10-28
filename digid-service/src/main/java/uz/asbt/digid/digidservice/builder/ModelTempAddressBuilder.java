package uz.asbt.digid.digidservice.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.ModelAddressTemproary;
import uz.asbt.digid.common.models.entity.client.ModelAddressTemproaryAnswere;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.digidservice.model.entity.Country;
import uz.asbt.digid.digidservice.model.entity.District;
import uz.asbt.digid.digidservice.model.entity.Region;
import uz.asbt.digid.digidservice.service.CountryService;
import uz.asbt.digid.digidservice.service.DistrictService;
import uz.asbt.digid.digidservice.service.RegionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class ModelTempAddressBuilder {

    private final CountryService countryService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final Environment env;
    private final MessageSource messageSource;
    private final ErrorService error;
    @Autowired
    public ModelTempAddressBuilder(
            final CountryService countryService,
            final RegionService regionService,
            final DistrictService districtService,
            final Environment env,
            final ErrorService error,
            final MessageSource messageSource) {
        this.countryService = countryService;
        this.regionService = regionService;
        this.districtService = districtService;
        this.env = env;
        this.error = error;
        this.messageSource = messageSource;
    }

    private ModelAddressTemproary parseAddress(final GetAddressResponse addressResponse,
                                               final Locale locale) {
        log.info("Begin parse temporary address");
        final ModelAddressTemproary address = new ModelAddressTemproary();
        if (!addressResponse.getpTemproaryAddress().getpCountry().equals("")
                && !addressResponse.getpTemproaryAddress().getpCountry().equals("-1")) {
            final Country country = countryService.findById(Long.parseLong(addressResponse.getpTemproaryAddress().getpCountry()));
            if (null == country)
                throw new CustomException(env.getProperty("ex8880", Integer.class), messageSource.getMessage("message_8880", null, locale));
            address.setCountry(country.getId());
            address.setCountryName(countryName(locale.getLanguage(), country));
            address.setCountryNameUz(countryName("uz", country));
            final Region region = regionService.findById(Long.parseLong(addressResponse.getpTemproaryAddress().getpRegion()));
            if (null == region)
                throw new CustomException(env.getProperty("ex8881", Integer.class), messageSource.getMessage("message_8881", null, locale));
            address.setRegion(region.getBankCode());
            address.setRegionName(regionName(locale.getLanguage(), region));
            address.setRegionNameUz(regionName("uz", region));
            final District district = districtService.findById(Long.parseLong(addressResponse.getpTemproaryAddress().getpDistrict()));
            if (null == district)
                throw new CustomException(env.getProperty("ex8882", Integer.class), messageSource.getMessage("message_8882", null, locale));
            address.setDistrict(district.getBankCode());
            address.setDistrictName(districtName(locale.getLanguage(), district));
            address.setDistrictNameUz(districtName("uz", district));
            address.setAddress(addressResponse.getpTemproaryAddress().getpAddress());
            address.setHome(addressResponse.getpTemproaryAddress().getpHouse());
            address.setFlat(addressResponse.getpTemproaryAddress().getpFlat());
            address.setBlock(addressResponse.getpTemproaryAddress().getpBlock());
            address.setLiveFromDate(addressResponse.getpTemproaryAddress().getpRegdate());
            address.setLiveTillDate(addressResponse.getpTemproaryAddress().getpRegtill());
            log.info("End parse temporary address");
        }
        return address;
    }

    public ModelAddressTemproaryAnswere build(final GetAddressResponse addressResponse,
                                              final Locale locale) {
        log.info("Begin build temporary address");
        final ModelAddressTemproaryAnswere modelAddressAnswere = new ModelAddressTemproaryAnswere();
        final Answere answere = new Answere();
        try {
            final ModelAddressTemproary address = parseAddress(addressResponse, locale);
            answere.setAnswereId(error.getErrorCode("ok"));
            answere.setAnswereComment(error.getErrorMessage("message_0"));
            modelAddressAnswere.setAnswere(answere);
            modelAddressAnswere.setAddressTemproary(Collections.singletonList(address));
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            answere.setAnswereId(env.getProperty("ex8001", Integer.class));
            answere.setAnswereComment(messageSource.getMessage("message_8001", null, locale));
        }
        modelAddressAnswere.setAnswere(answere);
        log.info("End build temporary address");
        return modelAddressAnswere;
    }

    public ModelAddressTemproaryAnswere build(final PhysicalResponseBody nibbdResponse,
                                              final Locale locale) {
        final ModelAddressTemproaryAnswere addressTemproaryAnswere = new ModelAddressTemproaryAnswere();
        final Answere answere = new Answere();
        try {
            final List<ModelAddressTemproary> list = parseAddress(nibbdResponse, locale);
            answere.setAnswereId(error.getErrorCode("ok"));
            answere.setAnswereComment(error.getErrorMessage("message_0"));
            addressTemproaryAnswere.setAnswere(answere);
            addressTemproaryAnswere.setAddressTemproary(list);
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            answere.setAnswereId(env.getProperty("ex8001", Integer.class));
            answere.setAnswereComment(messageSource.getMessage("message_8001", null, locale));
        }
        addressTemproaryAnswere.setAnswere(answere);
        return addressTemproaryAnswere;
    }

    private List<ModelAddressTemproary> parseAddress(final PhysicalResponseBody nibbdResponse,
                                                     final Locale locale) {
        final List<ModelAddressTemproary> list = new ArrayList<>();
        final ModelAddressTemproary temproary = new ModelAddressTemproary();
        if (nibbdResponse.getTempCountry().equals(""))
            throw new IllegalArgumentException("Temp country is empty");
        final Country country = countryService.findByBankCode(Long.parseLong(nibbdResponse.getTempCountry()));
        if (null == country)
            throw new CustomException(env.getProperty("ex8880", Integer.class), messageSource.getMessage("message_8880", null, locale));
        temproary.setCountry(country.getId());
        temproary.setCountryName(countryName(locale.getLanguage(), country));
        temproary.setCountryNameUz(countryName("uz", country));
        if (nibbdResponse.getTempRegion().equals(""))
            throw new IllegalArgumentException("Temp region is empty");
        final Region region = regionService.findByBankCode(Long.parseLong(nibbdResponse.getTempRegion()));
        if (null == region)
            throw new CustomException(env.getProperty("ex8881", Integer.class), messageSource.getMessage("message_8881", null, locale));
        temproary.setRegion(region.getId());
        temproary.setRegionName(regionName(locale.getLanguage(), region));
        temproary.setRegionNameUz(regionName("uz", region));
        if (nibbdResponse.getTempDistrict().equals(""))
            throw new IllegalArgumentException("Temp district is empty");
        final District district = districtService.findByBankCode(Long.parseLong(nibbdResponse.getTempDistrict()));
        if (null == district)
            throw new CustomException(env.getProperty("ex8882", Integer.class), messageSource.getMessage("message_8882", null, locale));
        temproary.setDistrict(district.getId());
        temproary.setDistrictName(districtName(locale.getLanguage(), district));
        temproary.setDistrictNameUz(districtName("uz", district));
        temproary.setAddress(nibbdResponse.getTempAddress());
        temproary.setHome(nibbdResponse.getTempHouse());
        temproary.setFlat(nibbdResponse.getTempFlat());
        temproary.setBlock(nibbdResponse.getTempBlock());
        temproary.setLiveFromDate("");
        temproary.setLiveTillDate("");
        temproary.setAdditional("");
        list.add(temproary);
        return list;
    }

    private String countryName(final String lang, final Country country) {
        final String countryName;
        switch (lang) {
            case "ru":
                countryName = country.getRus();
                break;
            case "lat":
                countryName = country.getLat();
                break;
            case "en":
                countryName = country.getEng();
                break;
            default:
                countryName = country.getUzb();
        }
        return countryName;
    }

    private String regionName(final String lang, final Region region) {
        final String regionName;
        switch (lang) {
            case "ru":
                regionName = region.getRus();
                break;
            case "lat":
                regionName = region.getLat();
                break;
            case "en":
                regionName = region.getEng();
                break;
            default:
                regionName = region.getUzb();
        }
        return regionName;
    }

    private String districtName(final String lang, final District district) {
        final String districtName;
        switch (lang) {
            case "ru":
                districtName = district.getRus();
                break;
            case "en":
                districtName = district.getEng();
                break;
            case "lat":
                districtName = district.getLat();
                break;
            default:
                districtName = district.getUzb();
        }
        return districtName;
    }

}
