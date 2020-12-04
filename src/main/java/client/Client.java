package client;

import data.Attributes;
import data.AttributesBuilder;
import wallet.Wallet;

public class Client {

    public static void main(String[] args) {

        // creates a wallet
        // sends data to be verified to the TIP server
        // gets the verified data from the tip server
        // presents the verified data to the service provider to get the service required.

        Wallet wallet = Wallet.create("https://localhost:8280/");
        AttributesBuilder attributesBuilder = new AttributesBuilder();

        Attributes attributes = attributesBuilder.birthDate("1980-1-1").cardNumber("1588213088725991").email("ttt@ttt.com").familyName("Hawking").givenName("Elm")
                .phoneNumber("+18002003456").residentAddress("Univ street").residentCity("Princeton").residentCountry("USA")
                .residentPostalCode("08836").residentState("CA").build();

        wallet.verify(attributes);

        wallet.getVerifiedData();


    }
}
