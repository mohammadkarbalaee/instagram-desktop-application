package application.datacomponents.directmessage;

public class Message
{
    private String sender;
    private String receiver;
    private String text;

    public Message(String sender, String receiver, String text)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
