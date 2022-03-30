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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ex01 extends Application {
    
    public static Stage primaryStage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {        
        ex01.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        StackPane stackPane = new StackPane();
        
        TreeItem<String> rootItem = new TreeItem<>("ROOT_ITEM");
        rootItem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<String>>(){
            @Override
            public void handle(TreeItem.TreeModificationEvent<String> t)
            {
                System.out.println("Item: " + t.getSource().getValue() + " expanded");
            }
        });
        
        TreeView treeView = new TreeView();
        treeView.setRoot(rootItem);
        treeView.setEditable(true);
        treeView.setCellFactory(TextFieldTreeCell.forTreeView());
        stackPane.getChildren().add(treeView);
        
        // #region GENEROVANI_DAT
        int limit = 10;
        int min = 0;
        int max = 5;
        for (int i = 0; i < limit; i++)
        {
            TreeItem<String> item = new TreeItem<>(String.format("ITEM_%02d", (i + 1)));
            int childs = (int) ((Math.random() * (max - min)) + min);
            for(int j = 0; j < childs; j++)
            {
                TreeItem<String> child = new TreeItem<>(String.format("ITEM_%02d_%02d", (i + 1), (j + 1)));
                item.getChildren().add(child);
            }
            item.setExpanded(true);
            rootItem.getChildren().add(item);
        }
        rootItem.setExpanded(true);
        // #endregion
        
        // #region "PROCHAZENI_STROMU"
        for(TreeItem<String> item: rootItem.getChildren())
        {
            if (item.getChildren().size() < 1)
            {
                item.setValue(item.getValue() + "_EMPTY");
            }
        }
        // #endregion
        
        Scene scene = new Scene(stackPane, 800, 600);
        ex01.primaryStage = stage;
        
        stage.setScene(scene);
        stage.setTitle("Lab 06 - ex01 (tree view demo)");
        stage.setResizable(false);
        stage.show();
    }
    
}
