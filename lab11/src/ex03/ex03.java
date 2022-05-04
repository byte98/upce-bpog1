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
package ex03;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex03 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex03.launch(args);
    }

    private Pane content;
    private final int count = 200;
    private List<Circle> points;
    private Rectangle selection;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        stage.setTitle("Lab 11 - ex03 (selection demo)");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        
        this.content = new Pane();
        root.setCenter(this.content);  
        this.points = new ArrayList<>();
        
        this.selection = new Rectangle(0, 0, 0, 0);
        this.selection.setStrokeWidth(1);
        this.selection.setStroke(Color.rgb(5, 185, 250));
        this.selection.setFill(Color.rgb(5, 185, 250, 0.3));
        this.content.getChildren().add(this.selection);
        
        this.content.setOnMousePressed((t) -> {
            this.selection.setX(t.getX());
            this.selection.setY(t.getY());
            this.selection.toFront();
            this.resetPoints();
        });
        this.content.setOnMouseDragged((t) -> {
            this.selection.setWidth(t.getX() - this.selection.getX());
            this.selection.setHeight(t.getY() - this.selection.getY());
        });
        this.content.setOnMouseReleased((t) -> {
            List<Circle> toHighlight = new ArrayList<>();
            for(Circle c: this.points)
            {
                if (this.selection.contains(c.getCenterX(), c.getCenterY()))
                {
                    toHighlight.add(c);
                }
            }
            for (Circle c: toHighlight)
            {
               RadialGradient gradient = new RadialGradient(
                0,
                .1,
                c.getCenterX() + 3,
                c.getCenterY() - 3,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.GREEN)
                ); 
               c.setFill(gradient);
            }
            this.selection.setWidth(0);
            this.selection.setHeight(0);
            this.selection.setX(0);
            this.selection.setY(0);
        });
        FlowPane buttons = new FlowPane();
        root.setBottom(buttons);
        Button exit = new Button("Konec");
        exit.setOnAction((t) -> {
            stage.close();
        });
        buttons.getChildren().add(exit);
        Button rem = new Button("Generuj");
        rem.setOnAction((t) -> {
            this.resetPoints();
            this.generate();    
        });
        buttons.getChildren().add(rem);
    }
    
    
    private void resetPoints()
    {
        for(Circle c: this.points)
        {
            RadialGradient gradient = new RadialGradient(
                0,
                .1,
                c.getCenterX() + 3,
                c.getCenterY() - 3,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.RED)
                );
                c.setFill(gradient);
        }
    }
    
    private void generate()
    {
        int generated = 0;
        Random rnd = ThreadLocalRandom.current();
        for(Circle c: this.points)
        {
            this.content.getChildren().remove(c);
        }
        this.points.clear();
        while (generated < this.count)
        {
            Circle c = new Circle(rnd.nextInt(0, (int) this.content.getWidth()), rnd.nextInt(0, (int) this.content.getHeight()), 10);
            if (this.isInContent(c))
            {
                generated++;
                RadialGradient gradient = new RadialGradient(
                0,
                .1,
                c.getCenterX() + 3,
                c.getCenterY() - 3,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.RED)
                );
                c.setFill(gradient);
                this.points.add(c);
            }
        }
        this.content.getChildren().addAll(this.points);
    }
    
    private boolean isInContent(Circle c)
    {
        return (
                c.getCenterX() + c.getRadius() < this.content.getWidth() &&
                c.getCenterX() - c.getRadius() > 0 &&
                c.getCenterY() + c.getRadius() < this.content.getHeight() &&
                c.getCenterY() - c.getRadius() > 0                
                );
    }
    
}
