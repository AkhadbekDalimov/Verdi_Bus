package uz.asbt.digid.digidservice.builder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.models.entity.client.ModelMRZ;
import uz.asbt.digid.common.models.entity.client.ModelPerson;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassport;
import uz.asbt.digid.common.models.rest.PhysicalResponseBody;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;
import uz.asbt.digid.digidservice.model.entity.Country;
import uz.asbt.digid.digidservice.model.entity.DocType;
import uz.asbt.digid.digidservice.model.entity.Nation;
import uz.asbt.digid.digidservice.model.entity.Sex;
import uz.asbt.digid.digidservice.service.CountryService;
import uz.asbt.digid.digidservice.service.DocTypeService;
import uz.asbt.digid.digidservice.service.NationService;
import uz.asbt.digid.digidservice.service.PersonStatusService;
import uz.asbt.digid.digidservice.service.SexService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Component
public class ModelPersonBuilder {

    @Autowired
    private SexService sexService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DocTypeService docTypeService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PersonStatusService personStatusService;

    public ModelPerson build(
            final PhysicalResponseBody nibbdResponse,
            final ModelPersonPassport personPassport,
            final ModelMRZ mrz,
            final Locale locale) {
        log.info("Locale language is {}", locale.getLanguage());
        log.info("Begin build");
        final String country;
        if (mrz != null) {
            country = (!mrz.getLine1().equals("") && mrz.getLine1().length() > 6) ? mrz.getLine1().substring(2, 5) : "UZB";
        } else {
            country = "UZB";
        }
        log.info("Country is {}", country);
        final ModelPerson person = new ModelPerson();
        person.setPinpp(nibbdResponse.getPin());
        person.setSurnameL(nibbdResponse.getLastName());
        person.setNameL(nibbdResponse.getFirstName());
        person.setPatronymL(nibbdResponse.getPatronym());
        person.setSurnameE(nibbdResponse.getSurname());
        person.setNameE(nibbdResponse.getGivenname());
        person.setBirthDate(nibbdResponse.getBirthDate());
        person.setSex(nibbdResponse.getSex());

        if (nibbdResponse.getSex() != null && !nibbdResponse.getSex().equals("")) {
            person.setSexName(sexName(locale.getLanguage(), Long.parseLong(nibbdResponse.getSex())));
            person.setSexNameUz(sexName("uz", Long.parseLong(nibbdResponse.getSex())));
        }

        if (!nibbdResponse.getBirthCountry().equals("")) {
            person.setBirthCountry(nibbdResponse.getBirthCountry());
            person.setBirthCountryName(countryName(locale.getLanguage(), Long.parseLong(nibbdResponse.getBirthCountry())));
            person.setBirthCountryNameUz(countryName("uz", Long.parseLong(nibbdResponse.getBirthCountry())));
        }

        if (nibbdResponse.getBirthPlace() != null && !nibbdResponse.getBirthPlace().equals("")) {
            person.setBirthPlace(nibbdResponse.getBirthPlace());
        }

        if (nibbdResponse.getNationality() != null && !nibbdResponse.getNationality().equals("")) {
            person.setNationality(nibbdResponse.getNationality());
        }

        if (nibbdResponse.getNationalityDesc() != null && !nibbdResponse.getNationalityDesc().equals("")) {
            person.setNationalityName(nationName(locale.getLanguage(), nibbdResponse.getNationalityDesc())); //TODO справочник
            person.setNationalityNameUz(nationName("uz", nibbdResponse.getNationalityDesc())); //TODO справочник
        }

        String documentType = personPassport.getDocumentType();
        person.setDocumentType(documentType);

        if (documentType != null && !personPassport.getDocumentNumber().startsWith("FA")) {

            documentType = documentType.concat("UZB");
        }

        log.info("Document type is: {}", documentType);

        if (documentType != null && !documentType.equals("")) {
            person.setDocumentTypeName(docTypeName(locale.getLanguage(), documentType));
            person.setDocumentTypeUz(docTypeName("uz", documentType));
        }

        person.setDocumentSerialNumber(nibbdResponse.getPassportSeria().concat(nibbdResponse.getPassportNumber()));
        person.setDocumentDateIssue(nibbdResponse.getDateIssue());
        person.setDocumentDateValid(nibbdResponse.getDateExpiry());
        person.setDocumentIssuedBy(nibbdResponse.getGivePlaceName());
        person.setPersonStatus(Integer.parseInt(nibbdResponse.getSubjectState()));
        person.setPersonStatusValue(personStatusService.getStatusName(Integer.parseInt(nibbdResponse.getSubjectState()), locale.getLanguage()));
        long countryBankCode = 860;
        if (nibbdResponse.getCitizenship().equals("")) {
            countryBankCode = countryService.findByIcaoCode(country).getBankCode();
        }
        log.info("Begin getting citizenship by id: {}", nibbdResponse.getCitizenship());
        log.info("bank code id: {}",  countryBankCode);
        person.setCitizenship(nibbdResponse.getCitizenship().equals("") ? String.valueOf(countryBankCode) :
                String.valueOf(countryService.findById(Long.parseLong(nibbdResponse.getCitizenship())).getBankCode()));
        person.setCitizenshipName(countryName(locale.getLanguage(), Long.parseLong(person.getCitizenship()))); //TODO
        person.setCitizenshipNameUz(countryName("uz", Long.parseLong(person.getCitizenship()))); //TODO
        person.setAdditional("");
        log.info("End build");
        return person;
    }

