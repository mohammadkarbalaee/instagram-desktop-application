package api;

public class Request
{
    private String label;
    private String string;
    private String serializedObject;
    private String username;

    public Request(String label,String string)
    {
        this.label = label;
        if (label.equals("SEND_USER"))
        {
            this.serializedObject = string;
        }
        else if (label.equals("GET_USER"))
        {
            this.username = string;
        }
    }

    public String getLabel()
    {
        return label;
    }

    public String getSerializedObject()
    {
        return serializedObject;
    }
}
