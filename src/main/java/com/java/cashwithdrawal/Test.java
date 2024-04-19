package com.java.cashwithdrawal;

import java.util.HashMap;
import java.util.Map;

//I assumes that the ATM has a fixed set of available denominations 
//and it maintains the minimum number of bank notes while dispensing 
//the amount. 
//It also supports parallel cash withdrawals.
public class Test {
 public static void main(String[] args) {
     Map<Integer, Integer> availableNotes = new HashMap<>();
     availableNotes.put(100, 10); // 10 notes of 100's
     availableNotes.put(50, 10); // 10 notes of 50's
     availableNotes.put(20, 10); // 10 notes of 20's
     availableNotes.put(10, 10); // 10 notes of 10's

     ATM atm = new ATM(availableNotes);

     // Simulate parallel withdrawals
     Runnable withdrawalTask1 = () -> {
         try {
             Map<Integer, Integer> withdrawnNotes = atm.withdraw(270);
             System.out.println("Withdrawn Notes 1: " + withdrawnNotes);
             System.out.println("Remaining Notes in ATM 1: " + availableNotes);
         } catch (Exception e) {
             System.out.println("Failed to withdraw amount: " + e.getMessage());
         }
     };

     Runnable withdrawalTask2 = () -> {
         try {
             Map<Integer, Integer> withdrawnNotes = atm.withdraw(150);
             System.out.println("Withdrawn Notes 2: " + withdrawnNotes);
             System.out.println("Remaining Notes in ATM 2: " + availableNotes);
         } catch (Exception e) {
             System.out.println("Failed to withdraw amount: " + e.getMessage());
         }
     };

     Thread thread1 = new Thread(withdrawalTask1);
     Thread thread2 = new Thread(withdrawalTask2);

     thread1.start();
     thread2.start();
 }
}