    @SneakyThrows(ParseException.class)
    public ModelPerson build(
            final PersonInfoResponse infoResponse,
            final ModelPersonPassport personPassport,
            final ModelMRZ mrz,
            final Locale locale) {
        log.info("Locale language is {}", locale.getLanguage());
        log.info("Begin build");
        String country;
        if (mrz != null)
            country = mrz.getLine1().substring(2, 5);
        else
            country = "UZB";
        log.info("Country is {}", country);
        final ModelPerson person = new ModelPerson();
        person.setPinpp(personPassport.getPinpp());
        person.setSurnameL(infoResponse.getSurnameLatin());
        person.setNameL(infoResponse.getNameLatin());
        person.setPatronymL(infoResponse.getPatronymLatin());
        person.setSurnameE(infoResponse.getSurnameEngl());
        person.setNameE(infoResponse.getNameEngl());

        final SimpleDateFormat sdfToBirth = new SimpleDateFormat("dd.MM.yyyy");
        sdfToBirth.setLenient(false);
        final SimpleDateFormat sdfFromBirth = new SimpleDateFormat("yyyy-MM-dd");
        sdfFromBirth.setLenient(false);


        final Date birthDate = sdfFromBirth.parse(infoResponse.getBirthDate());
        person.setBirthDate(sdfToBirth.format(birthDate));

        person.setSex(sexBankCode(Long.parseLong(infoResponse.getSex())));
        person.setSexName(sexNameById(locale.getLanguage(), infoResponse.getSex()));
        person.setSexNameUz(sexName("uz", Long.parseLong(infoResponse.getSex())));
        log.info("Begin setting country info");
        if (!infoResponse.getBirthCountryId().equals("")) {
            log.info("Country is not empty");
            final String bankCode = countryBankCode(Long.parseLong(infoResponse.getBirthCountryId()));
            log.info("BankCode is {}", bankCode);
            person.setBirthCountry(bankCode);
            person.setBirthCountryName(countryName(locale.getLanguage(), Long.parseLong(bankCode)));
            person.setBirthCountryNameUz(countryName("uz", Long.parseLong(bankCode)));
        }
        person.setBirthPlace(infoResponse.getBirthPlace());
        log.info("nation is {}", infoResponse.getNationalityId());
        final Nation nation = nationService.findById(Long.parseLong(infoResponse.getNationalityId()));
        log.info("nation is {}", nation);
        person.setNationality(nation.getBankCode());
        person.setNationalityName(nationString(locale.getLanguage(), nation));
        person.setNationalityNameUz(nation.getUzb());
        String documentType = personPassport.getDocumentType();
        person.setDocumentType(documentType);

        if (!personPassport.getDocumentNumber().startsWith("FA")) {
            documentType = documentType.concat("UZB");
        }
        log.info("Document type is: {}", documentType);
        person.setDocumentTypeName(docTypeName(locale.getLanguage(), documentType));
        person.setDocumentTypeUz(docTypeName("uz", documentType));
        person.setDocumentSerialNumber(infoResponse.getDocument());

        final SimpleDateFormat sdfTo = new SimpleDateFormat("dd.MM.yyyy");
        sdfToBirth.setLenient(false);
        final SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-dd-MM");
        sdfFromBirth.setLenient(false);

        final Date issueDate = sdfFrom.parse(infoResponse.getDateBeginDocument());
        person.setDocumentDateIssue(sdfTo.format(issueDate));

        final Date expiryDate = sdfFrom.parse(infoResponse.getDateEndDocument());
        person.setDocumentDateValid(sdfTo.format(expiryDate));

        person.setDocumentIssuedBy(infoResponse.getDocGivePlace());
        person.setPersonStatus(Integer.parseInt(infoResponse.getLivestatus()));
        person.setPersonStatusValue(personStatusService.getStatusName(Integer.parseInt(infoResponse.getLivestatus()), locale.getLanguage()));
        long countryBankCode = 860;

        log.info("citizenship by: {}", infoResponse.getCitizenship());
        if (infoResponse.getCitizenship().equals("")) {
            countryBankCode = countryService.findByIcaoCode(country).getBankCode();
        }
        log.info("countryBankCode by citizenship id: {}", countryBankCode);
        log.info("Begin getting citizenship by id: {}", infoResponse.getCitizenship());
        person.setCitizenship(infoResponse.getCitizenship().equals("") ? String.valueOf(countryBankCode) :
                String.valueOf(countryService.findById(Long.parseLong(infoResponse.getCitizenship())).getBankCode()));
        person.setCitizenshipName(countryName(locale.getLanguage(), Long.parseLong(String.valueOf(countryBankCode)))); //TODO
        person.setCitizenshipNameUz(countryName("uz", Long.parseLong(String.valueOf(countryBankCode)))); //TODO
        person.setAdditional("");
        log.info("End build");
        return person;
    }

