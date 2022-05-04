/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex01 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex01.launch(args);
    }

    private CubicCurve curve;
    private ImageView img;
    private boolean animateEnabled = false;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        stage.setTitle("Lab 10 - ex01 (animations demo)");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        
        Pane content = new Pane();
        root.setCenter(content);
        
        List<Circle> points = new ArrayList<>();
         this.curve = null;
        
         CheckBox anim = new CheckBox("Animace");
        anim.setOnAction((t) -> {
            this.animateEnabled = anim.isSelected();
            this.animate(content);
        });
         
        root.setOnMouseClicked((t) -> {
            if (points.size() < 4 && t.getButton() == MouseButton.PRIMARY)
            {
                Circle circle = new Circle(t.getX(), t.getY(), 5);
                circle.setFill(Color.BLUE);
                circle.setStroke(Color.BLACK);
                circle.setStrokeWidth(2);
                content.getChildren().add(circle);
                points.add(circle);
                circle.setOnMouseDragged((e) -> {
                    circle.setCenterX(e.getX());
                    circle.setCenterY(e.getY());
                    this.drawCurve(points, content);
                    anim.setSelected(false);
                });
            }
            this.drawCurve(points, content);
        });
        
        FlowPane buttons = new FlowPane();
        root.setBottom(buttons);
        Button exit = new Button("Konec");
        exit.setOnAction((t) -> {
            stage.close();
        });
        buttons.getChildren().add(exit);
        Button rem = new Button("Vymazat");
        rem.setOnAction((t) -> {
            this.curve = null;
            content.getChildren().clear();
            points.clear();
        });
        buttons.getChildren().add(rem);
        
        buttons.getChildren().add(anim);
        File f = new File("plane.png");
        this.img = new ImageView(new Image(f.getAbsolutePath()));
        this.img.setScaleX(0.5);
        this.img.setScaleY(0.5);
    }
    
    private void animate(Pane content)
    {
        if (this.animateEnabled && this.curve != null)
        {
            if (content.getChildren().contains(this.img) == false)
            {
                content.getChildren().add(this.img);
            }
            PathTransition transition = new PathTransition();
            transition.setDuration(Duration.seconds(5));
            transition.setPath(this.curve);
            transition.setAutoReverse(true);
            transition.setNode(this.img);
            transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
            
            transition.play();
        }
        else
        {
            content.getChildren().remove(this.img);
        }
    }
    
    private void drawCurve(List<Circle> points, Pane content)
    {
        if (points.size() == 4)
        {
            if (this.curve == null)
            {
            this.curve = new CubicCurve(
                points.get(0).getCenterX(), points.get(0).getCenterY(),
                points.get(1).getCenterX(), points.get(1).getCenterY(),
                points.get(2).getCenterX(), points.get(2).getCenterY(),
                points.get(3).getCenterX(), points.get(3).getCenterY()
            );
            this.curve.setStroke(Color.RED);
            this.curve.setStrokeWidth(2);
            this.curve.setFill(null);
            content.getChildren().add(this.curve);
            }
            else
            {
                this.curve.setStartX(points.get(0).getCenterX());
                this.curve.setStartY(points.get(0).getCenterY());
                this.curve.setEndX(points.get(3).getCenterX());
                this.curve.setEndY(points.get(3).getCenterY());
                this.curve.setControlX1(points.get(1).getCenterX());
                this.curve.setControlY1(points.get(1).getCenterY());
                this.curve.setControlX2(points.get(2).getCenterX());
                this.curve.setControlY2(points.get(2).getCenterY());
            }
            this.animate(content);
        }
    }
}
