package App;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rocket extends Group
{

    public Rocket()
    {
        Image file = new Image("file:rocket.png",100,100,true,true);
        ImageView Rocket = new ImageView(file);
        Rocket.setX(0);
        Rocket.setY(0);





        getChildren().addAll( Rocket);

        setLayoutX(170.22);
        setLayoutY(150);
    }

}
