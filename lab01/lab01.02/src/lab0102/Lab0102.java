/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab0102;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Lab0102 extends Application
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Lab0102.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        //btn.setBackground(null);
        //btn.setRotate(30);
        btn.setBackground(new Background(
        new BackgroundFill(Color.LIGHTCORAL,
        new CornerRadii(15), new Insets(3))));
        btn.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, new CornerRadii(15),
        new BorderWidths(3))));
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        //root.setBackground(Background.EMPTY);
        root.setBackground(new Background(new BackgroundFill(Color.ORANGE,
            new CornerRadii(30), new Insets(30))));
        //Scene scene = new Scene(root, 300, 250);
        Scene scene = new Scene(root, 300, 250, Color.SKYBLUE);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
