package org.example.prototype;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StockController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField curStockTextField;
    @FXML
    private DatePicker curDatePicker;
    @FXML
    private Label errorMsg;
    private LocalDate curDate=null;
    private double curStock=-1;
    private LocalDate latest_rt;

    public void initialize(){
        StringConverter<LocalDate> conv=new StringConverter<LocalDate>() {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                return (localDate!=null)? localDate.format(formatter):"";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s!=null && !s.trim().isEmpty())?LocalDate.parse(s,formatter):null;
            }
        };
        curDatePicker.setConverter(conv);
        submitButton.setOnAction(e->{
            double parsedStock=-1;
            LocalDate parsedCurDate=null;
            if(curDatePicker.getValue()!=null){
                if(curDatePicker.getValue().isBefore(latest_rt)){
                    setErrorMsg("Hiện tại không được sớm hơn ngày nhận gần nhất");
                    return;
                }
                parsedCurDate=curDatePicker.getValue();
            }
            if (curStockTextField.getText()!=null && !curStockTextField.getText().trim().isEmpty()){
                try{Double.parseDouble(curStockTextField.getText());} catch (
                        NumberFormatException ex) {
                    setErrorMsg("Số lượng không hợp lệ");
                    return;
                }
                if(Double.parseDouble(curStockTextField.getText())<0){
                    setErrorMsg("Số lượng không được âm");
                    return;
                }
                parsedStock=Double.parseDouble(curStockTextField.getText());
            }
            if(curDatePicker.getValue()!=null && (curStockTextField.getText()==null||curStockTextField.getText().trim().isEmpty())){
                setErrorMsg("Ngày phải được cung cấp với số lượng");
                return;
            }
            curStock=parsedStock;
            curDate=parsedCurDate;
            ((Stage)(submitButton.getScene().getWindow())).close();
        });
    }
    public void setErrorMsg(String text){
        FadeTransition transition=new FadeTransition();
        transition.setNode(errorMsg);
        transition.setDuration(Duration.millis(1000));
        errorMsg.setText(text);
        errorMsg.setVisible(true);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.play();
        submitButton.setDisable(true);
        transition.setOnFinished(e->{
            errorMsg.setVisible(false);
            submitButton.setDisable(false);
        });
    }
    public double getCurStock(){
        return curStock;
    }
    public LocalDate getCurDate(){
        return curDate;
    }
    public void setLatest_rt(LocalDate d){this.latest_rt=d;}
}
