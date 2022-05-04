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
package ex02;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex02 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex02.launch(args);
    }

    private Line line;
    private Ellipse ellipse;
    private Pane content;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        stage.setTitle("Lab 11 - ex02 (transformations demo)");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        
        this.content = new Pane();
        root.setCenter(this.content);        
        
        this.ellipse = new Ellipse(root.getWidth() / 2, root.getHeight() / 2, 100, 50);
        this.ellipse.setStrokeWidth(5);
        this.ellipse.setStroke(Color.RED);
        this.ellipse.setFill(Color.YELLOW);
        this.content.getChildren().add(this.ellipse);
        
        Circle point = new Circle(root.getWidth() / 2 + 100 + 100, root.getHeight() / 2, 10);
        point.setStroke(Color.BLACK);
        point.setFill(Color.BLACK);
        
        
        this.line = new Line(this.ellipse.getCenterX(), this.ellipse.getCenterY(), point.getCenterX(), point.getCenterY());
        this.line.setStroke(Color.DARKGRAY);
        this.content.getChildren().add(this.line);
        this.content.getChildren().add(point);
        point.setOnMouseDragged((t) -> {
            point.setCenterX(t.getX());
            point.setCenterY(t.getY());
            line.setEndX(point.getCenterX());
            line.setEndY(point.getCenterY());
            this.ellipse.setRotate(-ex02.lineAngle(line));
        });
        
        FlowPane buttons = new FlowPane();
        root.setBottom(buttons);
        Button exit = new Button("Konec");
        exit.setOnAction((t) -> {
            stage.close();
        });
        buttons.getChildren().add(exit);
        Button rem = new Button("Reset");
        rem.setOnAction((t) -> {
            point.setCenterX(root.getWidth() / 2 + 100 + 100);
            point.setCenterY(root.getHeight() / 2);
            line.setEndX(point.getCenterX());
            line.setEndY(point.getCenterY());
            this.ellipse.setRotate(0);
        });
        buttons.getChildren().add(rem);
    }
    
    private static double lineAngle(Line l)
    {
        double angle = -Math.atan2(l.getEndY() - l.getStartY(), l.getEndX() - l.getStartX());
        return Math.toDegrees(angle);
    }
    
    
}
