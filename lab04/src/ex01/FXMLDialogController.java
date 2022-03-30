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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLDialogController implements Initializable {

    @FXML
    private TextField textField_firstName;
    @FXML
    private TextField textField_lastName;
    @FXML
    private Button button_OK;
    @FXML
    private Button button_Cancel;
    
    private Person inputData;
    private Person result;
    private boolean okClicked;
    private Stage dialogStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void button_OK_onAction(ActionEvent event) {
        if (this.isInputValid())
        {
            this.result = new Person(
                    this.textField_firstName.getText(),
                    this.textField_lastName.getText()
            );
            this.okClicked = true;
            this.dialogStage.close();
        }
        else
        {
            String errMsg = "At least a first or last name must be set!";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("Invalid input");
            alert.setHeaderText("Please, correct invalid input");
            alert.setContentText(errMsg);
            alert.showAndWait();
        }
    }

    @FXML
    private void button_Cancel_onAction(ActionEvent event) {
        this.okClicked = false;
        if (this.dialogStage != null)
        {
            this.dialogStage.close();
        }
    }
    
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    
    public void setInputData(Person inputData)
    {
        if (inputData != null)
        {
            this.inputData = inputData;
            this.textField_firstName.setText(this.inputData.getFirstName());
            this.textField_lastName.setText(this.inputData.getLastName());
        }
        else
        {
            this.textField_firstName.setPromptText("Enter new first name...");
            this.textField_lastName.setPromptText("Enter new last name...");
        }
        
    }
    
    public boolean isOkClicked()
    {
        return this.okClicked;
    }
    
    public Person getResult()
    {
        return this.result;
    }
    
    private boolean isInputValid()
    {        
        return (this.textField_firstName.getText().trim().isEmpty() == false ||
            this.textField_lastName.getText().trim().length() > 0);
    }
    
}
