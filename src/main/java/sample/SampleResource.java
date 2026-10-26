package sample;


import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.jwk.JWK;
import data.ServiceRequest;
import data.ServiceResponse;
import data.VerifiedData;
import shareablemerkletree.MerkleTree;
import util.MerkleTreeUtil;
import util.SignUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {
    private final String template;
    private final String defaultName;

    // The TIP's public signing key, configured out of band by the operator of this service.
    // The public key carried inside the request is never trusted: anyone can sign data with
    // their own key and attach the matching public key.
    private final JWK tipPublicKey;


    public SampleResource(String template, String defaultName, JWK tipPublicKey) {
        this.template = template;
        this.defaultName = defaultName;
        if (tipPublicKey == null || tipPublicKey.isPrivate()) {
            throw new IllegalArgumentException("A TIP public signing key (without private part) is required");
        }
        this.tipPublicKey = tipPublicKey;
    }


    @POST
    @Timed
    @Path("/service")
    @Produces(MediaType.APPLICATION_JSON)
    public ServiceResponse service(ServiceRequest request) {

        if (request == null || request.getVerifiedData() == null) {
            throw new BadRequestException("verifiedData is required");
        }
        VerifiedData<String> data = request.getVerifiedData();
        if (data.getElements() == null || data.getSignature() == null) {
            throw new BadRequestException("elements and signature are required");
        }

        boolean result = SignUtil.verify(data.getElements(), data.getSignature(), tipPublicKey);

        if (result) {
            // only parse the tree once the TIP signature has been verified
            MerkleTree tree = MerkleTreeUtil.fromJSON(data.getElements());
            result = tree != null;
        }

        ServiceResponse response = new ServiceResponse();

        response.setMessage("Result of signature verification is " + result);

        return response;


    }


}
