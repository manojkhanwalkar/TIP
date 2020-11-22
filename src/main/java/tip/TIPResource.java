package tip;


import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import data.VerifyRequest;
import data.VerifyResponse;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

        return null;

    }

    @POST
    @Timed
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public String token(String request) {

        return "To be implemented";

    }

    private String JWKToJSON(List<JWK> list)
    {

        JWKSet set = new JWKSet(list);
        return set.toJSONObject().toJSONString();
    }








}
