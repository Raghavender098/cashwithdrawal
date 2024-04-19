package com.java.cashwithdrawal;

import java.util.*;

class ATM {
	 		// Denomination -> Count
    private Map<Integer, Integer> availableNotes;

    public ATM(Map<Integer, Integer> availableNotes) {
        this.availableNotes = availableNotes;
    }

    public Map<Integer, Integer> withdraw(int amount) throws Exception {
     
    	synchronized (this) {
            Map<Integer, Integer> withdrawnNotes = new HashMap<>();
            int remainingAmount = amount;

            // Sort denominations in descending order
            List<Integer> denominations = new ArrayList<>(availableNotes.keySet());
            denominations.sort(Collections.reverseOrder());

            for (int denomination : denominations) {
                int notesRequired = remainingAmount / denomination;
                if (notesRequired > 0 && availableNotes.get(denomination) >= notesRequired) {
                    withdrawnNotes.put(denomination, notesRequired);
                    remainingAmount -= notesRequired * denomination;
                }
            }

            if (remainingAmount != 0) {
                throw new Exception("Cannot dispense amount with available denominations");
            }

            // Update availableNotes
            for (int denomination : withdrawnNotes.keySet()) {
                availableNotes.put(denomination, availableNotes.get(denomination) - withdrawnNotes.get(denomination));
            }

            return withdrawnNotes;
        }
    }
}