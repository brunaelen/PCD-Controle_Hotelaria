/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

/**
 *
 * @author Bruna
 */
public class Main {
        public static void main(String[] args) {
        //quartos
        Quarto[] quartos = new Quarto[10];
        for (int i = 0; i < quartos.length; i++) {
            quartos[i] = new Quarto(i + 1);
        }

        //recepcionistas
        Recepcionista[] recepcionistas = new Recepcionista[5];
        for (int i = 0; i < recepcionistas.length; i++) {
            recepcionistas[i] = new Recepcionista(i + 1, quartos);
            recepcionistas[i].start();
        }

        //camareiras
        Camareira[] camareiras = new Camareira[10];
        for (int i = 0; i < camareiras.length; i++) {
            camareiras[i] = new Camareira(i + 1, quartos);
            camareiras[i].start();
        }

        //hóspedes
        for (int i = 0; i < 50; i++) {
            Hospede hospede = new Hospede(i + 1);
            hospede.setQuarto(null); 
            Recepcionista recepcionista = recepcionistas[i % recepcionistas.length];
            recepcionista.adicionarPessoaFilaEspera(hospede);
        }
        
        try {
            for (Recepcionista recepcionista : recepcionistas) {
                recepcionista.join();
            }
            
            for (Camareira camareira : camareiras) {
                camareira.join();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
