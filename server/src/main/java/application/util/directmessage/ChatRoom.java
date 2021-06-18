package application.util.directmessage;

public class ChatRoom
{
    private String sender;
    private String receiver;
    private String chatRoomTableName;

    public ChatRoom(String sender, String receiver)
    {
        this.sender = sender;
        this.receiver = receiver;
        chatRoomTableName = sender + "_" + receiver;
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

    public String getChatRoomTableName()
    {
        return chatRoomTableName;
    }

    public void setChatRoomTableName(String chatRoomTableName)
    {
        this.chatRoomTableName = chatRoomTableName;
    }
}
