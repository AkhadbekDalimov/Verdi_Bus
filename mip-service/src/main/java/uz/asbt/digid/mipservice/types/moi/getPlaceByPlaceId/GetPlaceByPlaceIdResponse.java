//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.06 at 06:42:35 PM UZT 
//


package uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId;

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
 *         &lt;element name="return" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="sp_name00" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="sp_name01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="sp_name02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="sp_name03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="sp_name04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="sp_name05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "_return"
})
@XmlRootElement(name = "getPlaceByPlaceIdResponse")
public class GetPlaceByPlaceIdResponse {

    @XmlElement(name = "return")
    protected GetPlaceByPlaceIdResponse.Return _return;

    /**
     * Gets the value of the return property.
     *
     * @return
     *     possible object is
     *     {@link GetPlaceByPlaceIdResponse.Return }
     *
     */
    public GetPlaceByPlaceIdResponse.Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value
     *     allowed object is
     *     {@link GetPlaceByPlaceIdResponse.Return }
     *
     */
    public void setReturn(GetPlaceByPlaceIdResponse.Return value) {
        this._return = value;
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
     *         &lt;element name="sp_name00" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="sp_name01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="sp_name02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="sp_name03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="sp_name04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="sp_name05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "spName00",
        "spName01",
        "spName02",
        "spName03",
        "spName04",
        "spName05"
    })
    public static class Return {

        @XmlElement(name = "sp_name00")
        protected String spName00;
        @XmlElement(name = "sp_name01")
        protected String spName01;
        @XmlElement(name = "sp_name02")
        protected String spName02;
        @XmlElement(name = "sp_name03")
        protected String spName03;
        @XmlElement(name = "sp_name04")
        protected String spName04;
        @XmlElement(name = "sp_name05")
        protected String spName05;

        /**
         * Gets the value of the spName00 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName00() {
            return spName00;
        }

        /**
         * Sets the value of the spName00 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName00(String value) {
            this.spName00 = value;
        }

        /**
         * Gets the value of the spName01 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName01() {
            return spName01;
        }

        /**
         * Sets the value of the spName01 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName01(String value) {
            this.spName01 = value;
        }

        /**
         * Gets the value of the spName02 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName02() {
            return spName02;
        }

        /**
         * Sets the value of the spName02 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName02(String value) {
            this.spName02 = value;
        }

        /**
         * Gets the value of the spName03 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName03() {
            return spName03;
        }

        /**
         * Sets the value of the spName03 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName03(String value) {
            this.spName03 = value;
        }

        /**
         * Gets the value of the spName04 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName04() {
            return spName04;
        }

        /**
         * Sets the value of the spName04 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName04(String value) {
            this.spName04 = value;
        }

        /**
         * Gets the value of the spName05 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpName05() {
            return spName05;
        }

        /**
         * Sets the value of the spName05 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpName05(String value) {
            this.spName05 = value;
        }

        @Override
        public String toString() {
            return "Return{" +
                    "spName00='" + spName00 + '\'' +
                    ", spName01='" + spName01 + '\'' +
                    ", spName02='" + spName02 + '\'' +
                    ", spName03='" + spName03 + '\'' +
                    ", spName04='" + spName04 + '\'' +
                    ", spName05='" + spName05 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetPlaceByPlaceIdResponse{" +
                "_return=" + _return +
                '}';
    }
}
