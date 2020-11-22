
package data;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "givenName",
    "familyName",
    "residentAddress",
    "residentCity",
    "residentState",
    "residentPostalCode",
    "residentCountry",
    "phoneNumber",
    "cardNumber",
    "birthDate",
    "email"
})
public class Attributes {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("givenName")
    private String givenName;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("familyName")
    private String familyName;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentAddress")
    private String residentAddress;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCity")
    private String residentCity;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentState")
    private String residentState;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentPostalCode")
    private String residentPostalCode;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCountry")
    private String residentCountry;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cardNumber")
    private String cardNumber;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("birthDate")
    private String birthDate;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("email")
    private String email;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("givenName")
    public String getGivenName() {
        return givenName;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("givenName")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("familyName")
    public String getFamilyName() {
        return familyName;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("familyName")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentAddress")
    public String getResidentAddress() {
        return residentAddress;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentAddress")
    public void setResidentAddress(String residentAddress) {
        this.residentAddress = residentAddress;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCity")
    public String getResidentCity() {
        return residentCity;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCity")
    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentState")
    public String getResidentState() {
        return residentState;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentState")
    public void setResidentState(String residentState) {
        this.residentState = residentState;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentPostalCode")
    public String getResidentPostalCode() {
        return residentPostalCode;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentPostalCode")
    public void setResidentPostalCode(String residentPostalCode) {
        this.residentPostalCode = residentPostalCode;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCountry")
    public String getResidentCountry() {
        return residentCountry;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("residentCountry")
    public void setResidentCountry(String residentCountry) {
        this.residentCountry = residentCountry;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cardNumber")
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cardNumber")
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }



}
