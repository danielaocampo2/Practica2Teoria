/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2teoria;

import java.util.ArrayList;

/**
 *
 * @author Daniela
 */
public class Automata {

    private static ArrayList<String> simPila = new ArrayList<>();
    public ArrayList<String> sEntra = new ArrayList<>();
    public ArrayList<String> instrucciones = new ArrayList<>();
    public static String hileraAutomata = "";
    public static String hilerasimpila = "";

    /**
     * Encuentra los símbolos de la pila, evaluando cada una de las producciones
     * y buscando los terminales que se encuentren un un α o β y agregando
     * "vacia" que es lo que remplazara al simbolo ▼.
     *
     */
    
    public static void reinicializarAutomata(){
        simPila.clear();
        hileraAutomata = "";
        hilerasimpila = "";
    }

    public ArrayList simPila() {

        for (int i = 0; i < tipoGramatica.estados.size(); i++) {
            simPila.add(tipoGramatica.estados.get(i));
        }
        for (int i = 0; i < tipoGramatica.gramatica.size(); i++) {
            int k = 1;
            if (!tipoGramatica.estados.contains(tipoGramatica.gramatica.get(i).get(1))) {
                k = 2;
            }

            for (int j = k; j < tipoGramatica.gramatica.get(i).size(); j++) {
                if (!tipoGramatica.estados.contains(tipoGramatica.gramatica.get(i).get(j))) {
                    if (!simPila.contains(tipoGramatica.gramatica.get(i).get(j))) {
                        simPila.add(tipoGramatica.gramatica.get(i).get(j));
                    }
                }
            }
        }
        simPila.add("vacia");
     return simPila;
    }

    /**
     * Llena la primera fila del automata con los símbolos de entrada y la
     * primer columna con los símbolos de la pila.
     *
     *
     */
    public void creaTabla() {

        sEntra = tipoGramatica.sEntrada;
        hileraAutomata+= " AUTOMATA \n";
        System.out.println(" AUTOMATA ");
        sEntra.add("~");
        int a = sEntra.size() + 1;
        String[][] automata = new String[simPila.size() + 1][a];
        for (int i = 1; i < automata.length; i++) {
            automata[i][0] = simPila.get(i - 1);
        }
        for (int i = 1; i < automata[0].length; i++) {
            automata[0][i] = sEntra.get(i - 1);
        }

        automata[simPila.size()][sEntra.size()] = "Acepte";

        automata[0][0] = "";
        creaAutomata(automata);

        for (String[] row : automata) {
            printRow(row);
        }
        hileraAutomata += "Instrucciones correspondientes: \n";
        System.out.println("Instrucciones correspondientes:");
        for (int i = 0; i < instrucciones.size(); i++) {
            int k=i+1;
            hileraAutomata+= k + ": significa:  \n";
            System.out.println(k+": significa: ");
            hileraAutomata+= instrucciones.get(i) + "\n";
            System.out.println(instrucciones.get(i));
        }

    }

    /**
     * Llena la tabla automata con las correpondientes instrucciones de acuerdo
     * a las caracteristicas de la producción.
     *
     * @param automata.
     *
     */
    public  void creaAutomata(String[][] automata) {

        for (int i = 0; i < tipoGramatica.gramatica.size(); i++) {
            if (!tipoGramatica.estados.contains(tipoGramatica.gramatica.get(i).get(1))) {
                if (tipoGramatica.gramatica.get(i).get(1).equals("$")) {
                    ArrayList<Integer> posicionSE = new ArrayList<>();
                    instrucciones.add("Desapile,retenga");
                    posicionSE = pSeleccion(i, automata);
                    int sp = buscaSP(tipoGramatica.gramatica.get(i).get(0), automata);
                    int pro = i + 1;
                    String prod = pro + "";
                    for (int j = 0; j < posicionSE.size(); j++) {
                        automata[sp][posicionSE.get(j)] = prod;
                    }
                } else {
                    if (2 == tipoGramatica.gramatica.get(i).size()) {
                        instrucciones.add("Desapile,avance");
                    } else {
                        String hilera = alfaoBeta(i, true);
                        instrucciones.add("Replace " + hilera + ", avance");
                    }
                    int sp = buscaSP(tipoGramatica.gramatica.get(i).get(0), automata);
                    int se = buscaSE(tipoGramatica.gramatica.get(i).get(1), automata);
                    int pro = i + 1;
                    String prod = pro + "";
                    automata[sp][se] = prod;
                }
            } else {
                String hilera = alfaoBeta(i, false);
                instrucciones.add("Replace " + hilera + ", retenga");
                agregaConjuntos(i, automata);
            }

        }
        instrucciones.add("Desapile avance");
        instrucciones.add("null: Rechace");
        llenaT(automata);

    }

