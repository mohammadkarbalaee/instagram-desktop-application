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
    synchronized static public String saveProfilePhoto(String username, BufferedImage profilePhoto) throws IOException
    {
        String savePath = System.getProperty("user.dir") + "\\ProfilePhotos\\" + username + ".png";
        File file = new File(savePath);
        FileOutputStream outputStream = new FileOutputStream(file);
        ImageIO.write(profilePhoto,"png",outputStream);
        outputStream.flush();
        outputStream.close();
        return savePath;
    }

    synchronized static public String savePostPhoto(String owner,BufferedImage postImage) throws IOException, SQLException
    {
        Integer nth = DatabaseManager.getPostQuantity(owner) + 1;
        String savePath = System.getProperty("user.dir") + "\\PostPhotos\\" + owner + nth + ".png";
        File file = new File(savePath);
        FileOutputStream outputStream = new FileOutputStream(file);
        ImageIO.write(postImage,"png",outputStream);
        outputStream.flush();
        outputStream.close();
        return savePath;
    }

    synchronized static public BufferedImage getPhoto(String photoAddress) throws IOException
    {
        File file = new File(photoAddress);
        return ImageIO.read(file);
    }
}