    private String nationString(final String lang, final Nation nation) {
        final String nationName;
        switch (lang) {
            case "ru":
                nationName = nation.getRus();
                break;
            case "uz":
                nationName = nation.getUzb();
                break;
            case "lat":
                nationName = nation.getLat();
                break;
            case "en":
                nationName = nation.getEng();
                break;
            default:
                nationName = nation.getRus();
        }
        return nationName;
    }

    private String nationName(final String lang, final String name) {
        String nationName;
        log.info("Name is {}", name);
        try {
            final Nation nation = nationService.findByName(name);
            if (null == nation)
                throw new Exception("Can't get nation");
            switch (lang) {
                case "ru":
                    nationName = nation.getRus();
                    break;
                case "uz":
                    nationName = nation.getUzb();
                    break;
                case "lat":
                    nationName = nation.getLat();
                    break;
                case "en":
                    nationName = nation.getEng();
                    break;
                default:
                    nationName = nation.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            nationName = "";
        }
        return nationName;
    }

    private String docTypeName(final String lang, final String icaoCode) {
        String docTypeName;
        try {
            log.info("Document icaoCode is: {}", icaoCode);
            final DocType docType = docTypeService.findByIcaoCode(icaoCode);
            if (null == docType)
                throw new IllegalArgumentException("Can't get doc type");
            switch (lang) {
                case "ru":
                    docTypeName = docType.getRus();
                    break;
                case "uz":
                    docTypeName = docType.getUzb();
                    break;
                case "lat":
                    docTypeName = docType.getLat();
                    break;
                case "en":
                    docTypeName = docType.getEng();
                    break;
                default:
                    docTypeName = docType.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            docTypeName = "";
        }
        log.info("Document icaoCode is: {} {}", icaoCode, docTypeName);
        return docTypeName;
    }

    private String countryNameIcao(final String lang, final String icaoCode) {
        String countryName;
        log.info("country name icaoCode is: {}", icaoCode);
        try {
            final Country country = countryService.findByIcaoCode(icaoCode);
            if (null == country)
                throw new IllegalArgumentException("Can't get country");
            switch (lang) {
                case "ru":
                    countryName = country.getRus();
                    break;
                case "uz":
                    countryName = country.getUzb();
                    break;
                case "lat":
                    countryName = country.getLat();
                    break;
                case "en":
                    countryName = country.getEng();
                    break;
                default:
                    countryName = country.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            countryName = "";
        }
        return countryName;
    }

    private String countryBankCode(final long id) {
        return String.valueOf(countryService.findById(id).getBankCode());
    }

    private String countryName(final String lang, final long bankCode) {
        String countryName;
        try {
            final Country country = countryService.findByBankCode(bankCode);
            if (null == country)
                throw new IllegalArgumentException("Can't get country");
            switch (lang) {
                case "ru":
                    countryName = country.getRus();
                    break;
                case "uz":
                    countryName = country.getUzb();
                    break;
                case "lat":
                    countryName = country.getLat();
                    break;
                case "en":
                    countryName = country.getEng();
                    break;
                default:
                    countryName = country.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            countryName = "";
        }
        return countryName;
    }

    private String countryBankCode(final String lang, final long bankCode) {
        String bankCodeStr;
        try {
            final Country country = countryService.findById(bankCode);
            if (null == country)
                throw new IllegalArgumentException("Can't get country");
            bankCodeStr = String.valueOf(country.getBankCode());
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            bankCodeStr = "";
        }
        return bankCodeStr;
    }

    private String sexBankCode(final long id) {
        return String.valueOf(sexService.findById(id).getBankCode());
    }

    private String sexName(final String lang, final long bankCode) {
        String sexName;
        try {
            final Sex sex = sexService.findByBankCode(bankCode);
            switch (lang) {
                case "ru":
                    sexName = sex.getRus();
                    break;
                case "uz":
                    sexName = sex.getUzb();
                    break;
                case "lat":
                    sexName = sex.getLat();
                    break;
                case "en":
                    sexName = sex.getEng();
                    break;
                default:
                    sexName = sex.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            sexName = "";
        }
        return sexName;
    }

    private String sexNameById(final String lang, final String id) {
        String sexName;
        try {
            final Sex sex = sexService.findById(Long.parseLong(id));
            switch (lang) {
                case "ru":
                    sexName = sex.getRus();
                    break;
                case "uz":
                    sexName = sex.getUzb();
                    break;
                case "lat":
                    sexName = sex.getLat();
                    break;
                case "en":
                    sexName = sex.getEng();
                    break;
                default:
                    sexName = sex.getRus();
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            sexName = "";
        }
        return sexName;
    }
}
