package tip;


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
public class TIPResource {
    private final String template;
    private final String defaultName;



    public TIPResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;



    }

    VerificationManager verificationManager = new VerificationManager();

    @POST
    @Timed
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public VerifyResponse verify(VerifyRequest request) {

        VerifyResponse verifyResponse = new VerifyResponse();

        try {
            Token token = verificationManager.verify(request.getAttributes(), JWK.parse(request.getClientPublicKey()));
            verifyResponse.setToken(token);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return verifyResponse;

    }

    @POST
    @Timed
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenResponse token(TokenRequest request) {

        return verificationManager.get(request.getToken());

    }

    private String JWKToJSON(List<JWK> list)
    {

        JWKSet set = new JWKSet(list);
        return set.toJSONObject().toJSONString();
    }








}
