/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

/**
 *
 * @author Bruna
 */
public class Camareira extends Thread{
    private int id;
    private Quarto[] quartos;
    
    public Camareira(int id, Quarto [] quartos) {
        this.id = id;
        this.quartos = quartos;
    }
    
    public void run() {
        while(true) {
            for (Quarto quarto : quartos) {
                if (podeLimparQuarto(quarto)) {
                    limparQuarto(quarto);
                }
            }
        }
    }
    
    private boolean podeLimparQuarto(Quarto quarto) {
        synchronized (quarto) {
            if(!quarto.isOcupado() || quarto.isChaveRecepcao()) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    private void limparQuarto(Quarto quarto) {
        synchronized (quarto.getChave()) {
            System.out.println("Camareira "+id+" limpando o quarto "+ quarto.getNumero());
            
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            quarto.desocupar();
        }
    }
}
