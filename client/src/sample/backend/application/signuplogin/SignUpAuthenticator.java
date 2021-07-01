package sample.backend.application.signuplogin;

import sample.backend.api.ApiHandler;
import sample.backend.api.Request;

import java.io.IOException;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a SignUpAuthenticator object
 */
public class SignUpAuthenticator
{
    /**
     * checks a password(in String format) with a regex.
     * @param password
     * @return whether this is a strong password or not
     */
    public boolean checkPasswordStrength(String password)
    {
        String passwordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[?!#$%^&@*()_+])[a-zA-Z0-9?!#$%^&@*()_+]{8,}";
        return password.matches(passwordRegex);
    }
    /**
     * checks an email(in String format) with a regex.
     * @param  email
     * @return whether this is a valid email address or not
     */
    public boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * connects to server and retrieves uniquness of a user
     * @param username
     * @return if this is a new user and can sign up or already exists and should login
     * @throws IOException
     */
    public boolean isNew(String username) throws IOException
    {
        Request
                checkIsNew = new Request("IS_NEW",username);
        ApiHandler
                apiHandler = new ApiHandler(checkIsNew);
        apiHandler.sendRequest();
        return apiHandler.receiveIsNew();
    }

    /**
     * is used for password validation while loggin in
     * @param username
     * @param givenPassword
     * @return if the password that someone entered for login is of the same number as the one
     * stored in database or not
     * @throws IOException
     */
    public boolean isPasswordMatch(String username,String givenPassword) throws IOException
    {
        Request getPassword = new Request("GET_PASSWORD",username);
        ApiHandler apiHandler = new ApiHandler(getPassword);
        apiHandler.sendRequest();
        String truePassword = apiHandler.receivePlainString();
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
