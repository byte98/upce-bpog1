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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView<String> listView_memberList;
    @FXML
    private TextField textField_newMember;
    @FXML
    private Button button_addMember;
    @FXML
    private Button button_deleteAll;
    @FXML
    private Button button_deleteSelected;
    
    private ObservableList<String> members;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.members = FXCollections.observableArrayList();
        this.listView_memberList.setItems(this.members);
        
    }    

    @FXML
    private void buttonAddMemberOnAction(ActionEvent event)
    {
        if (this.textField_newMember.getText().equals("") == false)
        {
            this.members.add(this.textField_newMember.getText());
            this.textField_newMember.setText("");
        }
    }

    @FXML
    private void buttonDeleteAllOnAction(ActionEvent event) {
        this.members.removeAll(this.members);
    }

    @FXML
    private void buttonDeleteSelectedOnAction(ActionEvent event) {
        this.members.removeAll(this.listView_memberList.getSelectionModel().getSelectedItems());
    }
    
}
