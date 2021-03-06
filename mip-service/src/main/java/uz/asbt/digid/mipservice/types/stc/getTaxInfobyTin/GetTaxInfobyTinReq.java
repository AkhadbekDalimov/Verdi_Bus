//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 06:42:35 PM UZT 
//


package uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tin" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lang" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AuthInfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="userSessionId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="WS_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="LE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "action",
    "tin",
    "lang",
    "authInfo"
})
@XmlRootElement(name = "getTaxInfobyTinReq")
public class GetTaxInfobyTinReq {

    @XmlElement(required = true)
    protected String action;
    @XmlElement(required = true)
    protected String tin;
    @XmlElement(required = true)
    protected String lang;
    @XmlElement(name = "AuthInfo", required = true)
    protected GetTaxInfobyTinReq.AuthInfo authInfo;

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the tin property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTin() {
        return tin;
    }

    /**
     * Sets the value of the tin property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTin(String value) {
        this.tin = value;
    }

    /**
     * Gets the value of the lang property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the authInfo property.
     *
     * @return
     *     possible object is
     *     {@link GetTaxInfobyTinReq.AuthInfo }
     *
     */
    public GetTaxInfobyTinReq.AuthInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * Sets the value of the authInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link GetTaxInfobyTinReq.AuthInfo }
     *
     */
    public void setAuthInfo(GetTaxInfobyTinReq.AuthInfo value) {
        this.authInfo = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="userSessionId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="WS_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="LE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "userSessionId",
        "wsid",
        "leid"
    })
    public static class AuthInfo {

        @XmlElement(required = true)
        protected String userSessionId;
        @XmlElement(name = "WS_ID", required = true)
        protected String wsid;
        @XmlElement(name = "LE_ID", required = true)
        protected String leid;

        /**
         * Gets the value of the userSessionId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUserSessionId() {
            return userSessionId;
        }

        /**
         * Sets the value of the userSessionId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUserSessionId(String value) {
            this.userSessionId = value;
        }

        /**
         * Gets the value of the wsid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWSID() {
            return wsid;
        }

        /**
         * Sets the value of the wsid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWSID(String value) {
            this.wsid = value;
        }

        /**
         * Gets the value of the leid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLEID() {
            return leid;
        }

        /**
         * Sets the value of the leid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLEID(String value) {
            this.leid = value;
        }

    }

}
