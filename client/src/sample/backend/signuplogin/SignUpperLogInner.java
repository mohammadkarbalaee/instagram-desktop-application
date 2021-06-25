package sample.backend.signuplogin;

import com.google.gson.Gson;
import sample.backend.api.Request;

import java.io.IOException;

public class SignUpperLogInner
{
    private final SignUpAuthenticator signUpAuthenticator = new SignUpAuthenticator();
    private User user;
    private final Gson gson = new Gson();

    public SignUpperLogInner(User user)
    {
        this.user = user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Request makeRequest() throws IOException
    {
        return new Request("SEND_USER",gson.toJson(user));
    }

    public boolean checkPasswordValidation()
    {
        return signUpAuthenticator.checkPasswordStrength(user.getPassword());
    }

    public boolean checkEmailValidation()
    {
        return signUpAuthenticator.isEmailValid(user.getEmail());
    }

    public boolean checkUserUniqueness() throws IOException
    {
        return signUpAuthenticator.isNew(user.getUserName());
    }



    public boolean isPasswordMatch() throws IOException
    {
        return signUpAuthenticator.isPasswordMatch(user.getUserName(), user.getPassword());
    }
}
