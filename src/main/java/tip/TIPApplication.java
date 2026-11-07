package tip;


import com.nimbusds.jose.jwk.JWK;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import util.JWUtil;
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
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(true)));

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
                configuration.getDefaultName(),
                new VerificationManager(loadSigningKey(configuration.getSigningKeyPath()))
        );


        environment.jersey().register(resource);


}


    private static JWK loadSigningKey(String path) {
        JWK key = JWUtil.readJWKFromFile(path);
        if (key == null || !key.isPrivate()) {
            throw new IllegalStateException("Could not read a private TIP signing key from " + path);
        }
        return key;
    }

}