    /**
     * Agrega la instrucción correspondiente en el conjunto de selección de la
     * producción
     *
     * @param i: número de producción
     * @param automata
     */
    public void agregaConjuntos(int i, String[][] automata) {

        ArrayList<Integer> posicionSE = new ArrayList<>();
        posicionSE = pSeleccion(i, automata);
        int sp = buscaSP(tipoGramatica.gramatica.get(i).get(0), automata);
        int pro = i + 1;
        String prod = pro + "";
        for (int j = 0; j < posicionSE.size(); j++) {
            automata[sp][posicionSE.get(j)] = prod;
        }
    }

    /**
     * Imprime la tabla
     *
     */

    public void printRow(String[] row) {
        for (String i : row) {
            hileraAutomata+= i + "\t";
            System.out.print(i);
            System.out.print("\t");
        }
        hileraAutomata+= "\n";
        System.out.println();
    }

    /**
     * Busca la posición donde se encuentra el símbolo en la pila.
     *
     * @param sp:simbolo en la pila
     * @param automata
     * @return i: posición en la tabla.
     */
    public int buscaSP(String sp, String[][] automata) {
        for (int i = 1; i < automata.length; i++) {
            if (sp.equals(automata[i][0])) {
                return i;
            }
        }
        return -5;
    }

    /**
     * Busca la posición donde se encuentra el símbolo de entrada.
     *
     * @param se:simbolo de entrada
     * @param automata
     * @return i: posicion en la tabla.
     */
    public int buscaSE(String se, String[][] automata) {
        for (int i = 0; i < automata[0].length; i++) {
            if (se.equals(automata[0][i])) {
                return i;
            }
        }
        return -5;
    }

    /**
     * retorna un lista con los conjuntos de selección de esa producción
     *
     * @param i: número de producción.
     * @param automata
     * @return posicion
     */
    public ArrayList pSeleccion(int i, String[][] automata) {
        ArrayList<Integer> posicion = new ArrayList<>();
        for (int j = 0; j < tipoGramatica.seleccion.get(i).size(); j++) {
            for (int k = 0; k < automata[0].length; k++) {
                if (tipoGramatica.seleccion.get(i).get(j).equals(automata[0][k])) {
                    posicion.add(k);
                }
            }
        }
        return posicion;
    }

    /**
     * Retorna el alfa o el beta en reversa.
     *
     * @param produ: número de la producción.
     * @param alfa: es verdadero cuando es una produccion con alfa
     * @return hile.
     */
    public String alfaoBeta(int produ, boolean alfa) {
        String hile = "";
        int ini;
        if (alfa) {
            ini = 2;
        } else {
            ini = 1;
        }
        for (int i = tipoGramatica.gramatica.get(produ).size() - 1; i >= ini; i--) {
            hile += tipoGramatica.gramatica.get(produ).get(i);
        }
        return hile;
    }

    /**
     * Este método busca si hay terminales en el simbolo de pila y caso de ser
     * así busca en los simbolos de entrada para poner la instrucción desapile,
     * avace.
     *
     * @param automata.
     */
    public void llenaT(String[][] automata) {
        for (int i = 1; i < automata.length - 1; i++) {
            String T = automata[i][0];
            if (!tipoGramatica.estados.contains(T)) {
                int se = buscaSE(T, automata);
                String prod = tipoGramatica.gramatica.size() + 1 + "";
                automata[i][se] = prod;
            }
        }
    }
}
