package App;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;


public class Main extends Application {

     Rocket Cohete = new Rocket();
   // StackPane shipGroup = new StackPane();
    Group StarGroup = new Group();
    int starAmount = 10000;
    Timeline Loop;
    Line laser ;

    AudioClip laserSound = new AudioClip("file:laserSound.mp3");
    MediaPlayer laserNoise  = new MediaPlayer(new Media("file:laserSound.mp3"));


    Group Space;
    Scene mainScene;


    public void start(Stage PrimaryStage)
    {



        RandomStars(starAmount);



        Space = new Group();

        laser = new Line(200,200,200,200);

        laser.setStroke(Color.TRANSPARENT);
        laser.setStrokeWidth(3.0);



        Space.getChildren().addAll(StarGroup,laser,Cohete);





        mainScene = new Scene(Space,400,400);
        mainScene.setFill(Color.BLACK);

        mainScene.setOnMousePressed(this::LaserActive);

        mainScene.setOnMouseMoved(this::MovementProcess);
        mainScene.setOnMouseReleased(this::LaserDeactive);


        PrimaryStage.setScene(mainScene);
        PrimaryStage.setResizable(false);
        PrimaryStage.show();
    }

    public void MovementProcess(MouseEvent event)  {


        if(Loop != null)
       Loop.stop();



       double mouseY = (-1)*event.getY()+200, mouseX = event.getX()-200;

       double rotate = Math.atan2(mouseX, mouseY);
//        Math.cos(event.getX());

       rotate = Math.toDegrees(rotate);

      // System.out.println("X: "+ mouseX +", Y: " + mouseY + ", Angle: " + rotate);



       Cohete.setRotate(rotate);

        if(laser.getStroke() == Color.RED)
        {
           // laser.getTransforms().add(new Rotate( (-1)*Math.atan2(mouseX, mouseY),200,200,0));
            LaserActive(event);


        }

       updateStarLocation(Math.atan2(mouseX, mouseY));


    }

    public void RandomStars(int starAmount)
    {
        for(int x = 0; x<starAmount;x++)
        {
            Circle star = new Circle(1.0,Color.WHITE);
            star.setTranslateX(new Random().nextInt(10000));
            star.setTranslateY(new Random().nextInt(10000));

            StarGroup.getChildren().add(star);

        }
    }

    public void updateStarLocation(double angle)  {

//        double oldX = StarGroup.getTranslateX();
//        double oldY = StarGroup.getTranslateY();

        Loop = new Timeline
                (new KeyFrame(Duration.seconds(1/60.0),
                                new EventHandler<ActionEvent>()
                                {
                                    @Override
                                    public void handle(ActionEvent event) {

                                        double oldX = StarGroup.getTranslateX();
                                        double oldY = StarGroup.getTranslateY();

                                        StarGroup.setTranslateY(oldY + Math.cos(angle));
                                        StarGroup.setTranslateX(oldX - Math.sin(angle));

                                        //System.out.println(StarGroup.getTranslateX() + " " + StarGroup.getTranslateY());

                                    }
                                }
                             )
                );
        Loop.setCycleCount(Timeline.INDEFINITE);
        Loop.play();
        //StarGroup.setTranslateX(oldX + Math.cos(angle));


    }

    public void LaserActive(MouseEvent event)
    {

        if (laser.getStroke() != Color.RED)
        {

         laserSound.play();
         laserSound.setCycleCount(100);
        }



        double mouseY = (-1)*event.getY()+200, mouseX = event.getX()-200;
        double posX = event.getX(), posY = event.getY();


        laser.setStroke(Color.RED);

        laser.setEndX(posX + mouseX*100);
        laser.setEndY(posY - mouseY*100);



        mainScene.setOnMouseDragged(this::MovementProcess);


    }
    public void LaserDeactive(MouseEvent event)
    {

        laser.setStroke(Color.TRANSPARENT);
        laserSound.stop();

    }


    public static void main(String[] args) {
	launch(args);
    }
}
