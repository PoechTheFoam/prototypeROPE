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

public class AddController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField itemTextField;
    @FXML
    private TextField amtTextField;
    @FXML
    private DatePicker btPicker;
    @FXML
    private DatePicker rtPicker;
    @FXML
    private TextField twTextField;
    @FXML
    private Label errorMsg;
    Purchase purchase=null;
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
        btPicker.setConverter(conv);
        rtPicker.setConverter(conv);
        submitButton.setOnAction(e->{
            String parsedLink =null;
            double parsedAmt =-1;
            LocalDate parsedBt =null;
            LocalDate parsedRt =null;
            int parsedTw =-1;
            if (itemTextField.getText()!=null){
                if(itemTextField.getText().trim().isEmpty()){
                    setErrorMsg("Không được bỏ trống vùng Link sản phẩm");
                    return;
                }
                parsedLink =itemTextField.getText();
            }
            if (amtTextField.getText()!=null){
                if(amtTextField.getText().isEmpty()){
                    setErrorMsg("Không được bỏ trống vùng Số lượng");
                    return;
                }
                try{
                    parsedAmt =Double.parseDouble(amtTextField.getText());} catch (NumberFormatException ex) {
                    setErrorMsg("Số lượng không hợp lệ");
                    return;
                }
            }
            if (btPicker.getValue()!=null){
                parsedBt =btPicker.getValue();
            }
            if (rtPicker.getValue()!=null){
                parsedRt =rtPicker.getValue();
            }
            if(btPicker.getValue()!=null && rtPicker.getValue()!=null){
                if(rtPicker.getValue().isBefore(btPicker.getValue())){
                    setErrorMsg("Ngày nhận không được sớm hơn ngày mua");
                    parsedRt=null;
                    return;
                }
            }
            if (twTextField.getText()!=null&&!twTextField.getText().isEmpty()){
                try{
                    Integer.parseInt(twTextField.getText());} catch (NumberFormatException ex) {
                    setErrorMsg("Khung thời gian không hợp lệ");
                    return;
                }
                parsedTw=Integer.parseInt(twTextField.getText());
            }
            if (parsedLink !=null && parsedAmt >0 && parsedBt !=null && parsedRt !=null && parsedTw >=0){
                purchase=new Purchase(parsedLink, parsedAmt, parsedBt, parsedRt, parsedTw);
                ((Stage)submitButton.getScene().getWindow()).close();
            }
            else {
                setErrorMsg("Có giá trị không hợp lệ");
            }
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
    public Purchase getPurchase(){
        return purchase;
    }
}
