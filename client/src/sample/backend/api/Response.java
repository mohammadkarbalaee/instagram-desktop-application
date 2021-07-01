package sample.backend.api;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a Response object
 */
public class Response
{
    private String body;

    public Response(String body)
    {
        this.body = body;
    }

    public String getBody()
    {
        return body;
    }
}
