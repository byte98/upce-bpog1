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
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLBookDialogController implements Initializable {


    @FXML
    private TextField textField_bookName;
    @FXML
    private ComboBox<Author> comboBox_author;
    @FXML
    private Spinner<Integer> spinner_price;
    @FXML
    private Button button_OK;
    @FXML
    private Button button_cancel;
    
    private Stage stage;
    private Model model;
    private Book book;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.okClicked = false;
    }    
    
    @FXML
    private void button_OK_onAction(ActionEvent event) {
        String validation = this.validateInput();
        if (validation != null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.stage);
            alert.setTitle("CHYBA!");
            alert.setHeaderText("Prosím, opravte chybný vstup.");
            alert.setContentText(validation);
            alert.showAndWait();
        }
        else
        {
            if (this.book == null)
            {
                this.book = this.model.createBook(
                        (Author)this.comboBox_author.getSelectionModel().getSelectedItem(),
                        this.textField_bookName.getText(),
                        (int)this.spinner_price.getValue()
                        );
            }
            else
            {
                this.book.setAuthor((Author)this.comboBox_author.getSelectionModel().getSelectedItem());
                this.book.setName(this.textField_bookName.getText());
                this.book.setPrice((int)this.spinner_price.getValue());
            }
            this.okClicked = true;
            this.stage.close();
        
        }
    }

    @FXML
    private void button_cancel_onAction(ActionEvent event) {
        this.okClicked = false;
        this.stage.close();
    }
    
    public void setData(Model model, Stage stage)
    {
        this.spinner_price.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        this.model = model;
        this.stage = stage;
        this.book = null;
        this.comboBox_author.getItems().addAll(model.getAuthors());
    }
    
    public void setData(Model model, Stage stage, Book book)
    {
        this.setData(model, stage);
        this.book = book;
        this.textField_bookName.setText(this.book.getName());
    }
    
    public boolean isOkClicked()
    {
        return this.okClicked;
    }
    
    public Book getData()
    {
        return this.book;
    }
    
    private String validateInput()
    {
        String reti = null;
        if (this.textField_bookName.getText().trim().length() < 1)
        {
            reti = "Jméno knihy nesmí být prázdné!";
        }
        else if ((int)this.spinner_price.getValue() < 0)
        {
            reti = "Cena knihy musí být vetší než nula!";
        }
        return reti;
    }

    
}
