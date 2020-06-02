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

    /**
     * Encuentra los símbolos de la pila, evaluando cada una de las producciones
     * y buscando los terminales que se encuentren un un α o β y agregando
     * "vacia" que es lo que remplazara al simbolo ▼
     */

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

}
