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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class FXMLGramaticaController implements Initializable {

    @FXML
    private TextArea hileraGramatica;
    @FXML
    private TextArea hileraConjuntos;
    @FXML
    private Button botonCerrar;
    @FXML
    private Button botonNuevaGramatica;
    @FXML
    private Button botonAutomata;
    private String hileGrama = "";

    /**
     * Inicializa el controlador del fxml y además imprime en el área de texto
     * tanto la gramática como los conjuntos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String grama = imprimirGramatica();
        tipoGramatica tipo = new tipoGramatica();
        tipo.posibleG();
        hileraGramatica.setText(grama);
        hileraConjuntos.setText(tipoGramatica.hileConjuntos);
    }
    
    /**
     * Abre una nueva ventana donde se mostrará el autómata.
     * @param event
     * @throws Exception 
     */
    @FXML
    public void botonAutomata(ActionEvent event) throws Exception{
        //tipoGramatica tipo = new tipoGramatica();
        //tipo.reinicializarTipoGramatica();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAutomata.fxml"));
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
     * Al accionar el botón NuevaGramatica se resetean los arraylist estáticos para que 
     * la aplicación pueda volver a comenzar un proceso de reconocimiento de 
     * gramática y abre nuevamente la ventana inicial.
     * @param event
     * @throws Exception 
     */
    @FXML
    public void botonNuevaGramaticaAccionado(ActionEvent event) throws Exception {
        FXMLDocumentController.reinicializarGramatica();
        //Automata.reinicializarAutomata();
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

    
    /**
     * Imprime en el área de texto de la ventana la gramática que se generó para
     * tener una mejor vista de ella.
     * @return 
     */
    public String imprimirGramatica() {
        for (int i = 0; i <= FXMLDocumentController.gramatica.size() - 1; i++) {

            for (int j = 0; j <= FXMLDocumentController.gramatica.get(i).size() - 1; j++) {
                if (j == 0) {
                    hileGrama = hileGrama + FXMLDocumentController.gramatica.get(i).get(j) + "->";
                } else {
                    hileGrama = hileGrama + FXMLDocumentController.gramatica.get(i).get(j);
                }

            }
            hileGrama = hileGrama + "\n";
        }

        return hileGrama;
    }

    public String getHileGrama() {
        return hileGrama;
    }

    public void setHileGrama(String hileGrama) {
        this.hileGrama = hileGrama;
    }

}
