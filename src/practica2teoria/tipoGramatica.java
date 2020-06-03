/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2teoria;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniela
 */
public class tipoGramatica {

    private static ArrayList<String> simPila = new ArrayList<>();
    private static ArrayList<String> instrucciones = new ArrayList<>();
    private conjunto GS = new conjunto();
    private Automata Automata = new Automata();
    public static String hileraSiguiente = "";
    public static String hileraPrimeros = "";
    public static ArrayList<Integer> prodAnulables = new ArrayList<>();
    public static ArrayList<String> Nanulables = new ArrayList<>();
    public static List<List<String>> primeros = new ArrayList<List<String>>();
    public static List<List<String>> gramatica = new ArrayList<List<String>>();
    public static ArrayList<String> estados = new ArrayList<>(); // Acá guarda todos los no terminales
    public static ArrayList<String> sEntrada = new ArrayList<>();
    public static List<List<String>> primerosProduccion = new ArrayList<List<String>>();
    public static List<List<String>> siguientesNT = new ArrayList<List<String>>();
    public static List<List<String>> seleccion = new ArrayList<List<String>>();
    public static List<List<String>> tablaAutomata = new ArrayList<List<String>>();
    public static String hileConjuntos = "";

    public static void reinicializarTipoGramatica() {
        hileraSiguiente = "";
        hileraPrimeros = "";
        hileConjuntos = "";
        prodAnulables.clear();
        Nanulables.clear();
        primeros.clear();
        primerosProduccion.clear();
        siguientesNT.clear();
        seleccion.clear();
        tablaAutomata.clear();
        simPila.clear();
        instrucciones.clear();          
    }

    /**
     * Este método saca la estructura de las producciones para luego verificar a
     * que tipo de gramatica corresponde.
     */
    public void posibleG() {
        gramatica = FXMLDocumentController.gramatica;
        estados = FXMLDocumentController.estados;
        sEntrada = FXMLDocumentController.sEntrada;
        for (int i = 0; i < estados.size(); i++) { // arma e inicializa conjunto de primeros para cada N
            primeros.add(new ArrayList<String>());
            primeros.get(i).add(estados.get(i));
            siguientesNT.add(new ArrayList<String>());
            siguientesNT.get(i).add(estados.get(i));
        }
        for (int i = 0; i < gramatica.size(); i++) {
            primerosProduccion.add(new ArrayList<String>());
            seleccion.add(new ArrayList<String>());
        }
        boolean landa = false;
        boolean iniN = false;
        for (int i = 0; i < gramatica.size(); i++) {
            String primer = gramatica.get(i).get(1);
            if (primer.equals("$")) {
                landa = true;
                prodAnulables.add(i);
                String Nanulable = gramatica.get(i).get(0);
                if (!Nanulables.contains(Nanulable)) {
                    Nanulables.add(Nanulable);
                }
            }
            if (estados.contains(primer)) {
                iniN = true;
            }
        }

        evaluarAnulables();

        boolean cumpleCondiciones = hacerPrimeros();
        if (cumpleCondiciones && landa == false && iniN == false) {
            hileConjuntos += "LA GRAMÁTICA ES S. \n";
            System.out.println("La gramática es S");
        }

        for (int i = 0; i < estados.size(); i++) {
            String siguienteH = GS.hileraSiguentes(estados.get(i), "");
            for (int j = 0; j < siguienteH.length(); j++) {
                String filaSiguiente = "" + siguienteH.charAt(j);
                if (!siguientesNT.get(i).contains(filaSiguiente)) {
                    siguientesNT.get(i).add(filaSiguiente);
                }
            }
            hileraSiguiente = "";
        }
        GS.verificaSiguientes();
        conjunto.faltantes.clear();
        GS.conjuntoSeleccion();
        boolean conjuntosSele = verificaSeleccion();

        if (cumpleCondiciones && landa == true && iniN == false && conjuntosSele == true) {
            hileConjuntos += "LA GRAMÁTICA ES Q. \n";
            System.out.println("la gramarica es Q");
        }
        if (cumpleCondiciones && landa == true && iniN == true && conjuntosSele == true) {
            hileConjuntos += "LA GRAMÁTICA ES LL(1) \n";
            System.out.println("la gramarica es LL(1) ");
        }
        
        imprimirComplemento();
        if (conjuntosSele) { // solo si es un gramatica s,q o ll1 crea el automata de pila.
            FXMLGramaticaController.sehaceautomata = true;
           simPila = Automata.simPila();
           Automata.creaTabla();  
        } else{
            FXMLGramaticaController.sehaceautomata = false;
            
        }
    }

