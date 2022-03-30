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
package bookdb;

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
public class FXMLAuthorDialogController implements Initializable {

    @FXML
    private TextField textField_name;
    @FXML
    private TextField textField_surname;
    @FXML
    private Button button_OK;
    @FXML
    private Button button_cancel;

    private Author data;
    private boolean okClicked;
    private Stage dialogStage;
    private Model model;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.okClicked = false;
    }    

    @FXML
    private void button_OK_onAction(ActionEvent event) {
        if (this.validateInput() == false)
        {
            String errMsg = "Jméno a příjmení nesmí být prázdněé!";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("CHYBA!");
            alert.setHeaderText("Prosím, opravte chybný vstup.");
            alert.setContentText(errMsg);
            alert.showAndWait();
        }
        else
        {
            if (this.data == null)
            {
                this.data = model.createAuthor(
                        this.textField_name.getText(),
                        this.textField_surname.getText());
            }
            else
            {
                this.data.setFirstName(this.textField_name.getText());
                this.data.setLastName(this.textField_surname.getText());
            }
            this.okClicked = true;
            this.dialogStage.close();
        }
    }

    @FXML
    private void button_cancel_onAction(ActionEvent event) {
        this.okClicked = false;
        this.dialogStage.close();
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public void setData(Author a)
    {
        if (a != null)
        {
            this.data = a;
            this.textField_name.setText(this.data.getFirstName());
            this.textField_surname.setText(this.data.getLastName());
        }
    }
    
    private boolean validateInput()
    {
        return (this.textField_name.getText().trim().length() > 0 &&
                this.textField_surname.getText().trim().length() > 0);
    }
    
    public boolean isOkClicked()
    {
        return this.okClicked;
    }
    
    public Author getResult()
    {
        return this.data;
    }
    
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }
    
}
