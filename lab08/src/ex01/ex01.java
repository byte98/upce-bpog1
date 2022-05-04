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

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.event.DocumentEvent;

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

 
    
    @Override
    public void start(Stage stage) throws Exception
    {        
        Canvas canvas = new Canvas(800, 600);
        Group root = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();        
        root.getChildren().add(canvas);
        stage.setTitle("Lab 08 - ex01 (graphics context demo)");
        stage.setScene(new Scene(root));
        stage.show();
        
        // Colour settings
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.GREEN);
        
        // Width of line
        gc.setLineWidth(5.0);
        
        // Draw some shapes
        gc.strokeLine(10, 40, 100, 10);
        gc.fillOval(10, 60, 40, 20);
        gc.strokeOval(60, 60, 40, 20);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRect(160, 60, 30, 30);
        
        // Arcs
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        
        // Filled arcs
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        
        // Polygons and polylines
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140}, 
                          new double[]{210, 210, 240, 240}, 4);
        
        // Prepare image
        Image img = new Image("ex01/image.png");
        ImagePattern ip1 = new ImagePattern(img);
        ImagePattern ip2 = new ImagePattern(img, 0, 0, 10, 20, false);
        ImagePattern ip3 = new ImagePattern(img, 0, 0, 1.0, 0.33, true);
        
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        
        // Add images
        gc.setFill(ip1);
        gc.fillRect(100, 300, 100, 50);
        gc.strokeRect(100, 300, 100, 50);
        
        gc.setFill(ip2);
        gc.fillRect(300, 300, 100, 50);
        gc.strokeRect(300, 300, 100, 50);
        
        gc.setFill(ip3);
        gc.fillRect(500, 300, 100, 50);
        gc.strokeRect(500, 300, 100, 50);
        
        // Gradient
        LinearGradient lg = new LinearGradient (50, 50, 70, 70, false, CycleMethod.REFLECT,
        new Stop(0f, Color.RED), new Stop(1.0f, Color.CYAN));
        gc.setFill(lg);
        gc.fillRect(300, 450, 100, 50);
        gc.strokeRect(300, 450, 100, 50);
        lg = new LinearGradient (0, 0, 1, 1, true, CycleMethod.REPEAT,
        new Stop(0f, Color.RED), new Stop(1.0f, Color.CYAN));
        gc.setFill(lg);
        gc.fillOval(500, 450, 200, 100);
        
        // Path
        gc.setFill(ip1);
        gc.beginPath();
        gc.moveTo(50, 450);
        gc.lineTo(200, 400);
        gc.quadraticCurveTo(150, 500, 200, 600);
        gc.closePath();
        gc.fill();
        gc.stroke();
        
    }
    
    /**
     * Prepares path for clipping
     * @param gc Context in which will be clipping performed
     */
    private void prepareClip(GraphicsContext gc)
    {        
        gc.beginPath();
        gc.moveTo(10, 20);
        gc.lineTo(50, 40);
        gc.lineTo(100, 5);
        gc.lineTo(10, 20);
        
        gc.clip();
        gc.stroke();
    }
    
    /**
     * Cancels clipping
     * @param gc Context in which clipping will be canceled
     */
    private void cancelClip(GraphicsContext gc)
    {
        gc.restore();
    }
    
}
