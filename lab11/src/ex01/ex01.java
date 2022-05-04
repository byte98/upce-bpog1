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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

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

    private Line line;
    private Label distance;
    private Label angle;
    private double startX = 0;
    private double startY = 0;
    private double endX = 0;
    private double endY = 0;
    private Pane content;
    private boolean down = false;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        stage.setTitle("Lab 11 - ex01 (calculations demo)");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        
        this.content = new Pane();
        root.setCenter(this.content);        
        
        this.content.setOnMousePressed((t) -> {
            this.down = true;
            this.startX = t.getX();
            this.startY = t.getY();
            this.endX = t.getX();
            this.endY = t.getY();
            this.displayLine();
        });
        this.content.setOnMouseDragged((t) -> {
            
            if (this.down)
            {
                this.endX = t.getX();
                this.endY = t.getY();
                this.displayLine();
            }
        });
        this.content.setOnMouseReleased((t) -> {
            this.down = false;
            this.displayLine();
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
            this.content.getChildren().clear();
            this.line = null;
            this.angle = null;
            this.distance = null;
            this.startX = 0;
            this.startY = 0;
            this.endX = 0;
            this.endY = 0;
        });
        buttons.getChildren().add(rem);
    }
    
    private void displayLine()
    {
        if (this.line == null)
        {
            this.line = new Line(this.startX, this.startY, this.endX, this.endY);
            this.content.getChildren().add(this.line);
            this.line.setStrokeWidth(5);
        }
        else
        {
            this.line.setStartX(this.startX);
            this.line.setStartY(this.startY);
            this.line.setEndX(this.endX);
            this.line.setEndY(this.endY);
        }
        this.displayDistance();
        this.displayAngle();
    }
    
    private void displayDistance()
    {
        if (this.distance == null)
        {
            this.distance = new Label();
            this.content.getChildren().add(this.distance);
            this.distance.setTextFill(Color.DARKGREEN);
        }
        this.distance.setLayoutX(this.startX + (this.endX - this.startX) / 2);
        this.distance.setLayoutY(this.startY + (this.endY - this.startY) / 2);
        this.distance.setText(String.valueOf(Math.sqrt((Math.pow((this.endX - this.startX), 2)) + (Math.pow((this.endY - this.startY), 2)))));
    }
    
    private void displayAngle()
    {
        if (this.angle == null)
        {
            this.angle = new Label();
            this.content.getChildren().add(this.angle);
            this.angle.setTextFill(Color.DARKRED);
        }
        this.angle.setLayoutX(this.startX + (this.endX - this.startX) / 2);
        this.angle.setLayoutY(this.startY + ((this.endY - this.startY) / 2) + 20);
        this.angle.setText(String.valueOf(Math.toDegrees(-Math.atan2((this.endY - this.startY), (this.endX - this.startX)))) + "°");
    }
    
}
