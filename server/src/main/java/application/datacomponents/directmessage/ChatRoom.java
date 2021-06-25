package application.datacomponents.directmessage;

public class ChatRoom
{
    private String sender;
    private String receiver;
    private String chatroomTableName;

    public ChatRoom(String sender, String receiver)
    {
        this.sender = sender;
        this.receiver = receiver;
        chatroomTableName = sender + "_" + receiver;
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

    public String getChatroomTableName()
    {
        return chatroomTableName;
    }

    public void setChatroomTableName(String chatroomTableName)
    {
        this.chatroomTableName = chatroomTableName;
    }
}
