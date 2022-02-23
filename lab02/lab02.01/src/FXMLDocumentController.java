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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private Label label_Result;
    @FXML
    private TextField textField_Height;
    @FXML
    private TextField textField_Weight;
    @FXML
    private Button button_Submit;
    
    private static final double TO_CM = 100;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //this.button_Submit.setDisable(true);
    }    

    @FXML
    private void buttonSubmitOnActionHandler(ActionEvent event)
    {
        double height = 0;
        double weight = 0;
        double result = 0;
        
        try
        {
            height = Double.parseDouble(this.textField_Height.getText()) / FXMLDocumentController.TO_CM;
            weight = Double.parseDouble(this.textField_Weight.getText());
            this.label_Result.setText(String.format("%.2f", this.computeBMI(height, weight)));

        }
        catch (Exception ex)
        {
            this.label_Result.setText("NEPLATNY VSTUP!");
        }
    }
    
    private double computeBMI(double height, double weight)
    {
        return weight / Math.pow(height, 2);
    }
    
}