    /**
     * Este método solo lo hice para que me muestre en consola todos los
     * conjuntos
     */
    private void imprimirComplemento() {
        for (int i = 0; i < prodAnulables.size(); i++) {
            int k = prodAnulables.get(i) + 1;
            hileConjuntos += "Producciones anulables " + k + "\n";
            System.out.println("producciones anulables " + k);
        }
        for (int i = 0; i < Nanulables.size(); i++) {
            hileConjuntos += "No terminales anulables " + Nanulables.get(i) + "\n";
            System.out.println("No terminales anulables " + Nanulables.get(i));
        }
        hileConjuntos += "PRIMEROS DE CADA NO TERMINAL \n";
        System.out.println("primeros de cada no terminal");
        for (int i = 0; i < primeros.size(); i++) {
            hileConjuntos += "Primeros de " + primeros.get(i).get(0) + "\n";
            System.out.println(" primeros de " + primeros.get(i).get(0));
            for (int j = 1; j < primeros.get(i).size(); j++) {
                hileConjuntos += primeros.get(i).get(j) + "\n";
                System.out.println(primeros.get(i).get(j));
            }
        }
        hileConjuntos += "PRIMEROS DE CADA PRODUCCIÓN \n";
        System.out.println("primeros de cada producción");
        for (int i = 0; i < gramatica.size(); i++) {
            int k = i + 1;
            hileConjuntos += "Primeros de producción: " + k + "\n";
            System.out.println("Pimeros de producción: " + k);
            for (int j = 0; j < primerosProduccion.get(i).size(); j++) {
                hileConjuntos += primerosProduccion.get(i).get(j) + "\n";
                System.out.println(primerosProduccion.get(i).get(j));
            }
        }
        for (int i = 0; i < siguientesNT.size(); i++) {
            hileConjuntos += "Siguientes de :" + siguientesNT.get(i).get(0) + "\n";
            System.out.println("siguientes de: " + siguientesNT.get(i).get(0));
            for (int j = 1; j < siguientesNT.get(i).size(); j++) {
                hileConjuntos += siguientesNT.get(i).get(j) + "\n";
                System.out.println(siguientesNT.get(i).get(j));
            }
        }
        hileConjuntos += "CONJUNTOS DE SELECCIÓN PARA CADA PRODUCCIÓN  \n";
        System.out.println("Conjuntos de seleccion para cada producción");
        for (int i = 0; i < gramatica.size(); i++) {
            int k = i + 1;
            hileConjuntos += "Selección: " + k + "\n";
            System.out.println("seleccion: " + k);
            for (int j = 0; j < seleccion.get(i).size(); j++) {
                hileConjuntos += seleccion.get(i).get(j) + "\n";
                System.out.println(seleccion.get(i).get(j));
            }
        }
        //hileConjuntos += "SIMBOLOS EN LA PILA: \n ";
        System.out.println("simbolos de pila");
        for (int i = 0; i < simPila.size(); i++) {
            hileConjuntos += simPila.get(i) + "\n";
            System.out.println(simPila.get(i));
        }

    }

    /**
     * Este método verifica cada uno de los No terminales si es o no anulable,
     * teniendo como base el conjunto de Nanulables que fue realizado con las
     * producciones <N>->$ (para este proyecto landa es $) lo que hace es
     * verificar si hay alguna producción conformada solo por Nanulables y en
     * caso de existir, el No terminal de su lado izquierdo tambien es anulable,
     * junto con la producción.
     */
    private void evaluarAnulables() {

        for (int i = 0; i < gramatica.size(); i++) {
            boolean anulable = true;
            for (int j = 1; j < gramatica.get(i).size(); j++) {

                String evaluar = gramatica.get(i).get(j);
                if (!Nanulables.contains(evaluar)) {
                    j = gramatica.get(i).size();
                    anulable = false;
                }
            }
            if (anulable == true) {
                String Nterminal = gramatica.get(i).get(0);
                if (!Nanulables.contains(Nterminal)) {
                    Nanulables.add(Nterminal);
                    prodAnulables.add(i);
                }
            }
        }

    }

