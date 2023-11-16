package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) {
        Random rand = new Random();
        Vault vault = new Vault(rand.nextInt(MAX_PASSWORD));
        List<Thread> threads = new ArrayList<Thread>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for(Thread t : threads) {
            t.start();
        }

    }

    static class Vault {
        private int password;
        public Vault(int password) {
            this.password = password;;
        }
        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e) {
            }
            return this.password == guess;
        }
    }

    static abstract class HackerThread extends Thread {
        protected Vault vault;
        public HackerThread(Vault vault) {
            this.vault = vault;
        }
        public void start() {
            System.out.println("Starting Thread " + this.getName());
            super.start();
        }
    }

    static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }
        @Override
        public void run() {
            for(int guess = 0; guess < MAX_PASSWORD; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + "Guessed password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD; guess > 0; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + "Guessed password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    static class PoliceThread extends Thread {
        @Override
        public void run() {
            for(int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {

                }
                System.out.println(i);
            }
            System.out.println("Game Over Hackers!");
        }
    }

}