package uz.asbt.digid.mipservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Converters {

    @Bean(name = "getAddress")
    public Jaxb2Marshaller marshallerGetAddress() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getAddress");
        return marshaller;
    }

    @Bean(name = "getDistrictByDistrictId")
    public Jaxb2Marshaller marshallerGetDistrictByDistrictId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId");
        return marshaller;
    }

    @Bean(name = "getDistrictByRegionId")
    public Jaxb2Marshaller marshallerGetDistrictByRegionId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId");
        return marshaller;
    }

    @Bean(name = "getPlaceByPlaceId")
    public Jaxb2Marshaller marshallerGetPlaceByPlaceId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId");
        return marshaller;
    }

    @Bean(name = "getPlaceByDistrictId")
    public Jaxb2Marshaller marshallerGetPlaceByDistrictId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId");
        return marshaller;
    }

    @Bean(name = "getRegionByRegionId")
    public Jaxb2Marshaller marshallerGetRegionByRegionId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getRegionByRegionId");
        return marshaller;
    }

    @Bean(name = "getRegions")
    public Jaxb2Marshaller marshallerGetRegions() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getRegions");
        return marshaller;
    }

    @Bean(name = "getStreetByStreetId")
    public Jaxb2Marshaller marshallerGetStreetByStreetId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getStreetByStreetId");
        return marshaller;
    }

    @Bean(name = "getStreetsByDistrictId")
    public Jaxb2Marshaller marshallerGetStreetsByDistrictId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId");
        return marshaller;
    }

    @Bean(name = "getStreetsByPlaceId")
    public Jaxb2Marshaller marshallerGetStreetsByPlaceId() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId");
        return marshaller;
    }

    @Bean(name = "personDocInfoService")
    public Jaxb2Marshaller marshallerPersonDocInfoService() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.personDocInfoService");
        return marshaller;
    }

    @Bean(name = "getTaxInfoById")
    public Jaxb2Marshaller marshallerGetTaxInfoById() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.stc.getTaxInfobyId");
        return marshaller;
    }

    @Bean(name = "getTaxInfoByPin")
    public Jaxb2Marshaller marshallerGetTaxInfoByPin() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin");
        return marshaller;
    }

    @Bean(name = "getTaxInfoByTin")
    public Jaxb2Marshaller marshallerGetTaxInfoByTin() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin");
        return marshaller;
    }

}
