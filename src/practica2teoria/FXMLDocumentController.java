/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2teoria;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author SIMON
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField NTProduccion;
    @FXML
    private TextField hileraPT;
    @FXML
    private TextField hileraPNT;
    @FXML
    private Button btnAceptarNTP;
    @FXML
    private Button botonAceptarNTT;
    @FXML
    private Button botonT;
    @FXML
    private Button botonNT;
    @FXML
    private Button aceptarPT;
    @FXML
    private Button aceptarPNT;

    @FXML
    private Button botonLambda;
    @FXML
    private Button botonSiguiente;
    @FXML
    private Button botonTerminar;

    private static boolean esCorrecta = true;

    private static String produccion = "";
    public static ArrayList<String> estados = new ArrayList<>();
    public static ArrayList<String> sEntrada = new ArrayList<>();
    public static List<List<String>> gramatica = new ArrayList<List<String>>();
    private static int numeroProducciones;
    public static int contadorProducciones = 0;

    /**
     * Método que reestablece la gramática para que pueda ingresar una nueva.
     */
    public static void reinicializarGramatica() {
        produccion = "";
        estados.clear();
        sEntrada.clear();
        gramatica.clear();
        contadorProducciones = 0;
    }

    /**
     * Ejecuta el método guardaG() y permite que se ingrese una nueva producción
     * para la gramática Después de usar el botón se permite terminar la
     * gramática.
     *
     * @param event
     */
    @FXML
    public void botonSiguienteProduccion(ActionEvent event) {
        if (contadorProducciones >= 1) {
            botonTerminar.setDisable(false);
        }
        guardaG();
        contadorProducciones++;
        NTProduccion.setDisable(false);
        botonSiguiente.setDisable(true);
        aceptarPT.setDisable(true);
        NTProduccion.setText("");
        setProduccion("");
        botonT.setDisable(true);
        botonNT.setDisable(true);
        botonLambda.setDisable(true);
        botonTerminar.setDisable(false);

    }

    /**
     * Ejecuta el método verificarEstados() para que se ingrese una gramática
     * correcta y en caso de no haber problemas abre una nueva ventana donde se
     * mostrarán los resultados , en caso contrario, mostrará una notificación y
     * permitirá que se ingrese la gramática de nuevo.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    public void botonTerminarAccionado(ActionEvent event) throws Exception {
        if (verificarEstados() == true) {
            tipoGramatica tipo = new tipoGramatica();
            tipoGramatica.reinicializarTipoGramatica();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLGramatica.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.show();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        } else {
            ventanaEmergente("ERROR", "NT no definido. Comience nuevamente");
            reinicializarGramatica();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.show();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            //tipoGramatica.hileConjuntos = "";
        }

    }

    /**
     * Controla que los caracteres que se ingresen en la hilera para el no
     * terminal del lado izquierdo de la producción sean solo letras válidas.
     *
     * @param keyEvent
     */
    @FXML
    public void NTProduccionHilera(javafx.scene.input.KeyEvent keyEvent) {
        NTProduccion.setTextFormatter(new TextFormatter<String>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {

                return null;
            } else {
                return change;
            }
        }));

        char car = keyEvent.getCharacter().charAt(0);
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')) {
            keyEvent.consume();
        }
        btnAceptarNTP.setDisable(false);

    }

    /**
     * Controla que solo se ingrese un caracter en la hilera donde se registran
     * los terminales de la producción.
     *
     * @param keyEvent
     */
    @FXML
    public void hileraPTManejo(javafx.scene.input.KeyEvent keyEvent) {
        hileraPT.setTextFormatter(new TextFormatter<String>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {

                return null;
            } else {
                return change;
            }
        }));
        aceptarPT.setDisable(false);

    }

    /**
     * Controla que solo se ingrese un caracter en la hilera donde se registran
     * los NO terminales de la producción y que solo se permitan letras.
     *
     * @param keyEvent
     */
    @FXML
    public void hileraPNTManejo(javafx.scene.input.KeyEvent keyEvent) {
        hileraPNT.setTextFormatter(new TextFormatter<String>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {

                return null;
            } else {
                return change;
            }
        }));

        char car = keyEvent.getCharacter().charAt(0);
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')) {
            keyEvent.consume();
        }
        aceptarPNT.setDisable(false);

    }

    /**
     * Método para que el botón con el signo "$" agregue una producción nula, es
     * decir que solo contenga el símbolo lambda que en nuestro caso es el
     * anteriormente mencionado y pasa directamente a una nueva producción.
     *
     * @param event
     */
    @FXML
    public void botonLambdaAccionado(ActionEvent event) {

        setProduccion(produccion + "$");
        guardaG();
        contadorProducciones++;
        NTProduccion.setDisable(false);
        botonSiguiente.setDisable(true);
        aceptarPT.setDisable(true);
        NTProduccion.setText("");
        setProduccion("");
        botonT.setDisable(true);
        botonNT.setDisable(true);
        botonLambda.setDisable(true);
        botonTerminar.setDisable(false);

    }

    /**
     * Botón que acepta lo que haya en la caja de texto del No Terminal de la
     * producción y le agrega al no terminal sus respectivos símbolos "(<>)" más
     * el indicador "->" para que pueda ser procesado por los otros algoritmos.
     *
     * @param event
     */
    @FXML
    public void botonAceptarNTP(ActionEvent event) {
        if (NTProduccion.getText().length() != 0) {
            NTProduccion.setDisable(true);
            btnAceptarNTP.setDisable(true);
            botonT.setDisable(false);
            botonNT.setDisable(false);
            if(contadorProducciones > 0){
                botonLambda.setDisable(false);
            }
            
            aceptarPNT.setDisable(true);
            String prod = NTProduccion.getText().toUpperCase();
            setProduccion(produccion + "<" + prod + ">" + "->");
            System.out.println(produccion);
            botonTerminar.setDisable(true);

        } else {
            btnAceptarNTP.setDisable(true);

        }

    }

    /**
     * Acepta el terminal que se ingrese en la caja de texto del terminal de la
     * producción y permite que se ingresen más T O NT.
     *
     * @param event
     */
    @FXML
    public void botonAceptarPT(ActionEvent event) {
        if (hileraPT.getText().length() != 0) {
            hileraPT.setDisable(true);
            botonNT.setDisable(false);
            botonT.setDisable(false);
            aceptarPT.setDisable(true);
            setProduccion(produccion + hileraPT.getText().toLowerCase());
            System.out.println(produccion);
            hileraPT.setText("");
            botonSiguiente.setDisable(false);
            botonLambda.setDisable(true);
            //botonTerminar.setDisable(false);

        }
    }

    /**
     * Acepta el texto que haya en la caja de texto del NO Terminal del lado
     * derecho de la producción y permite ingresar otros T o NT.
     *
     * @param event
     */
    @FXML
    public void botonAceptarPNT(ActionEvent event) {
        if (hileraPNT.getText().length() != 0) {
            hileraPNT.setDisable(true);
            botonNT.setDisable(false);
            botonT.setDisable(false);
            aceptarPNT.setDisable(true);
            String prod = hileraPNT.getText().toUpperCase();
            setProduccion(produccion + "<" + prod + ">");
            System.out.println(produccion);
            System.out.println(produccion);
            hileraPNT.setText("");
            botonSiguiente.setDisable(false);
            //botonTerminar.setDisable(false);

        }
    }

    /**
     * Habilita la caja de texto para ingresar un terminal a la producción y
     * deshabiita cualquier acción que genere un error en la gramática.
     *
     * @param event
     */
    @FXML
    public void botonTAccionado(ActionEvent event) {
        hileraPT.setDisable(false);
        botonNT.setDisable(true);
        botonT.setDisable(true);
        botonLambda.setDisable(true);

    }

    /**
     * Habilita la caja de texto para la entrada de un NO terminal del lado
     * derecho de la producción y deshabilita cualquier posible error.
     *
     * @param event
     */
    @FXML
    public void botonNTAccionado(ActionEvent event) {
        hileraPNT.setDisable(false);
        botonNT.setDisable(true);
        botonT.setDisable(true);
        botonLambda.setDisable(true);
    }

    /**
     * Guarda toda la gramática en una multilista, donde cada columna indica una
     * producción y la primer fila contiene cada uno de los No terminales del
     * lado izquierdo de la producción.
     */
    private static void guardaG() {

        gramatica.add(new ArrayList<String>());
        String nT = cogerNoTerminal(0, produccion);
        for (int j = 0; j < produccion.length(); j++) {
            if (j == 0) {

                gramatica.get(contadorProducciones).add(nT); // agrega el  N del lado derecho de la producción 
                j = 4;// mire bien
                if (!estados.contains(nT)) { // verifica que el estado no este agregado antes de agregarlo
                    estados.add(nT);
                }

            } else {

                if (produccion.charAt(j) == '<' && produccion.length() > j + 1) {
                    String s = "" + produccion.charAt(j + 1);
                    if (esMayuscula(s)) { // Solo lo toma como un N en caso de que el siguiente a < sea mayuscula
                        nT = cogerNoTerminal(j, produccion);
                        gramatica.get(contadorProducciones).add(nT);
                        j = j + 2;

                        if (!estados.contains(nT)) { // verifica que el estado no este agregado
                            estados.add(nT);
                        }

                    } else { // toma el < como un simbolo y lo agrega
                        gramatica.get(contadorProducciones).add(Character.toString(produccion.charAt(j)));
                        if (!sEntrada.contains(Character.toString(produccion.charAt(j)))) { // verifica que el estado no este agregado
                            sEntrada.add(Character.toString(produccion.charAt(j)));
                        }
                    }

                } else {
                    gramatica.get(contadorProducciones).add(Character.toString(produccion.charAt(j)));
                    if (!sEntrada.contains(Character.toString(produccion.charAt(j)))&& !Character.toString(produccion.charAt(j)).equals("$")) { // verifica que el estado no este agregado
                        sEntrada.add(Character.toString(produccion.charAt(j)));
                    }
                }
            }
        }

        imprimir();
    }

    /**
     * Verifica que esten todos los no terminales definidos, lo que quiere decir
     * que si un terminal aparece en el lado derecho, debe tener al menos una
     * producción donde se defina.
     *
     */
    public boolean verificarEstados() { // verifica que todos los estados esten definidos 
        for (int i = 0; i < estados.size(); i++) {
            boolean encontro = false;
            for (int j = 0; j < gramatica.size(); j++) {
                if (estados.get(i).equals(gramatica.get(j).get(0))) {
                    encontro = true;
                    j = gramatica.size();
                }
            }

            if (encontro == false) {
                return esCorrecta = false;

            }

        }
        return esCorrecta = true;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public static String getProduccion() {
        return produccion;
    }

    public static void setProduccion(String aProduccion) {
        produccion = aProduccion;
    }

    public static int getNumeroProducciones() {
        return numeroProducciones;
    }

    public static void setNumeroProducciones(int aCantidadProducciones) {
        numeroProducciones = aCantidadProducciones;
    }
    /**
     * Método exclusivo para verificar el funcionamiento de los métodos por parte de los desarrolladores.
     */
    private static void imprimir() {
        for (int i = 0; i < gramatica.size(); i++) {
            String fila = "";
            for (int j = 0; j < gramatica.get(i).size(); j++) {
                fila += "  " + gramatica.get(i).get(j);
                if (j == gramatica.get(i).size() - 1) {
                    System.out.println(fila);
                }
            }
        }

    }

    /**
     * verifica si el String ingresado esta en mayuscula
     *
     * @param s: es el string que va a evaluar
     * @return true|false.
     */
    private static boolean esMayuscula(String s) {
        // Regresa el resultado de comparar la original con sun versión mayúscula
        return s.equals(s.toUpperCase());
    }

    /**
     * Separa los no terminales, los cuales reconocemos por el <> de la
     * producción ingresada
     *
     * @param ini: indica donde esta el <
     * @param producción: hilera ingresada
     * @return nT: retorna un string donde se encuenta el no terminal.
     */
    private static String cogerNoTerminal(int ini, String produccion) { // cuando es un terminal coge los 3 caracteres 
        String nT = "";
        for (int i = ini; i < ini + 3; i++) {
            nT += produccion.charAt(i);
        }
        return nT;
    }
    
    /**
     * Genera una ventana emergente con un mensaje de error.
     * @param texto
     * @param mensaje 
     */
    public static void ventanaEmergente(String texto, String mensaje) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(texto);
        ventana.setMinWidth(250);
        ventana.setMinHeight(250);
        Label label = new Label();
        label.setText(mensaje);
        Button botonCerrar = new Button("Cerrar");
        botonCerrar.setOnAction(e -> ventana.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, botonCerrar);
        layout.setAlignment(Pos.CENTER);
        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.showAndWait();

    }

}
