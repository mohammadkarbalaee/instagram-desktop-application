package application.util;

import api.ApiHandler;
import api.RequestPipeline;
import application.signup.SignUpper;
import application.signup.User;

import java.io.IOException;
import java.util.Scanner;

public class Runner
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        RequestPipeline.build();
        User user = new User("reza","z@sbu.com","123aaaBBB@#");
        user.setBio("HELLO EVERYONE");
        SignUpper signUpper = new SignUpper(user);
        ApiHandler apiHandler = new ApiHandler(signUpper.makeRequest());
        apiHandler.sendRequest();
        in.next();
    }
}
