package data;

public class AttributesBuilder {

    Attributes attributes = new Attributes();

    public Attributes build()
    {
        return attributes;
    }

    public AttributesBuilder givenName(String givenName)
    {
        attributes.setGivenName(givenName);
        return this;

    }

    public AttributesBuilder familyName(String familyName)
    {
        attributes.setFamilyName(familyName);
        return this;

    }

    public AttributesBuilder residentAddress(String residentAddress)
    {
        attributes.setResidentAddress(residentAddress);
        return this;

    }

    public AttributesBuilder residentCity(String residentCity)
    {
        attributes.setResidentCity(residentCity);
        return this;

    }

    public AttributesBuilder residentState(String residentState)
    {
        attributes.setResidentState(residentState);
        return this;

    }

    public AttributesBuilder residentPostalCode(String residentPostalCode)
    {
        attributes.setResidentPostalCode(residentPostalCode);
        return this;

    }

    public AttributesBuilder residentCountry(String residentCountry)
    {
        attributes.setResidentCountry(residentCountry);
        return this;

    }

    public AttributesBuilder phoneNumber(String phoneNumber)
    {
        attributes.setPhoneNumber(phoneNumber);
        return this;

    }

    public AttributesBuilder cardNumber(String cardNumber)
    {
        attributes.setCardNumber(cardNumber);
        return this;

    }

    public AttributesBuilder birthDate(String birthDate)
    {
        attributes.setBirthDate(birthDate);
        return this;

    }

    public AttributesBuilder email(String email)
    {
        attributes.setEmail(email);
        return this;

    }



}
