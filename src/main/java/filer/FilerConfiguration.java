package filer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class FilerConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    // Directory where the encrypted files are stored.
    @NotEmpty
    private String filesDirectory;

    // Maximum accepted size of the base64 encoded file contents, in characters.
    @Min(1)
    private int maxFileContentsLength = 10 * 1024 * 1024;

    @JsonProperty
    public String getFilesDirectory() {
        return filesDirectory;
    }

    @JsonProperty
    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    @JsonProperty
    public int getMaxFileContentsLength() {
        return maxFileContentsLength;
    }

    @JsonProperty
    public void setMaxFileContentsLength(int maxFileContentsLength) {
        this.maxFileContentsLength = maxFileContentsLength;
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