package sample;


import com.nimbusds.jose.jwk.JWK;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class SampleApplication extends Application<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "Slacker Application";
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(true)));

    }

    @Override
    public void run(SampleConfiguration configuration,
                    Environment environment) {
        final SampleResource resource = new SampleResource(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                loadTipPublicKey(configuration.getTipPublicKeyPath())
        );


        environment.jersey().register(resource);


}


    private static JWK loadTipPublicKey(String path) {
        JWK key = util.JWUtil.readJWKFromFile(path);
        if (key == null) {
            throw new IllegalStateException("Could not read TIP public key from " + path);
        }
        return key.toPublicJWK();
    }

}
