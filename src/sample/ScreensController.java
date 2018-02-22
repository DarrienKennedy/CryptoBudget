package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public class ScreensController extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();

    public ScreensController(){
        super();
    }

    //
    public void addScreen(String name, Node screen) {
        screens.put(name,screen);
    }

    //returns the node with name
    public Node getScreen(String name){
        return screens.get(name);
    }

    //loads FXML file add screen to collection injects screen pane to controller
    public boolean loadScreen(String name, String resource) {
        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean setScreen(final String name){
        if(screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                getChildren().remove(0);
                                getChildren().add(0, screens.get(name));
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(1), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        }else {
            System.out.println("screen wasn't loaded \n");
            return false;
        }
    }



    public boolean unloadScreen(String name){
        if(screens.remove(name) == null) {
            System.out.println("no screen to remove");
            return false;
        } else {
            return true;
        }

    }
}
