package tip;


import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.jwk.JWK;
import data.*;


import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import util.JWUtil;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TIPResource {
    private final String template;
    private final String defaultName;

    private final VerificationManager verificationManager;


    public TIPResource(String template, String defaultName, VerificationManager verificationManager) {
        this.template = template;
        this.defaultName = defaultName;
        this.verificationManager = verificationManager;
    }

    @POST
    @Timed
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public VerifyResponse verify(VerifyRequest request) {

        if (request == null || request.getAttributes() == null || request.getClientPublicKey() == null) {
            throw new BadRequestException("attributes and clientPublicKey are required");
        }

        VerifyResponse verifyResponse = new VerifyResponse();

        Token token = verificationManager.verify(request.getAttributes(), parseClientPublicKey(request.getClientPublicKey()));
        if (token == null) {
            throw new BadRequestException("could not process request");
        }
        verifyResponse.setToken(token);
        return verifyResponse;

    }

    @POST
    @Timed
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenResponse token(TokenRequest request) {

        if (request == null || request.getToken() == null || request.getToken().getToken() == null) {
            throw new BadRequestException("token is required");
        }
        return verificationManager.get(request.getToken());

    }

    public static JWK parseClientPublicKey(String json) {
        return JWUtil.parsePublicECKey(json)
                .orElseThrow(() -> new BadRequestException("clientPublicKey must be a public EC JWK"));
    }

}
