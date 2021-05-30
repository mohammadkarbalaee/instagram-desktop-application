package api;

public class Request
{
    private String label;
    private String serializedObject;

    public Request(String label,String serializedObject)
    {
        this.label = label;
        this.serializedObject = serializedObject;
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
