//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 06:42:35 PM UZT 
//


package uz.asbt.digid.mipservice.types.moi.getStreetByStreetId;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the megaware.mediate.ips.moi.getstreetbystreetid.getstreetbystreetid package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: megaware.mediate.ips.moi.getstreetbystreetid.getstreetbystreetid
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStreetByStreetId }
     * 
     */
    public GetStreetByStreetId createGetStreetByStreetId() {
        return new GetStreetByStreetId();
    }

    /**
     * Create an instance of {@link GetStreetByStreetIdResponse }
     *
     */
    public GetStreetByStreetIdResponse createGetStreetByStreetIdResponse() {
        return new GetStreetByStreetIdResponse();
    }

    /**
     * Create an instance of {@link GetStreetByStreetId.AuthInfo }
     *
     */
    public GetStreetByStreetId.AuthInfo createGetStreetByStreetIdAuthInfo() {
        return new GetStreetByStreetId.AuthInfo();
    }

    /**
     * Create an instance of {@link GetStreetByStreetIdResponse.Return }
     *
     */
    public GetStreetByStreetIdResponse.Return createGetStreetByStreetIdResponseReturn() {
        return new GetStreetByStreetIdResponse.Return();
    }

}