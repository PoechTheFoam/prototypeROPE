package org.example.prototype;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OutputController {
    @FXML
    private Label limitLabel;
    @FXML
    private Label itemLabel;
    @FXML
    private Label predictLabel;
    @FXML
    private Label multiplierLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button denyButton;
    private LocalDate target=LocalDate.now(); //fallback

    public void initialize(){
        confirmButton.setOnAction(e->{
            ((Stage)(denyButton.getScene().getWindow())).close();
        });
        denyButton.setOnAction(e->{
            ((Stage)(denyButton.getScene().getWindow())).close();
        });
    }

    public void setGraphics(LocalDate d1, LocalDate d2, String itemLink, String totalM){
        if (!d1.isAfter(target)){
            limitLabel.setText("Trong phiên bản hoàn thiện, cửa sổ này sẽ xuất hiện ngay vì ngày đặt hàng tối ưu xảy ra trước hoặc trong lần đặt hàng gần nhất/hiện tại, với các thông số như sau:");
            itemLabel.setText(itemLink);
            predictLabel.setText(d2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            multiplierLabel.setText(totalM);
            warningLabel.setText("Hãy đặt hàng ngay lập tức, bạn có nguy cơ muộn nếu không đặt hàng bây giờ (ngày lý tưởng: "+d1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")");
            warningLabel.setVisible(true);
        }
        else {
            limitLabel.setText("Trong phiên bản hoàn thiện, cửa sổ ngày sẽ xuất hiện vào ngày " + d1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " và hiển thị các thông số như sau:");
            itemLabel.setText(itemLink);
            predictLabel.setText(d2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            multiplierLabel.setText(totalM);
        }
    }
    public void setTarget(LocalDate d){
        this.target=d;
    }
}
