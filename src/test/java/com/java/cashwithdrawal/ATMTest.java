package com.java.cashwithdrawal;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

public class ATMTest extends TestCase {

	@Test
  void testParallelWithdrawals() throws InterruptedException {
      Map<Integer, Integer> availableNotes = new HashMap<>();
      availableNotes.put(100, 10); // 10 notes of 100's
      availableNotes.put(50, 10); // 10 notes of 50's
      availableNotes.put(20, 10); // 10 notes of 20's
      availableNotes.put(10, 10); // 10 notes of 10's

      ATM atm = new ATM(availableNotes);

      // Withdrawal tasks
      Runnable withdrawalTask1 = () -> {
          try {
        	  Map<Integer, Integer> withdrawnNotes1 = atm.withdraw(270);
              assertEquals(3, withdrawnNotes1.size());
              assertEquals(2, withdrawnNotes1.get(100).intValue());
              assertEquals(1, withdrawnNotes1.get(50).intValue());
              assertEquals(1, withdrawnNotes1.get(20).intValue());
          } catch (Exception e) {
              e.printStackTrace();
          }
      };

      Runnable withdrawalTask2 = () -> {
          try {
        	  Map<Integer, Integer> withdrawnNotes2 = atm.withdraw(150);
        	  assertEquals(2, withdrawnNotes2.size());
              assertEquals(1, withdrawnNotes2.get(100).intValue());
              assertEquals(1, withdrawnNotes2.get(50).intValue());
          } catch (Exception e) {
              e.printStackTrace();
          }
      };

      // Start withdrawal threads
      Thread thread1 = new Thread(withdrawalTask1);
      Thread thread2 = new Thread(withdrawalTask2);

      thread1.start();
      thread2.start();

      // Wait for threads to finish
      thread1.join();
      thread2.join();

     
  }

  @Test
  void testInsufficientDenominations() {
      Map<Integer, Integer> availableNotes = new HashMap<>();
      availableNotes.put(100, 1); // 1 note of 100's

      ATM atm = new ATM(availableNotes);

      assertThrows(Exception.class, () -> atm.withdraw(200));
  }

}
