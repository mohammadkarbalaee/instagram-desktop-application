import application.datamanagement.DataManager;
import application.datacomponents.signuplogin.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseTester
{
    public static void main(String[] args) throws SQLException, IOException
    {
        DataManager dataManager = new DataManager();
        User user = new User("hasan","hasan@yahoo.com","dbaa");
        user.setBio("here i am");
        File file = new File("C:\\Users\\Muhammad\\Desktop\\ali.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        bufferedImage.flush();
        inputStream.close();
        user.setProfilePhoto(bufferedImage);
        dataManager.saveUser(user);
    }
}
