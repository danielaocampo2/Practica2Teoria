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
public class conjunto {

    public List<List<String>> gramatica = new ArrayList<List<String>>();
    private static ArrayList<String> estados = new ArrayList<>();
    private static ArrayList<String> sEntrada = new ArrayList<>();
    public static ArrayList<String> faltantes = new ArrayList<>();
    /**
     * Llena lista de primeros primeros de cada terminal y de cada producción,
     * cuando la producción es de la forma <N>->xα y verifica que se cumpla la
     * condición que es, si dos producciones tienen igual lado izquiedo, x debe
     * ser diferente.
     *
     * @param produccion
     * @return false|true: falso si no cumple la condición y true si la cumple
     */
    public boolean primerosDeS(int produccion) {

        gramatica = FXMLDocumentController.gramatica;
        estados = FXMLDocumentController.estados;
        sEntrada = FXMLDocumentController.sEntrada;

        String Nt = gramatica.get(produccion).get(0);
        String nuevoPrimero = gramatica.get(produccion).get(1);
        int posicionColumna = buscaPosN(Nt);
        if (!verificaSiEstaPrimeros(posicionColumna, nuevoPrimero)) { // si aun no esta en el conjunto de primeros lo agrega
            tipoGramatica.primeros.get(posicionColumna).add(nuevoPrimero);
            tipoGramatica.primerosProduccion.get(produccion).add(nuevoPrimero);
        } else {

            System.out.println("no es una gramatica S,Q o ll(1) porque el no terminal " + Nt + "tiene mas de una producción que comienza con el mismo simbolo");
            return false;
        }
        return true;

    }

    /**
     * Verifica que se cumpla la condición que es, si dos producciones tienen
     * igual lado izquiedo, x debe ser diferente.
     *
     * @param posicionColumna,nuevoPrimero
     * @return estaX
     */
    private boolean verificaSiEstaPrimeros(int posicionColumna, String nuevoPrimero) {
        boolean estaX = false;
        for (int j = 0; j < tipoGramatica.primeros.get(posicionColumna).size(); j++) {
            if (tipoGramatica.primeros.get(posicionColumna).get(j).equals(nuevoPrimero)) {
                estaX = true;
                System.out.println("encontro simbolo repetido");
                return estaX;
            }
        }

        return estaX;
    }

    /**
     * Retorna la posicion en la que se encuentran los primeros de ese No
     * terminal
     *
     * @param Nterminal
     * @return i: retorna -5 si no lo encuentra.
     */
    private int buscaPosN(String Nterminal) {
        for (int i = 0; i < tipoGramatica.primeros.size(); i++) {
            if (tipoGramatica.primeros.get(i).get(0).equals(Nterminal)) {
                return i;
            }
        }
        return -5;
    }

    /**
     * Retorna hilera de primeros,cuando la producción es de la forma <N>->β
     *
     * @param produccion
     * @return hileraPrimeros.
     */
    public String primerosQ(int produccion) {
        int Tope = buscaTope(produccion);

        if (tipoGramatica.Nanulables.contains(gramatica.get(produccion).get(Tope)) && Tope != gramatica.get(produccion).size() - 1) {
            if (!estados.contains(gramatica.get(produccion).get(Tope + 1))) {

                tipoGramatica.hileraPrimeros += gramatica.get(produccion).get(Tope + 1);
            }
        }
        for (int j = Tope; j > 0; j--) {
            String derTerminal = gramatica.get(produccion).get(j);
            for (int i = 0; i < gramatica.size(); i++) {
                if (gramatica.get(i).get(0).equals(derTerminal)) {
                    if (estados.contains(gramatica.get(i).get(1)) && !gramatica.get(i).get(0).equals(gramatica.get(produccion).get(0))) {
                        tipoGramatica.hileraPrimeros += primerosQ(i);

                    }
                }
            }

            int posi = buscaPosN(gramatica.get(produccion).get(j));

            for (int i = 1; i < tipoGramatica.primeros.get(posi).size(); i++) {
                tipoGramatica.hileraPrimeros += tipoGramatica.primeros.get(posi).get(i);
            }

        }

        return tipoGramatica.hileraPrimeros;

    }

    /**
     * Retorna la posición el último No terminal anulable consecutivo.
     *
     * @param produccion
     * @return i.
     */

