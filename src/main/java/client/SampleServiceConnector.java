package client;

import data.*;
import util.Connection;
import util.JSONUtil;

import java.util.Optional;

public class SampleServiceConnector {

    private Connection connection;
    public SampleServiceConnector(String tipUrl) {
        connection = new Connection(tipUrl);

    }

    public ServiceResponse service(ServiceRequest request)
    {
        Optional<String>  result = connection.post(JSONUtil.toJSON(request),"service");
        if (result.isPresent())
        {
            ServiceResponse response = (ServiceResponse)JSONUtil.fromJSON(result.get(),ServiceResponse.class);
            return response;
        }

        return null;
    }



}
