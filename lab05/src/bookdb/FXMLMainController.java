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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLMainController implements Initializable {

    @FXML
    private ListView<Book> listView_books;
    @FXML
    private Button button_close;
    @FXML
    private Button button_addBook;
    @FXML
    private Button button_addAuthor;
    @FXML
    private Button button_editBook;
    @FXML
    private Button button_removeBook;
    @FXML
    private Label label_bookID;
    @FXML
    private Label label_bookName;
    @FXML
    private Label label_bookAuthor;
    @FXML
    private Label label_bookPrice;
    
    private Model model;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new Model();
        this.listView_books.setItems(FXCollections.observableList(model.getBooks()));
    }    

    @FXML
    private void button_close_onAction(ActionEvent event) {
        model.save();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void button_addBook_onAction(ActionEvent event) {
        this.showBookDialog(null);
    }

    @FXML
    private void button_addAuthor_onAction(ActionEvent event) {
        this.showAuthorDialog(null);
    }
    
    @FXML
    private void button_editBook_onAction(ActionEvent event) {
        Book selected = this.listView_books.getSelectionModel().getSelectedItem();        
        if (selected != null)
        {
            this.showBookDialog(selected);
        }        
    }
    
    @FXML
    private void button_removeBook_onAction(ActionEvent event) {
        Book selected = this.listView_books.getSelectionModel().getSelectedItem();        
        if (selected != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Opravdu chcete odstranit knihu?");
            alert.setHeaderText("Opravdu chcete odstranit tuto knihu?");
            alert.setContentText(selected.toString());
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK)
            {
                this.model.removeBook(selected);
                this.listView_books.setItems(FXCollections.observableList(model.getBooks()));
            }            
        }        
    }
    
    @FXML
    private void listView_books_onChange(Event event){
        Book selected = this.listView_books.getSelectionModel().getSelectedItem();        
        if (selected != null)
        {
            this.button_editBook.setDisable(false);
            this.button_removeBook.setDisable(false);
            this.label_bookID.setText(selected.getId());
            this.label_bookName.setText(selected.getName());
            StringBuilder price = new StringBuilder();
            price.append(selected.getPrice());
            price.append(" CZK");
            this.label_bookPrice.setText(price.toString());
            this.label_bookAuthor.setText(selected.getAuthor().toString());
        }        
    }
    
    private Author showAuthorDialog(Author author)
    {
        Author reti = null;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(bookdb.class.getResource("FXMLAuthorDialog.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            if (author != null)
            {
                dialogStage.setTitle("Editace autora");
            }
            else
            {
                dialogStage.setTitle("Přidání nového autora");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(bookdb.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            
            FXMLAuthorDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(author);
            controller.setModel(this.model);
            
            dialogStage.showAndWait();
            reti = controller.getResult();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;
    }
    
    private Book showBookDialog(Book book)
    {
        Book reti = null;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(bookdb.class.getResource("FXMLBookDialog.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            if (book != null)
            {
                dialogStage.setTitle("Editace knihy");
            }
            else
            {
                dialogStage.setTitle("Přidání nové knihy");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(bookdb.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            
            FXMLBookDialogController controller = loader.getController();
            controller.setData(this.model, dialogStage);
            
            dialogStage.showAndWait();
            reti = controller.getData();
            this.listView_books.setItems(FXCollections.observableList(model.getBooks()));
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;
    }
    
}
