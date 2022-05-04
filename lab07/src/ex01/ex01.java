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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    
    public static Stage primaryStage;
    
    private Pane pane;
    
    private Ellipse el;
    
    private Line xAxis;
    
    private Line yAxis;
    
    ChangeListener<Number> cl = new ChangeListener<>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                el.setRadiusX(pane.getWidth() / 2);
                el.setRadiusY(pane.getHeight() / 2);
                el.setCenterX(pane.getWidth() / 2);
                el.setCenterY(pane.getHeight() / 2);
                el.setStroke(Color.BLACK);
                el.setStrokeWidth(4);
                el.setFill(null);
                setAxis();
            }
            
        };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex01.launch(args);
    }

    private void setAxis()
    {   
        this.xAxis.setStartX(pane.getWidth() / 2);
        this.xAxis.setStartY(pane.getHeight() / 2);
        this.xAxis.setEndX(pane.getWidth());
        this.xAxis.setEndY(pane.getHeight() / 2);
        
        this.yAxis.setStartX(pane.getWidth() / 2);
        this.yAxis.setStartY(pane.getHeight() / 2);
        this.yAxis.setEndX(pane.getWidth() / 2);
        this.yAxis.setEndY(pane.getHeight());
        
        
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        ex01.primaryStage = stage;
        this.pane = new Pane();
        stage.setScene(new Scene(this.pane, 800, 600));
        stage.setTitle("Lab 07 - ex01 (simple graphics demo)");
        stage.show();
        
        
        
        //Rectangle
        Rectangle rect = new Rectangle(0, 0, 128, 256);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(4);
        pane.getChildren().addAll(rect);
        
        // Line
        Line line = new Line(256, 128, 512, 256);
        line.setStroke(Color.GREEN);
        pane.getChildren().addAll(line);
        
        // Circle
        Circle circ = new Circle(256, 128, 64);
        circ.setFill(Color.color(0.5, 0.5, 0.5));
        pane.getChildren().addAll(circ);
        
        // Cubic curve
        CubicCurve curve = new CubicCurve(64, 256, 512, 256, 128, 256, 128, 16);
        curve.setStroke(Color.MAGENTA);
        curve.setStrokeWidth(8);
        curve.setFill(Color.YELLOW);
        pane.getChildren().addAll(curve);
        
        // Elipse with size of pane
        el = new Ellipse(pane.getWidth() / 2, pane.getHeight() / 2, pane.getWidth() / 2, pane.getHeight() / 2);
        el.setStroke(Color.BLACK);
        el.setStrokeWidth(4);
        el.setFill(null);
        pane.getChildren().addAll(el);
        
        // X axis
        this.xAxis = new Line(0, 0, 0, 0);
        this.xAxis.setStroke(Color.RED);
        this.xAxis.setStrokeWidth(2);
        this.pane.getChildren().addAll(xAxis);
        
        // Y axis
        this.yAxis = new Line(0, 0, 0, 0);
        this.yAxis.setStroke(Color.BLUE);
        this.yAxis.setStrokeWidth(2);
        this.pane.getChildren().addAll(yAxis);
        
        
        this.setAxis();
        
        this.pane.widthProperty().addListener(cl);
        this.pane.heightProperty().addListener(cl);
    }
    
}
