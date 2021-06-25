package sample.backend.application.signuplogin;

import sample.backend.api.ApiHandler;
import sample.backend.api.Request;

import java.io.IOException;

public class SignUpAuthenticator
{
    public boolean checkPasswordStrength(String password)
    {
        String passwordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[?!#$%^&@*()_+])[a-zA-Z0-9?!#$%^&@*()_+]{8,}";
        return password.matches(passwordRegex);
    }

    public boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isNew(String username) throws IOException
    {
        Request
                checkIsNew = new Request("IS_NEW",username);
        ApiHandler
                apiHandler = new ApiHandler(checkIsNew);
        apiHandler.sendRequest();
        return apiHandler.receiveIsNew();
    }

    public boolean isPasswordMatch(String username,String givenPassword) throws IOException
    {
        Request getPassword = new Request("GET_PASSWORD",username);
        ApiHandler apiHandler = new ApiHandler(getPassword);
        apiHandler.sendRequest();
        String truePassword = apiHandler.receivePassword();
        if (givenPassword.equals(truePassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
