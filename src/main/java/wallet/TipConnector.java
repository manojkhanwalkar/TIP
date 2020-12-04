package wallet;

import data.TokenRequest;
import data.TokenResponse;
import data.VerifyRequest;
import data.VerifyResponse;
import util.Connection;
import util.JSONUtil;

import java.util.Optional;

public class TipConnector {

    private Connection connection;
    public TipConnector(String tipUrl) {
        connection = new Connection(tipUrl);

    }

    public VerifyResponse verify(VerifyRequest request)
    {
        Optional<String>  result = connection.post(JSONUtil.toJSON(request),"verify");
        if (result.isPresent())
        {
            VerifyResponse response = (VerifyResponse)JSONUtil.fromJSON(result.get(),VerifyResponse.class);
            return response;
        }

        return null;
    }

    public TokenResponse token(TokenRequest tokenRequest)
    {
        Optional<String>  result = connection.post(JSONUtil.toJSON(tokenRequest),"token");
        if (result.isPresent())
        {
            TokenResponse response = (TokenResponse)JSONUtil.fromJSON(result.get(),TokenResponse.class);
            return response;
        }

        return null;
    }

}
