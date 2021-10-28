package uz.asbt.digid.digidservice.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.ModelAddress;
import uz.asbt.digid.common.models.entity.client.ModelAddressAnswere;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.digidservice.model.entity.Country;
import uz.asbt.digid.digidservice.model.entity.District;
import uz.asbt.digid.digidservice.model.entity.Region;
import uz.asbt.digid.digidservice.service.CountryService;
import uz.asbt.digid.digidservice.service.DistrictService;
import uz.asbt.digid.digidservice.service.RegionService;

import java.util.Locale;

@Component
public class ModelAddressBuilder {

    private static final Logger log = LoggerFactory.getLogger(ModelAddressBuilder.class);

    private final CountryService countryService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final Environment env;
    private final MessageSource messageSource;
    private final ErrorService error;
    @Autowired
    public ModelAddressBuilder(
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

    public ModelAddressAnswere build(final PhysicalResponseBody nibbdResponse, final Locale locale) {
        log.info("Begin build address");
        final ModelAddressAnswere modelAddressAnswere = new ModelAddressAnswere();
        final Answere answere = new Answere();
        try {
            final ModelAddress address = parseAddress(nibbdResponse, locale);
            answere.setAnswereId(error.getErrorCode("ok"));
            answere.setAnswereComment(error.getErrorMessage("message_0"));
            modelAddressAnswere.setAnswere(answere);
            modelAddressAnswere.setModelAddress(address);
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            answere.setAnswereId(env.getProperty("ex8000", Integer.class));
            answere.setAnswereComment(messageSource.getMessage("message_8000", null, locale));
        }
        modelAddressAnswere.setAnswere(answere);
        log.info("End build address");
        return modelAddressAnswere;
    }

    /**
     * Build Address coming from MIP
     * @param addressResponse
     * @param locale
     * @return
     */
    public ModelAddressAnswere build(final GetAddressResponse addressResponse, final Locale locale) {
        log.info("Begin build address");
        final ModelAddressAnswere modelAddressAnswere = new ModelAddressAnswere();
        final Answere answere = new Answere();
        try {
            final ModelAddress address = parseAddress(addressResponse, locale);
            answere.setAnswereId(error.getErrorCode("ok"));
            answere.setAnswereComment(error.getErrorMessage("message_0"));
            modelAddressAnswere.setAnswere(answere);
            modelAddressAnswere.setModelAddress(address);
        } catch (final Exception ex) {
            if (ex instanceof CustomException) {
                log.error("CustomException: code: {}, message: {}", ((CustomException) ex).getCode(), ex.getMessage());
            } else {
                log.error(ex.getMessage());
            }
            answere.setAnswereId(env.getProperty("ex8000", Integer.class));
            answere.setAnswereComment(messageSource.getMessage("message_8000", null, locale));
        }
        modelAddressAnswere.setAnswere(answere);
        log.info("End build address");
        return modelAddressAnswere;
    }

    private ModelAddress parseAddress(final GetAddressResponse addressResponse, final Locale locale) {
        log.info("Begin parse address");
        final ModelAddress address = new ModelAddress();
        if (!addressResponse.getpPermanentAddress().getpCountry().equals("")
                && !addressResponse.getpPermanentAddress().getpCountry().equals("-1")) {
            final Country country = countryService.findById(Long.parseLong(addressResponse.getpPermanentAddress().getpCountry()));
            if (null == country)
                throw new CustomException(env.getProperty("ex8880", Integer.class), messageSource.getMessage("message_8880", null, locale));
            address.setCountryICAO(country.getIcaoCode());
            address.setCountry(country.getBankCode());
            address.setCountryName(countryName(locale.getLanguage(), country));
            address.setCountryNameUz(countryName("uz", country));
            final Region region = regionService.findById(Long.parseLong(addressResponse.getpPermanentAddress().getpRegion()));
            if (null == region)
                throw new CustomException(env.getProperty("ex8881", Integer.class), messageSource.getMessage("message_8881", null, locale));
            address.setRegion(region.getBankCode());
            address.setRegionName(regionName(locale.getLanguage(), region));
            address.setRegionNameUz(regionName("uz", region));
            final District district = districtService.findById(Long.parseLong(addressResponse.getpPermanentAddress().getpDistrict()));
            if (null != district) {
                address.setDistrict(district.getBankCode());
                address.setDistrictName(districtName(locale.getLanguage(), district));
                address.setDistrictNameUz(districtName("uz", district));
            }
            address.setAddress(addressResponse.getpPermanentAddress().getpAddress());
            address.setHouse(addressResponse.getpPermanentAddress().getpHouse());
            address.setFlat(addressResponse.getpPermanentAddress().getpFlat());
            address.setBlock(addressResponse.getpPermanentAddress().getpBlock());
            address.setLiveFromDate(addressResponse.getpPermanentAddress().getpRegdate());
        }
        log.info("End parse address");
        return address;
    }

    private ModelAddress parseAddress(final PhysicalResponseBody nibbdResponse, final Locale locale) {
        log.info("Begin parse address");
        final ModelAddress address = new ModelAddress();
        address.setKadastr(nibbdResponse.getKadastr());
        final Country country = countryService.findByBankCode(Long.parseLong(nibbdResponse.getDomicileCountry()));
        if (null == country) {
            throw new CustomException(env.getProperty("ex8880", Integer.class), messageSource.getMessage("message_8880", null, locale));
        }
        log.info("Country: {}", country.toString());
        address.setCountryICAO(country.getIcaoCode());
        address.setCountry(country.getBankCode());
        address.setCountryName(countryName(locale.getLanguage(), country));
        address.setCountryNameUz(countryName("uz", country));
        final Region region = regionService.findByBankCode(Long.parseLong(nibbdResponse.getDomicileRegion()));
        if (null == region) {
            throw new CustomException(env.getProperty("ex8881", Integer.class), messageSource.getMessage("message_8881", null, locale));
        }
        log.info("Region: {}", region.toString());
        address.setRegion(region.getBankCode());
        address.setRegionName(regionName(locale.getLanguage(), region));
        address.setRegionNameUz(regionName("uz", region));
        log.info("Domicile district: {}", nibbdResponse.getDomicileDistrict());
        if (nibbdResponse.getDomicileDistrict() != null && !nibbdResponse.getDomicileDistrict().equals("")) {
            log.info("District 1: {}", nibbdResponse.getDomicileDistrict());
            final District district = districtService.findByBankCode(Long.parseLong(nibbdResponse.getDomicileDistrict()));
            log.info("District 2: {}", district.toString());
            if (null != district) {
                log.info("District: {}", district.toString());
                address.setDistrict(district.getBankCode());
                address.setDistrictName(districtName(locale.getLanguage(), district));
                address.setDistrictNameUz(districtName("uz", district));
            }
        }
        address.setAddress(nibbdResponse.getDomicileAddress());
        address.setHouse(nibbdResponse.getDomicileHouse());
        address.setFlat(nibbdResponse.getDomicileFlat());
        address.setBlock(nibbdResponse.getDomicileBlock());
        address.setLiveFromDate("");
        log.info("End parse address");
        return address;
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
            case "lat":
                districtName = district.getLat();
                break;
            case "en":
                districtName = district.getEng();
                break;
            default:
                districtName = district.getUzb();
        }
        return districtName;
    }

}
