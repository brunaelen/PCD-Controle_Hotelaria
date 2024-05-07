/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isabe
 */
public class Quarto {
    private int numero;
    private boolean ocupado;
    private int capacidade;
    private int hospedesPresentes;
    private Object chave;
    private boolean chaveRecepcao;
    private boolean limpezaEmAndamento;
    
    public Quarto(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.capacidade = 4;
        this.hospedesPresentes = 0;
        this.chave = new Object();
        this.chaveRecepcao = false;
        this.limpezaEmAndamento = false;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public synchronized void reservar() {
        if (!ocupado) {
            ocupado = true;
            System.out.println("Quarto "+numero+" reservado.");
        } else {
            System.out.println("Quarto "+numero+" ja esta ocupado.");
        }
    }
    
    public synchronized void desocupar() {
        if (ocupado) {
            ocupado = false;
            chaveRecepcao = true;
            System.out.println("Quarto "+numero+" desocupado.");
            hospedesPresentes = 0;
        } else {
            System.out.println("Quarto "+numero+" já está desocupado.");
        }
    }
    
    public synchronized boolean adicionarHospede() {
        if (hospedesPresentes < capacidade) {
            hospedesPresentes++;
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized void removerHospede() {
        hospedesPresentes--;
        if (hospedesPresentes == 0 && limpezaEmAndamento) {
            limpezaEmAndamento = false;
            notify();
        }
    }
    
    public Object getChave() {
        return chave;
    }
    
    public synchronized boolean isChaveRecepcao() {
        return chaveRecepcao;
    }
    
    public synchronized void pegarChave() {
        chaveRecepcao = false;
        System.out.println("Hospede pegou a chave do quarto "+numero+" na recepcao.");
    }
    
    public boolean isOcupado() {
        return ocupado;
    }
    
    public synchronized boolean isLimpezaEmAndamento() {
        return limpezaEmAndamento;
    }
    
    public synchronized void iniciarLimpeza() {
        limpezaEmAndamento = true;
    }
    
    public synchronized void terminarLimpeza() {
        limpezaEmAndamento = false;
    }
    
    public synchronized void aguardarLimpeza() throws InterruptedException {
        while (limpezaEmAndamento) {
            wait();
        }
    }
    
    public synchronized void deixarChaveNaRecepcao() {
        System.out.println("Hospede deixou a chave do quarto "+numero+" na recepcao.");
        chaveRecepcao = true;
        notifyAll();
    }
    
    private List<Hospede> hospedes = new ArrayList<>();
    
    public synchronized boolean adicionarHospede(Hospede hospede) {
        if (hospedes.size() < capacidade) {
            hospedes.add(hospede);
            
            if (hospedes.size() == capacidade) {
                StringBuilder mensagem = new StringBuilder("Família com o(s) hospedes: ");
                for (Hospede h : hospedes) {
                    mensagem.append(h.threadId()).append(", ");
                }
                
                mensagem.deleteCharAt(mensagem.length() - 1);
                mensagem.deleteCharAt(mensagem.length() - 1);
                mensagem.append(" esta no quarto").append(numero).append(".");
                System.out.println(mensagem);
            }
            return true;
        } else {
            return false;
        }
    }
    
}
