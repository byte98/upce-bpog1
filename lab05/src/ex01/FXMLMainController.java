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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;
/**
 * FXML Controller class
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLMainController implements Initializable {


    @FXML
    private TextArea textArea_input;
    @FXML
    private Button button_newRecord;
    @FXML
    private Button button_deleteAll;
    @FXML
    private PieChart pieChart_chart;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.clear();        
        this.pieChart_chart.setAnimated(false);
        this.pieChart_chart.setLegendVisible(true);
        this.pieChart_chart.setTitleSide(Side.TOP);
        this.pieChart_chart.setClockwise(true);
        this.pieChart_chart.setLabelLineLength(10);
    }    
    
    @FXML
    private void button_newRecord_onAction(ActionEvent event) {
        /*
        int nr = (int) (Math.random() * 50 + 1);
        this.textArea_input.appendText(nr + "\n");
        this.pieChart_chart.getData().add(new PieChart.Data(nr + "", nr));
        */
        Pair<String, Integer> result = this.showDialog();
        if (result != null)
        {
            this.textArea_input.appendText(String.format("%s; %d\n",
                    result.getKey(), result.getValue()));
            this.pieChart_chart.getData().add(new PieChart.Data(
                    String.format("%s; %d", result.getKey(), result.getValue()),
                    result.getValue()
            ));
        }
        
    }
    
    private Pair<String, Integer> showDialog()
    {
        Pair<String, Integer> reti = null;
        Dialog<Pair<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Zadejte nový záznam odpracovaných hodin");
        dialog.getDialogPane().getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.setPadding(new Insets(20, 150, 10, 10));
        
        TextField textField_name = new TextField();
        TextField textField_duration = new TextField();
        
        content.add(new Label("Jmeno:"), 0, 0);
        content.add(textField_name, 1, 0);
        content.add(new Label("Pocet odpracovanch hodin:"), 0, 1);
        content.add(textField_duration, 1, 1);
        
        Node button_OK = dialog.getDialogPane().lookupButton(ButtonType.OK);
        button_OK.setDisable(true);
        
        ChangeListener clEmptyTextValidation = new ChangeListener<String>(){
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                button_OK.setDisable(!this.isInputValid(textField_name.getText(), textField_duration.getText()));
            }

            private boolean isInputValid(String name, String duration)
            {
                boolean reti = false;
                if (name.trim().length() > 0 && duration.trim().length() > 0)
                {
                    try
                    {
                        Integer.parseInt(duration);
                        reti = true;
                    }
                    catch (NumberFormatException ex)
                    {
                        reti = false;
                    }
                }
                return reti;
            }
        };
        textField_name.textProperty().addListener(clEmptyTextValidation);
        textField_duration.textProperty().addListener(clEmptyTextValidation);
        dialog.getDialogPane().setContent(content);
        textField_name.requestFocus();
        dialog.setResultConverter(
                new Callback<ButtonType, Pair<String, Integer>>(){
                    @Override
                    public Pair<String, Integer> call(ButtonType dialogButton)
                    {
                        Pair<String, Integer> reti = null;
                        if (dialogButton == ButtonType.OK)
                        {
                            reti = new Pair(
                            textField_name.getText(),
                            Integer.parseInt(textField_duration.getText())
                            );
                        }
                        return reti;
                    }
                }
        );
        reti = dialog.showAndWait().get();
        return reti;
    }

    @FXML
    private void button_deleteAll_onAction(ActionEvent event) {
        this.clear();
    }
    
    private void clear()
    {
        this.textArea_input.clear();
        this.pieChart_chart.getData().clear();
    }

}
