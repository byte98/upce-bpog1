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
package ex04;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex04 extends Application {

    private Canvas canvas;
    
    private static final int width = 800;
    private static final int height = 600;
    private static final int steps = 10;
    
    private static final double sX = width / 2;
    private static final double sY = height / 2;
    
    private static final int radius = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex04.launch(args);
    }
    
    private void draw(GraphicsContext gc)
    {
        
        gc.setStroke(Color.BLACK);
        gc.strokeOval(sX - radius, sY - radius, 2 * radius, 2 * radius);
        gc.setFill(Color.GREEN);
        
        Affine trans = new Affine();
        double angle = 360f / steps;
        trans.appendRotation(angle, sX, sY);
        
        for (int i = 0; i < steps; i++)
        {
            gc.fillOval(sX + 100, sY - 20, 60, 40);
            gc.transform(trans);
        }
    }

    
    @Override
    public void start(Stage stage) throws Exception
    {        
        this.canvas = new Canvas(800, 600);
        StackPane root = new StackPane();    
        root.getChildren().add(this.canvas);
        stage.setTitle("Lab 08 - ex04 (graphics context with events demo)");
        stage.setScene(new Scene(root));
        stage.show();
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        this.draw(gc);
       
    }
    
}
