package tip;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;


public class TIPApplication extends Application<TIPConfiguration> {
    public static void main(String[] args) throws Exception {
        new TIPApplication().run(args);
    }

    @Override
    public String getName() {
        return "Slacker Application";
    }

    @Override
    public void initialize(Bootstrap<TIPConfiguration> bootstrap) {

        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());

        //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(TIPConfiguration configuration,
                    Environment environment) {
        final TIPResource resource = new TIPResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );


        environment.jersey().register(resource);


}

}