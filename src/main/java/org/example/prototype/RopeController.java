package org.example.prototype;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RopeController {
    //responsible for event handling + display
    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableColumn<Purchase,String> linkCol;
    @FXML
    private TableColumn<Purchase,Double> amtCol;
    @FXML
    private TableColumn<Purchase, String> btCol;
    @FXML
    private TableColumn<Purchase, String> rtCol;
    @FXML
    private TableColumn<Purchase, Integer> twCol;
    @FXML
    private Button addButton;
    @FXML
    private Button computeButton;
    @FXML
    private Button addTestDataButton;
    @FXML
    private CheckBox wCheck;
    @FXML
    private CheckBox tCheck;
    @FXML
    private CheckBox hCheck;
    RopeDataHandler dataHandler=new RopeDataHandler();
    double totalM=1;
    double wVal=0.3;
    double hVal=0.2;
    double tVal=0.15;

    public void initialize(){
        linkCol.setCellValueFactory(cell-> new SimpleObjectProperty<>(cell.getValue().itemLink)
        );
        amtCol.setCellValueFactory(cell-> new SimpleObjectProperty<>(cell.getValue().amt)
        );
        btCol.setCellValueFactory(cell-> new SimpleObjectProperty<>(cell.getValue().bt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );
        rtCol.setCellValueFactory(cell-> new SimpleObjectProperty<>(cell.getValue().rt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );
        twCol.setCellValueFactory(cell-> new SimpleObjectProperty<>(cell.getValue().tw)
        );
        addButton.setOnAction(e->{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("add-view.fxml"));
            try {
                Parent root=loader.load();
                Stage popup=new Stage();
                Scene scene=new Scene(root);
                AddController add_controller=loader.getController();
                popup.setScene(scene);
                popup.setTitle("R.O.P.E Add");
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.showAndWait();
                if (add_controller.getPurchase()!=null) {
                    purchaseTable.getItems().add(add_controller.getPurchase());
                    dataHandler.getPurchases().add(add_controller.getPurchase());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            computeButton.setDisable(dataHandler.getPurchases().size()<3);
        });
        addTestDataButton.setOnAction(e->{
            purchaseTable.getItems().addAll(dataHandler.load_test_purchases());
            computeButton.setDisable(dataHandler.getPurchases().size()<3);
        });
        computeButton.setOnAction(e->{
            totalM=1;
            RopeComputer computer=new RopeComputer(dataHandler.getPurchases());
            if(wCheck.isSelected())totalM+= wVal;
            if(hCheck.isSelected())totalM+=hVal;
            if(tCheck.isSelected())totalM+=tVal;
            FXMLLoader loader=new FXMLLoader((getClass().getResource("stock-view.fxml")));
            try{
                Stage stockStage=new Stage();
                Parent s_root =loader.load();
                Scene s_scene =new Scene(s_root);
                stockStage.setScene(s_scene);
                stockStage.initModality(Modality.APPLICATION_MODAL);
                stockStage.setTitle("R.O.P.E Optional");
                StockController s_controler=loader.getController();
                s_controler.setLatest_rt(computer.getLatestRt());
                stockStage.showAndWait();
                double curStock=s_controler.getCurStock();
                LocalDate curDate=s_controler.getCurDate();
                LocalDate reorderDate;
                if (curStock<0){ //revert to option A
                    reorderDate=computer.reorderDate(totalM);
                    curDate=computer.getLatestRt();
                }
                else if (curStock>0 && curDate!=null){
                    reorderDate=computer.reorderDate_cur_provided(totalM,curDate,curStock);
                }
                else {//assume this case for
                    curDate=LocalDate.now();
                    reorderDate=computer.reorderDate_cur_provided(totalM,curDate,curStock);
                }
                LocalDate reorderDate2=computer.reorderDate2(reorderDate,totalM);

                Stage o_stage=new Stage();
                FXMLLoader o_loader=new FXMLLoader(getClass().getResource("output-view.fxml"));
                try{
                    Parent o_root=o_loader.load();
                    OutputController o_controller=o_loader.getController();
                    Scene o_scene=new Scene(o_root);
                    o_stage.setScene(o_scene);
                    o_controller.setTarget(curDate);
                    o_controller.setGraphics(reorderDate,reorderDate2,dataHandler.getPurchases().getLast().itemLink,Double.toString(totalM));
                    o_stage.initModality(Modality.APPLICATION_MODAL);
                    o_stage.setTitle("R.O.P.E Output");
                    o_stage.showAndWait();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        });
    }

}
