package client.api;

import java.io.IOException;

public class Request extends QueryPipeline
{
    public void askingServer(AllQueries query) throws IOException
    {
        QueryPipeline.dataOutputStream.writeInt(query.getLevelCode());
    }

}
