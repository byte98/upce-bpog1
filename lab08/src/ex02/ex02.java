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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex02 extends Application {

    private Canvas canvas;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex02.launch(args);
    }

    private void draw()
    {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        gc.setStroke(Color.BLUE);
        gc.strokeLine(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeLine(this.canvas.getWidth(), 0, 0, this.canvas.getHeight());
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {        
        this.canvas = new Canvas(800, 600);
        StackPane root = new StackPane();    
        root.getChildren().add(this.canvas);
        stage.setTitle("Lab 08 - ex03 (graphics context with listeners demo)");
        stage.setScene(new Scene(root));
        stage.show();
        
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        
        canvas.widthProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable o)
            {
                draw();
            }
        });
        
        canvas.heightProperty().addListener(new ChangeListener<>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                draw();
            }
        });
        
        this.draw();
       
    }
    
}
