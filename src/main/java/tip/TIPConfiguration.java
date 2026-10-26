package tip;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class TIPConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    // Path to the TIP's private signing key (JWK JSON). Service providers pin the matching
    // public key, so it must be stable across restarts.
    @NotEmpty
    private String signingKeyPath;

    @JsonProperty
    public String getSigningKeyPath() {
        return signingKeyPath;
    }

    @JsonProperty
    public void setSigningKeyPath(String signingKeyPath) {
        this.signingKeyPath = signingKeyPath;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
}