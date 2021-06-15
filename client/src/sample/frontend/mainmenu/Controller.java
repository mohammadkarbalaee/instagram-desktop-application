package sample.frontend.mainmenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.frontend.mainmenu.model.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private GridPane postGrid;
    private List<Post> posts;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        posts = new ArrayList<>(data());

        int columns = 0, rows = 1;

        try
        {
            for (int i = 0; i < posts.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("post.fxml"));

                VBox postBox = fxmlLoader.load();

                PostController postController = fxmlLoader.getController();
                postController.setData(posts.get(i));

                if (columns == 3)
                {
                    columns = 0;
                    ++rows;
                }

                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            }

        }catch (IOException ioException){
            ioException.printStackTrace();
        }


    }

    private List<Post> data()
    {
        List<Post> list = new ArrayList<>();


        //post 1
        list.add(
                  addPost("/sample/photo/userProf1.png", "/sample/photo/post1.jpg",
                  "user 1", "2 DAYS AGO", "12", "2")
                );

        //post 2
        list.add(
                addPost("/sample/photo/userProf2.png", "/sample/photo/post2.jpg",
                        "user 2", "4 DAYS AGO", "20", "5")
        );

        //post 3
        list.add(
                addPost("/sample/photo/userProf3.png", "/sample/photo/post3.jpg",
                        "user 3", "4 DAYS AGO", "40", "4")
        );

        //post 4
        list.add(
                addPost("/sample/photo/userProf4.png", "/sample/photo/post4.jpg",
                        "user 4", "6 DAYS AGO", "30", "6")
        );

        //post 5
        list.add(
                addPost("/sample/photo/userProf5.png", "/sample/photo/post5.jpg",
                        "user 5", "10 DAYS AGO", "35", "2")
        );

        //post 6
        list.add(
                addPost("/sample/photo/userProf6.png", "/sample/photo/post6.jpg",
                        "user 6", "12 DAYS AGO", "80", "9")
        );

        //post 7
        list.add(
                addPost("/sample/photo/userProf7.png", "/sample/photo/post7.jpg",
                        "user 7", "25 DAYS AGO", "10", "1")
        );




        return list;
    }

    private Post addPost(String profileSrc, String imgSrc, String username, String date, String likeNum, String commentNum)
    {
        Post post = new Post();
        post.setProfileImageSrc(profileSrc);
        post.setPostImageSrc(imgSrc);
        post.setUsername(username);
        post.setDate(date);
        post.setNumLikes(likeNum);
        post.setNumComments(commentNum);

        return post;
    }
}
