package server.api;

public enum AllQueries
{
    SEND_ACCOUNT(1),
    GET_ACCOUNT(2);

    private final int levelCode;

    AllQueries(int levelCode)
    {
        this.levelCode = levelCode;
    }

    public int getLevelCode()
    {
        return this.levelCode;
    }

}
