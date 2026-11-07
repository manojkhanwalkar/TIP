package filer;


import com.codahale.metrics.annotation.Timed;
import data.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Base64;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FilerResource {
    private final String template;
    private final String defaultName;

    private final FilerManager filerManager;
    private final int maxFileContentsLength;


    public FilerResource(String template, String defaultName, FilerManager filerManager, int maxFileContentsLength) {
        this.template = template;
        this.defaultName = defaultName;
        this.filerManager = filerManager;
        this.maxFileContentsLength = maxFileContentsLength;
    }

    @POST
    @Timed
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public FileUpdateResponse update(FileUpdateRequest request) {

        if (request == null || request.getBase64FileContents() == null
                || request.getFileName() == null || request.getClientPublicKey() == null) {
            throw new BadRequestException("base64FileContents, fileName and clientPublicKey are required");
        }
        if (request.getBase64FileContents().length() > maxFileContentsLength) {
            throw new BadRequestException("file too large");
        }
        try {
            Base64.getDecoder().decode(request.getBase64FileContents());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("base64FileContents is not valid base64");
        }
        if (request.getFileName().length() > 255) {
            throw new BadRequestException("fileName too long");
        }

        FileUpdateResponse fileUpdateResponse = new FileUpdateResponse();

        Token token = filerManager.update(request.getBase64FileContents(), request.getFileName(),
                util.JWUtil.parsePublicECKey(request.getClientPublicKey())
                        .orElseThrow(() -> new BadRequestException("clientPublicKey must be a public EC JWK")));
        if (token == null) {
            throw new BadRequestException("could not process request");
        }
        fileUpdateResponse.setToken(token);
        return fileUpdateResponse;

    }

    @POST
    @Timed
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public FileRetrieveResponse retrieve(TokenRequest request) {

        if (request == null || request.getToken() == null || !FilerManager.isValidToken(request.getToken().getToken())) {
            throw new BadRequestException("a valid token is required");
        }
        return filerManager.retrieve(request.getToken());

    }

}
