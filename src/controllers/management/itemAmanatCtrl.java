package controllers.management;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Amanat;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class itemAmanatCtrl implements Initializable {
    @FXML
    private HBox itemBook;

    @FXML
    private Label amtIdLBL;

    @FXML
    private Label ktbNameLBL;

    @FXML
    private Label usrNameLBL;

    @FXML
    private Label mohlatLBL;

    @FXML
    private ImageView elanImg;

    @FXML
    private Button detailsBTN;

    static Stage detailsAmanatPage = null;
    static String amtID = null;
    static String ktbName = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detailsBTN.setOnAction(e -> {
            amtID = amtIdLBL.getText();
            ktbName = ktbNameLBL.getText();
            try {
                openDetailsAmanatPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void setItemAmanat(Amanat amanat) {
        amtIdLBL.setText(amanat.getAmtID());
        ktbNameLBL.setText(amanat.getKtbName());
        usrNameLBL.setText(amanat.getUsrName());
        mohlatLBL.setText((amanat.getMohlat()));
        if (amanat.getAmtDarkhastUsr().equals("1")){
            elanImg.setVisible(true);
        }

    }

    private void openDetailsAmanatPage() throws IOException {
        if (detailsAmanatPage == null) {
            Pane root = FXMLLoader.load(getClass().getResource("../../view/fxmls/management/detailsAmanatPage.fxml"));
            detailsAmanatPage = new Stage();
            detailsAmanatPage.initStyle(StageStyle.UNDECORATED);
            detailsAmanatPage.setScene(new Scene(root));
            detailsAmanatPage.show();

        }
    }
}
