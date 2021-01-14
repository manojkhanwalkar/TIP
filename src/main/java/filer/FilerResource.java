package filer;


import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import data.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.List;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FilerResource {
    private final String template;
    private final String defaultName;




    public FilerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;



    }

    FilerManager filerManager = new FilerManager();

    @POST
    @Timed
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public FileUpdateResponse update(FileUpdateRequest request) {

        FileUpdateResponse fileUpdateResponse = new FileUpdateResponse();

        try {
            Token token = filerManager.update(request.getBase64FileContents(), request.getBase64FileContents(),JWK.parse(request.getClientPublicKey()));
            fileUpdateResponse.setToken(token);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fileUpdateResponse;

    }

    @POST
    @Timed
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public FileRetrieveResponse retrieve(TokenRequest request) {

        return null; //TODO = verificationManager.get(request.getToken());

    }

    private String JWKToJSON(List<JWK> list)
    {

        JWKSet set = new JWKSet(list);
        return set.toJSONObject().toJSONString();
    }








}
