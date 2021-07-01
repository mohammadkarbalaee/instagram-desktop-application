package sample.backend.application.signuplogin;

import sample.backend.api.Request;

import java.io.IOException;

import static sample.frontend.ClientRunner.getGson;

/**
 * @author Muhammad Karbalaee Shabani
 * a class in with methods in charge of validation and finally loggin in a user
 */
public class SignUpperLogInner
{
    private final SignUpAuthenticator signUpAuthenticator = new SignUpAuthenticator();
    private User user;

    public SignUpperLogInner(User user)
    {
        this.user = user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Request makeRequest()
    {
        return new Request("SEND_USER",getGson().toJson(user));
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