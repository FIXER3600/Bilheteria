package controller;

import java.util.concurrent.Semaphore;

public class Compra extends Thread{
    private int maxIngresso = 4;
    private int minIngresso = 1;
    private static int ingressos = 100;
    private int idThread;
    private Semaphore semaforo;


    public Compra(int idThread, Semaphore semaforo) {
        this.idThread = idThread;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        Login();
        try {
            semaforo.acquire();
            validacao();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaforo.release();
        }

    }

    private void Login() {
        double tempoLog = ((Math.random()*1951)+50);
        if(tempoLog>1){
            System.err.println("A thread #" + this.idThread + " levou timeout \n");
        }else{
            System.out.println("A thread #"+this.idThread+" efetuou o login \n");
            compraBilhete();
        }

    }

    private void compraBilhete() {
        int timeBuy = (int) ((Math.random() * 1001) + 2001);
        if(timeBuy>=2500){
            System.err.println("A pessoa " + this.idThread + " ultrapassou o tempo de sessão \n");
        }else{
            try {
            sleep(timeBuy);
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }
    private void validacao() {
        try {
            semaforo.acquire();
            int buy = (int) ((Math.random() * maxIngresso) + minIngresso);
            if (ingressos > 0 && buy <= ingressos) {

                ingressos -= buy;
                System.out.println("A pessoa " + this.idThread + " comprou " +ingressos+ "\n");
                System.out.println("Há " + ingressos + " ingressos restantes \n");
            } else {
                System.err.println("Ingressos esgotados para a Thread #" + this.idThread + " \n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            semaforo.release();
        }
    }
}
