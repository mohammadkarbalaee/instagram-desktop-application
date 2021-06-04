package application;

import api.Handler;
import api.Request;

import java.io.IOException;

public class SignUpAuthenticator
{
    private Handler handler;

    public boolean checkPasswordStrength(String password)
    {
        String passwordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[?!#$%^&*()_+])[a-zA-Z0-9?!#$%^&*()_+]{8,}";
        return password.matches(passwordRegex);
    }

    public boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isNew(String username) throws IOException
    {
        Request checkIsNew = new Request("IS_NEW",username);
        handler = new Handler(checkIsNew);
        handler.send();
        return handler.receiveIS_NEWresponse();
    }

}
