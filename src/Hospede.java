/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

/**
 *
 * @author Bruna
 */
public class Hospede extends Thread{
    private int id;
    private Quarto quarto;
    private int tentativas;
    
    public Hospede(int id) {
        this.id = id;
        this.tentativas = 0;
    }
    
    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
    
    public int getTentativas() {
        return tentativas;
    }
    
    @Override
    public void run() {
        while(true) {
            passearPelaCidade();
            
            if (quarto!=null) {
                quarto.deixarChaveNaRecepcao();
                break;
            }
        }
    }
    
    private void passearPelaCidade() {
        System.out.println("Hospede "+id+ " esta passeando pela cidade.");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
