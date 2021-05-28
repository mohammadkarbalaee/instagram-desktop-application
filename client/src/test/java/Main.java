import application.User;
import client.api.AllQueries;
import client.api.QueryPipeline;
import client.api.Request;

import java.io.IOException;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("localhost",8080);
        QueryPipeline queryPipeline = new QueryPipeline(socket);
        User user = new User("mamad","muhammad@gmail.com","1234");
        Request request = new Request(AllQueries.SEND_ACCOUNT,user);
        queryPipeline.sendRequest(request);
    }
}