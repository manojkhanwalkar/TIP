package sample;


import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.jwk.JWK;
import data.ServiceRequest;
import data.ServiceResponse;
import data.VerifiedData;
import shareablemerkletree.MerkleTree;
import util.MerkleTreeUtil;
import util.SignUtil;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {
    private final String template;
    private final String defaultName;



    public SampleResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;



    }


    @POST
    @Timed
    @Path("/service")
    @Produces(MediaType.APPLICATION_JSON)
    public ServiceResponse service(ServiceRequest request) {

        VerifiedData data = request.getVerifiedData();

       MerkleTree tree =  MerkleTreeUtil.fromJSON(data.getElements());

        boolean result = false;
        try {
            result = SignUtil.verify(data.getElements(),data.getSignature(), JWK.parse(data.getPublicKey()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ServiceResponse response = new ServiceResponse();

        response.setMessage("Result of signature verification is " + result);

        return response;


    }











}
