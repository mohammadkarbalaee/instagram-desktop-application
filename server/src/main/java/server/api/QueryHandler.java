package server.api;

import static server.api.AllQueries.*;
import java.io.IOException;

public class QueryHandler extends QueryPipeline
{
    private int levelCode;

    public void listen() throws IOException
    {
        levelCode = QueryPipeline.dataInputStream.readInt();
        action(toQuery(levelCode));
    }

    private AllQueries toQuery(int levelCode)
    {
        AllQueries query = null;
        switch (levelCode)
        {
            case 1: query = SEND_ACCOUNT;
        }
        System.out.println(query);
        return query;
    }

    private void action(AllQueries query) throws IOException
    {
        Response response = new Response();
        switch (query)
        {
            case SEND_ACCOUNT: response.respondingToClient(); break;
        }
    }
}
