package wallet;

import data.*;
import util.Connection;
import util.JSONUtil;

import java.util.Optional;

public class FilerConnector {

    private Connection connection;
    public FilerConnector(String tipUrl) {
        connection = new Connection(tipUrl);

    }

    public FileUpdateResponse verify(FileUpdateRequest request)
    {
        Optional<String>  result = connection.post(JSONUtil.toJSON(request),"update");
        if (result.isPresent())
        {
            FileUpdateResponse response = (FileUpdateResponse)JSONUtil.fromJSON(result.get(),FileUpdateResponse.class);
            return response;
        }

        return null;
    }

    public FileRetrieveResponse retrieve(TokenRequest tokenRequest)
    {
        Optional<String>  result = connection.post(JSONUtil.toJSON(tokenRequest),"retrieve");
        if (result.isPresent())
        {
            FileRetrieveResponse response = (FileRetrieveResponse)JSONUtil.fromJSON(result.get(),FileRetrieveResponse.class);
            return response;
        }

        return null;
    }

}
