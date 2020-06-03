/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2teoria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class FXMLAutomataController implements Initializable {

    @FXML
    private TextArea cajaTextoAutomata;
    @FXML 
    private Button botonNuevaGramatica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaTextoAutomata.setText(Automata.hileraAutomata);
    }
    @FXML
    public void botonNuevaGramaticaAccionado(ActionEvent event) throws Exception {
        FXMLDocumentController.reinicializarGramatica();
        Automata.reinicializarAutomata();
        //tipoGramatica tipo = new tipoGramatica();
        //tipo.reinicializarTipoGramatica();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.setResizable(false);
        stage1.show();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
        /**
     * Termina la aplicación.
     * @param event
     * @throws Exception 
     */
    @FXML
    public void botonCerrarAccionado(ActionEvent event) throws Exception{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
