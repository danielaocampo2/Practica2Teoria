/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2teoria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author SIMON
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField NTProduccion;
    @FXML
    private TextField hileraProduccion;
    @FXML
    private Button btnAceptarNTP;
    @FXML
    private Button botonAceptarNTT;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonAbre;
    @FXML
    private Button botonCierra;
    @FXML
    private Button btnBorrarUlt;

    @FXML
    public void NTProduccionHilera(javafx.scene.input.KeyEvent keyEvent) {
        char car = keyEvent.getCharacter().charAt(0);
        if (car < 'A' || car > 'Z') {
            keyEvent.consume();
        }

        NTProduccion.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                    event.consume(); // to cancel character-removing keys               
                }
            }
        });
        if (NTProduccion.getText().length() == 0) {
            btnAceptarNTP.setDisable(true);
        }
        btnAceptarNTP.setDisable(false);
        botonBorrar.setDisable(false);

    }

    @FXML
    public void hileraProd(javafx.scene.input.KeyEvent keyEvent) {
        char car = keyEvent.getCharacter().charAt(0);
        if (((car < 'a' || car > 'z') && (car < 'A' || car > 'Z'))) {
            keyEvent.consume();
        }
        if (!hileraProduccion.getText().isEmpty()) {
            if (hileraProduccion.getText().charAt(hileraProduccion.getText().length() - 1) == '<') {
                botonCierra.setDisable(false);
                if (car < 'A' || car > 'Z') {
                    keyEvent.consume();
                }

            }

        }

    }

    @FXML
    public void botonBorrarNTP(ActionEvent event) {
        NTProduccion.setText("");
        btnAceptarNTP.setDisable(true);
    }

    @FXML
    public void botonAceptarNTP(ActionEvent event) {
        NTProduccion.setDisable(true);
        botonBorrar.setDisable(true);
        btnAceptarNTP.setDisable(true);
        hileraProduccion.setDisable(false);
        botonAbre.setDisable(false);
    }

    @FXML
    public void botonAbreAcc(ActionEvent event) {
        hileraProduccion.setText(hileraProduccion.getText() + "<");
        botonAbre.setDisable(true);
        btnBorrarUlt.setDisable(true);
    }

    @FXML
    public void botonCierraAcc(ActionEvent event) {
        hileraProduccion.setText(hileraProduccion.getText() + ">");
        botonCierra.setDisable(true);
        botonAbre.setDisable(false);
        btnBorrarUlt.setDisable(false);
    }
    
    @FXML
    public void btnBorrarUltAcc(ActionEvent event){
        String hilera = hileraProduccion.getText();
        String subHilera = "";
        char ultCar = hilera.charAt(hilera.length()-1);
        if(ultCar=='>'){
            //FALTA TERMINAR ESTO

            
        }
        
    }
    public void recorreNT(String hilera){
        String hileraAux="";
        //char car = hilera.charAt(hilera.length())
    }
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
