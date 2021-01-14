package filer;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;


public class FilerApplication extends Application<FilerConfiguration> {
    public static void main(String[] args) throws Exception {
        new FilerApplication().run(args);
    }

    @Override
    public String getName() {
        return "Slacker Application";
    }

    @Override
    public void initialize(Bootstrap<FilerConfiguration> bootstrap) {

        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());


    }

    @Override
    public void run(FilerConfiguration configuration,
                    Environment environment) {
        final FilerResource resource = new FilerResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );


        environment.jersey().register(resource);


}

}