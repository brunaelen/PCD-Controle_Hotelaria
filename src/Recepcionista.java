/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Isabe
 */
public class Recepcionista extends Thread{
     private int id;
    private Quarto[] quartos;
    private Queue<Hospede> filaEspera;
    private final int TEMPO_MAXIMO_ESPERA = 60000;

    public Recepcionista(int id, Quarto[] quartos) {
        this.id = id;
        this.quartos = quartos;
        this.filaEspera = new LinkedList<>();
    }

    public void run() {
        while (true) {
            Hospede hospede = null;
            synchronized (filaEspera) {
                if (!filaEspera.isEmpty()) {
                    hospede = filaEspera.poll();
                }
            }
            if (hospede != null) {
                Quarto quartoDisponivel = encontrarQuartoDisponivel();
                if (quartoDisponivel != null) {
                    if (quartoDisponivel.adicionarHospede()) {
                        hospede.setQuarto(quartoDisponivel);
                        hospede.start();

                        System.out.println("Recepcionista " + id + ": Hóspede " + hospede.threadId() +
                                           " alocado no quarto " + quartoDisponivel.getNumero());
                    } else {
                        System.out.println("Recepcionista " + id + ": Não há capacidade suficiente no quarto " +
                                           quartoDisponivel.getNumero() + " para acomodar mais hóspedes.");
                    }
                } else {
                    System.out.println("Recepcionista " + id + ": Não há quartos disponíveis no momento.");
                    if (hospede.getTentativas() < 2) {
                        synchronized (filaEspera) {
                            filaEspera.offer(hospede);
                        }
                    } else {
                        System.out.println("Recepcionista " + id + ": Hóspede " + hospede.threadId() +
                                           " deixou uma reclamação e foi embora.");
                    }
                }
            } else {
                System.out.println("Recepcionista " + id + ": Não há pessoas na fila de espera.");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Quarto encontrarQuartoDisponivel() {
        for (Quarto quarto : quartos) {
            synchronized (quarto) {
                if (!quarto.isOcupado() && !quarto.isChaveRecepcao() && !quarto.isLimpezaEmAndamento()) {
                    return quarto;
                }
            }
        }
        return null;
    }

    public synchronized void adicionarPessoaFilaEspera(Hospede hospede) {
        filaEspera.offer(hospede);
    }
}
