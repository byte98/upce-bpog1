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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLMainController implements Initializable {

    @FXML
    private Label label_name;
    @FXML
    private Button button_edit;
    
    private Person person = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showPersonDetails(this.person);
    }    

    @FXML
    private void button_edit_onAction(ActionEvent event) {
        Person result = this.showPersonDialog(this.person);
        if (result != null)
        {
            this.person = result;
            this.showPersonDetails(this.person);
        }
    }
    
    private Person showPersonDialog(Person person)
    {
        Person reti = null;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ex01.class.getResource("FXMLDialog.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            if (this.person != null)
            {
                dialogStage.setTitle("Edit person");
            }
            else
            {
                dialogStage.setTitle("Create new person");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ex01.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            
            FXMLDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setInputData(this.person);
            
            dialogStage.showAndWait();
            reti = controller.getResult();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return reti;
    }
    
    private void showPersonDetails(Person person)
    {
        if (person != null)
        {
            this.label_name.setText(person.getFirstName().trim() + " " + person.getLastName().trim());
        }
        else
        {
            this.label_name.setText("NO PERSON!");
        }
        
        if (this.label_name.getText().trim().equals(""))
        {
            this.label_name.setText("NO NAME!");
        }
    }
    
}
