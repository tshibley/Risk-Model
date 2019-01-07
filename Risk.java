import java.util.*;

class Risk {
  public static void main(String[] args) {
    ArrayList<Integer> troopNumbersMaster = new ArrayList<Integer>();
    System.out.println("Simulating a Risk turn with the following setup:");
    System.out.println();
    System.out.println("The first value is the attacking country, the rest of the");
    System.out.println("values are the chain of defending countries");

    /**
     * Edit the numbers below here for attacking a defending troop setups and the
     * number of trials to run
     */

    // The attacking Troop
    troopNumbersMaster.add(45);

    // The defending Troops

    troopNumbersMaster.add(1);
    troopNumbersMaster.add(1);
    troopNumbersMaster.add(7);
    troopNumbersMaster.add(1);
    troopNumbersMaster.add(7);
    troopNumbersMaster.add(7);
    troopNumbersMaster.add(7);
    troopNumbersMaster.add(1);
    troopNumbersMaster.add(1);
    troopNumbersMaster.add(1);
    troopNumbersMaster.add(1);

    // The number of trials
    int totalReps = 100000;

    /**
     * Do not edit below here
     */

    System.out.println(troopNumbersMaster);
    System.out.println();

    Integer[] copyDeep = new Integer[troopNumbersMaster.size()];
    copyDeep = troopNumbersMaster.toArray(copyDeep);

    int winCount = 0;

    for (int i = 0; i < totalReps; i++) {

      ArrayList<Integer> troopNumbers = new ArrayList<Integer>(Arrays.asList(copyDeep));

      // the starting index for the attackers
      int attackingIndex = 0;

      // Have the attacking index attack the next square until depleted
      while (attackingIndex < troopNumbers.size() - 1 && troopNumbers.get(attackingIndex) > 1) {
        // System.out.println("Attacking Index " + attackingIndex);
        // System.out.println(troopNumbers);
        int attacking = troopNumbers.get(attackingIndex);
        int defending = troopNumbers.get(attackingIndex + 1);
        int[] res = simulateBattle(new int[] { attacking, defending });
        if (res[0] > 1) {
          // the attacker won, needs to leave one behind
          // System.out.println("attacker Won");
          troopNumbers.set(attackingIndex, 1);
          troopNumbers.set(attackingIndex + 1, res[0] - 1);
          attackingIndex++;
        } else {
          // the defender won
          // System.out.println("defender Won");
          troopNumbers.set(attackingIndex, 1);
          troopNumbers.set(attackingIndex + 1, res[1]);
        }
      }

      // simulation is finisished, attacker won if they made it to the last index
      if (attackingIndex == troopNumbers.size() - 1) {
        winCount++;
      }
    }

    double percentWon = (double) winCount / (double) totalReps;
    System.out.println("Result: ");
    System.out.printf("Win percent: %.2f %n", percentWon * 100);
  }

  public static int[] simulateBattle(int[] input) {
    int attacking = input[0];
    int defending = input[1];
    // System.out.println("Battling " + attacking + " vs " + defending);
    while (attacking > 1 && defending > 0) {
      // System.out.println("Attacking: " + attacking);
      // System.out.println("Defending: " + defending);
      int[] attackingRolls = new int[Math.min(attacking - 1, 3)];
      int[] defendingRolls = new int[Math.min(defending, 2)];
      for (int i = 0; i < attackingRolls.length; i++) {
        attackingRolls[i] = (int) (Math.random() * 6) + 1;
      }
      for (int i = 0; i < defendingRolls.length; i++) {
        defendingRolls[i] = (int) (Math.random() * 6) + 1;
      }

      Arrays.sort(attackingRolls);
      Arrays.sort(defendingRolls);
      attackingRolls = reverseArray(attackingRolls);
      defendingRolls = reverseArray(defendingRolls);
      // System.out.println("attacking Rolls:");
      // System.out.println(Arrays.toString(attackingRolls));
      // System.out.println("defending Rolls:");
      // System.out.println(Arrays.toString(defendingRolls));

      for (int i = 0; i < Math.min(attackingRolls.length, defendingRolls.length); i++) {
        if (attackingRolls[i] > defendingRolls[i]) {
          defending--;
          // System.out.println("minus one defending");
        } else {
          attacking--;
          // System.out.println("minus one attacking");
        }
      }
    }
    int[] result = new int[] { attacking, defending };
    return result;
  }

  public static int[] reverseArray(int[] validData) {
    for (int i = 0; i < validData.length / 2; i++) {
      int temp = validData[i];
      validData[i] = validData[validData.length - i - 1];
      validData[validData.length - i - 1] = temp;
    }
    return validData;
  }
}