    /**
     * Clasifica producciones y decide como completar el conjunto de primeros
     * tanto para cada no terminal, como para cada produccion. Posibles tipos de
     * producciones: 1.<N>->xα 2.<N>->$ 3.<N>->β
     *
     * @return False|True
     */
    private boolean hacerPrimeros() {

        boolean cumpleCondiciones = false;
        for (int i = 0; i < gramatica.size(); i++) {
            String primer = gramatica.get(i).get(1);
            if (!estados.contains(primer) && !primer.equals("$")) {
                cumpleCondiciones = GS.primerosDeS(i);
            }
        }
        for (int i = 0; i < gramatica.size(); i++) {
            String primer = gramatica.get(i).get(1);
            if (estados.contains(primer)) {

                String hilePrimeros = GS.primerosQ(i);

                for (int j = 0; j < hilePrimeros.length(); j++) {
                    String filaPrimeros = "" + hilePrimeros.charAt(j);
                    if (!primerosProduccion.get(i).contains(filaPrimeros)) {
                        primerosProduccion.get(i).add(filaPrimeros);
                    }
                    int posi = buscaposPri(gramatica.get(i).get(0));
                    if (!primeros.get(posi).contains(filaPrimeros)) {
                        primeros.get(posi).add(filaPrimeros);
                    }
                }
                hileraPrimeros = "";
            }
        }

        return cumpleCondiciones;
    }

    /**
     * Retorna la posicion en la que se encuentran los primeros de ese No
     * terminal
     *
     * @param NT
     * @return i
     */
    public int buscaposPri(String NT) {
        int k = 0;
        for (int i = 0; i < primeros.size(); i++) {
            if (primeros.get(i).get(0).equals(NT)) {
                return i;
            }
        }
        return k; // si no la encuentra, significa que hay un error.
    }

    /**
     * Este método comprueba que los conjuntos de selección de cada una de las
     * producciones sean disjuntos, para decir si es una gramática LL(1).
     *
     * @return true|false
     */
    private boolean verificaSeleccion() {

        ArrayList<Integer> produccionesRepetidas = new ArrayList<>();

        for (int i = 0; i < estados.size(); i++) {
            produccionesRepetidas = igualLI(estados.get(i));
            if (produccionesRepetidas.size() > 1) {
                for (int j = 0; j < produccionesRepetidas.size() - 1; j++) {
                    for (int k = 1; k < produccionesRepetidas.size(); k++) {
                        for (int l = 0; l < seleccion.get(produccionesRepetidas.get(j)).size(); l++) {
                            if (seleccion.get(produccionesRepetidas.get(k)).contains(seleccion.get(produccionesRepetidas.get(j)).get(l))) {
                                int prod1 = produccionesRepetidas.get(k) + 1;
                                int prod2 = produccionesRepetidas.get(j) + 1;
                                hileConjuntos += "OJO: La gramática no es S, Q o LL(1) porque: \n";
                                System.out.println("La gramática no es S,Q o LL(1) porque");
                                hileConjuntos += "Los conjuntos de selección de las producciones: " + prod1 + "y" + prod2 + "no son disyuntos \n";
                                System.out.println("los conjuntos de seleccion de las producciones : " + prod1 + " y " + prod2 + " no son disyuntos");
                                return false;

                            }
                        }

                    }

                }
            }
        }
        return true;
    }

    /**
     * Busca que producciones tienen el mismo no terminal en el lado izquierdo
     *
     * @NT: no terminal
     * @repetidas
     */
    private ArrayList igualLI(String NT) {
        ArrayList<Integer> repetidas = new ArrayList<>();
        for (int i = 0; i < gramatica.size(); i++) {
            if (gramatica.get(i).get(0).equals(NT)) {
                repetidas.add(i);
            }
        }
        return repetidas;
    }

   

}
