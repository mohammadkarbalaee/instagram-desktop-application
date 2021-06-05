package application.signup;

import api.Request;
import com.google.gson.Gson;

import java.io.IOException;

public class SignUpper
{
    private final SignUpAuthenticator
            signUpAuthenticator = new SignUpAuthenticator();
    private User
            user;
    private final Gson gson = new Gson();

    public SignUpper(User user)
    {
        this.user = user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Request makeRequest() throws IOException
    {
        if
        (
                signUpAuthenticator.checkPasswordStrength(user.getPassword())
                &&
                signUpAuthenticator.isEmailValid(user.getEmail())
                &&
                signUpAuthenticator.isNew(user.getUserName())
        )
        {
            return new Request("SEND_USER",gson.toJson(user));
        }
        return null;
    }
}