    private int buscaTope(int produccion) {
        for (int i = 1; i < gramatica.get(produccion).size(); i++) {
            if (!tipoGramatica.Nanulables.contains(gramatica.get(produccion).get(i))) {
                if (i == 1) {
                    return i;
                } else {
                    return i - 1;
                }
            }
        }
        return gramatica.get(produccion).size() - 1; // significa que el la última posición de la producción es anulable y los anteriores a ella tambien.
    }

    /**
     * Realiza conjunto de siguientes para cada no terminal Este ~ símbolo en
     * nuestro ejemplo significa fin de secuencia
     *
     * @param NT
     * @return hileraSiguiente.
     */

    public String hileraSiguentes(String NT, String NT2) {
        if (NT.equals(estados.get(0))) {
            tipoGramatica.hileraSiguiente += "~";
        }
        for (int i = 0; i < gramatica.size(); i++) { // Busca el no terminal en el lado derecho de cada una de las producciones
            for (int j = 1; j < gramatica.get(i).size(); j++) {
                if (gramatica.get(i).get(j).equals(NT)) {
                    if (j == gramatica.get(i).size() - 1) {
                        if (!gramatica.get(i).get(0).equals(gramatica.get(i).get(j)) && !gramatica.get(i).get(0).equals(NT2) ) { // verifica si esta al final de la producción.
                            return tipoGramatica.hileraSiguiente += hileraSiguentes(gramatica.get(i).get(0),NT);
                        }
                    } else if (!estados.contains(gramatica.get(i).get(j + 1))) {
                        tipoGramatica.hileraSiguiente += gramatica.get(i).get(j + 1);
                    } else {
                            int posiSiguientes = buscaPosN(gramatica.get(i).get(j));
                            int posi = buscaPosN(gramatica.get(i).get(j + 1));
                            for (int k = 1; k < tipoGramatica.primeros.get(posi).size(); k++) {
                                if (!tipoGramatica.siguientesNT.get(posiSiguientes).contains(tipoGramatica.primeros.get(posi).get(k))) {
                                    tipoGramatica.siguientesNT.get(posiSiguientes).add(tipoGramatica.primeros.get(posi).get(k));
                                }
                            } 
                        if (tipoGramatica.Nanulables.contains(gramatica.get(i).get(j + 1))) {
                            int topeSiguientes = topeSiguente(i, j + 1);
                            for (int k = topeSiguientes; k >= j + 1; k--) {
                                if (k == gramatica.get(i).size() - 1) {
                                    if(gramatica.get(i).get(0).equals(gramatica.get(i).get(k))){
                                        faltantes.add(gramatica.get(i).get(k));
                                        faltantes.add(gramatica.get(i).get(j));
                                      
                                    }
                                    if (!gramatica.get(i).get(0).equals(gramatica.get(i).get(k)) && !gramatica.get(i).get(0).equals(NT2)) {
                                       return tipoGramatica.hileraSiguiente += hileraSiguentes(gramatica.get(i).get(0),NT);
                                    }
                                } else {
                                    if (!estados.contains(gramatica.get(i).get(k + 1))) {
                                        tipoGramatica.hileraSiguiente += gramatica.get(i).get(k + 1);
                                    }
                                    int posiSiguiente = buscaPosN(gramatica.get(i).get(k - 1));
                                    int pos = buscaPosN(gramatica.get(i).get(k));
                                    for (int p = 1; p < tipoGramatica.primeros.get(pos).size(); p++) {
                                        if (!tipoGramatica.siguientesNT.get(posiSiguiente).contains(tipoGramatica.primeros.get(pos).get(p))) {
                                            tipoGramatica.siguientesNT.get(posiSiguiente).add(tipoGramatica.primeros.get(pos).get(p));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return tipoGramatica.hileraSiguiente;
    }
    
    public void verificaSiguientes() {
        for (int i = 0; i < faltantes.size(); i++) {
            int pos1 = buscaPosN(faltantes.get(i));
            int pos2 = buscaPosN(faltantes.get(i + 1));
            for (int j = 1; j < tipoGramatica.siguientesNT.get(pos1).size(); j++) {
                if (!tipoGramatica.siguientesNT.get(pos2).contains(tipoGramatica.siguientesNT.get(pos1).get(j))) {
                    tipoGramatica.siguientesNT.get(pos2).add(tipoGramatica.siguientesNT.get(pos1).get(j));
                }
            }
            
            for (int j = 0; j < tipoGramatica.gramatica.size(); j++) {  
                if (tipoGramatica.gramatica.get(j).get(0).equals(faltantes.get(i + 1))) {
                    int ultimaPos = tipoGramatica.gramatica.get(j).size() - 1;
                    if (tipoGramatica.estados.contains(tipoGramatica.gramatica.get(j).get(ultimaPos))) {
                        if (!tipoGramatica.gramatica.get(j).get(ultimaPos).equals(faltantes.get(i + 1))) {
                            int pos3 = buscaPosN(tipoGramatica.gramatica.get(j).get(ultimaPos));
                            for (int k = 1; k < tipoGramatica.siguientesNT.get(pos2).size(); k++) {
                                if (!tipoGramatica.siguientesNT.get(pos3).contains(tipoGramatica.siguientesNT.get(pos2).get(k))) {
                                    tipoGramatica.siguientesNT.get(pos3).add(tipoGramatica.siguientesNT.get(pos2).get(k));
                                }
                            }
                        }
                    }
                }  
            }
            i = i + 1;
        }
        ultimaVerificacion();
    }
    
    public void ultimaVerificacion() {
        for (int i = 0; i < tipoGramatica.gramatica.size(); i++) {
            int ultimo = tipoGramatica.gramatica.get(i).size() - 1;
            if (tipoGramatica.estados.contains(tipoGramatica.gramatica.get(i).get(ultimo))) {
                if (!tipoGramatica.gramatica.get(i).get(0).equals(tipoGramatica.gramatica.get(i).get(ultimo))) {
                    int pos1 = buscaPosN(tipoGramatica.gramatica.get(i).get(0));
                    int pos2 = buscaPosN(tipoGramatica.gramatica.get(i).get(ultimo));
                    for (int j = 1; j < tipoGramatica.siguientesNT.get(pos1).size(); j++) {
                        if (!tipoGramatica.siguientesNT.get(pos2).contains(tipoGramatica.siguientesNT.get(pos1).get(j))) {

                            tipoGramatica.siguientesNT.get(pos2).add(tipoGramatica.siguientesNT.get(pos1).get(j));
                        }
                    }
                }
            }
        }
    }

    /**
     * Busca cuantos Terminales anulables comenzando desde la posición+1 donde
     * encontro el <N> al que se le buscan los siguientes retorna la última
     * posición en la que fue anulable.
     *
     * @param produccion: numero de produccion
     * @param posicion: posicion siguiente a la de los siguientes del <N> que se
     * esta buscando
     * @return i.
     */
    private int topeSiguente(int produccion, int posicion) {
        for (int i = posicion; i < gramatica.get(produccion).size(); i++) {
            if (!tipoGramatica.Nanulables.contains(gramatica.get(produccion).get(i))) {
                if (i == posicion) {
                    return i;
                } else {
                    return i - 1;
                }
            }
        }
        return gramatica.get(produccion).size() - 1;
    }

    public void conjuntoSeleccion() {
        for (int i = 0; i < tipoGramatica.seleccion.size(); i++) {
            for (int j = 0; j < tipoGramatica.primerosProduccion.get(i).size(); j++) {
                if (!tipoGramatica.seleccion.get(i).contains(tipoGramatica.primerosProduccion.get(i).get(j))) {
                    tipoGramatica.seleccion.get(i).add(tipoGramatica.primerosProduccion.get(i).get(j));
                }
            }
            if (tipoGramatica.prodAnulables.contains(i)) {
                int posSiguiente = buscaPosN(gramatica.get(i).get(0));
                if (tipoGramatica.siguientesNT.get(posSiguiente).size() > 1) {
                    for (int j = 1; j < tipoGramatica.siguientesNT.get(posSiguiente).size(); j++) {
                        if (!tipoGramatica.seleccion.get(i).contains(tipoGramatica.siguientesNT.get(posSiguiente).get(j))) {
                            tipoGramatica.seleccion.get(i).add(tipoGramatica.siguientesNT.get(posSiguiente).get(j));
                        }
                    }
                }
            }
        }
    }

}
