package sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class SampleConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    // Path to the TIP's public signing key (JWK JSON). Required: signatures are only
    // accepted if they verify against this key.
    @NotEmpty
    private String tipPublicKeyPath;

    @JsonProperty
    public String getTipPublicKeyPath() {
        return tipPublicKeyPath;
    }

    @JsonProperty
    public void setTipPublicKeyPath(String tipPublicKeyPath) {
        this.tipPublicKeyPath = tipPublicKeyPath;
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