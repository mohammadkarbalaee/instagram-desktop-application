package application.datamanagement.file;

import application.datamanagement.database.DatabaseManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class PhotoManager
{
    synchronized static public void saveProfilePhoto(String username, BufferedImage profilePhoto) throws IOException
    {
        File file = new File(System.getProperty("user.dir") + "\\ProfilePhotos\\" + username + ".jpg");
        FileOutputStream outputStream = new FileOutputStream(file);
        ImageIO.write(profilePhoto,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    }

    synchronized static public String savePostPhoto(String owner,BufferedImage postImage) throws IOException, SQLException
    {
        Integer nth = DatabaseManager.getPostQuantity(owner) + 1;
        String saveAddress = System.getProperty("user.dir") + "\\PostPhotos\\" + owner + nth + ".png";
        File file = new File(saveAddress);
        FileOutputStream outputStream = new FileOutputStream(file);
        ImageIO.write(postImage,"png",outputStream);
        outputStream.flush();
        outputStream.close();
        return saveAddress;
    }

    synchronized static public BufferedImage getPostPhoto(String photoAddress) throws IOException
    {
        System.out.println(photoAddress);
        File file = new File(photoAddress);
        return ImageIO.read(file);
    }


}
