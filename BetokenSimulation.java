public class BetokenSimulation {

  public static void main (String[] args) {
    GroupFund g = new GroupFund();

    Player player1 = Player.addPlayer(10, g);
    double[] arr1 = {1.0, 1.0};
    player1.updateProbability(arr1);

    Player player2 = Player.addPlayer(10, g);
    double[] arr2 = {0.0, 0.0};
    player2.updateProbability(arr2);

    Player player3 = Player.addPlayer(10, g);
    double[] arr3 = {1.0, 0.0};
    player3.updateProbability(arr3);

    Player player4 = Player.addPlayer(10, g);
    double[] arr4 = {0.0, 1.0};
    player4.updateProbability(arr4);


    Proposal p1 = Proposal.addProposal(2, 0.5, 0.5, g);

    Proposal p2 = Proposal.addProposal(1.2, 0.6, 0.75, g);

    runCycle(1000, g);

    ///////

    System.out.println("The total balance is: " + g.balance);

    for (int j = 0; j < g.playerList.size(); j++) {
      System.out.println("Player " + (j+1));
      System.out.println("Kairo balance: " + g.playerList.get(j).kairoBalance);
      System.out.println();
    }

    System.out.println();

    for (int j = 0; j < g.proposalList.size(); j++) {
      System.out.println("Proposal " + j + " " + g.proposalList.get(j).statusVar);
    }

    System.out.println("**************************************");

    // Run a price cycle
  }

  // The auxiliary runCycle function
  public static void runCycle(int times, GroupFund g) {

    // Iterate through and see how the Kairo changes
    for (int i = 0; i < times; i++) {

      /*
      System.out.println("The total balance is: " + g.balance);

      for (int j = 0; j < g.playerList.size(); j++) {
        System.out.println("Player " + (j+1));
        System.out.println("Kairo balance: " + g.playerList.get(j).kairoBalance);
        System.out.println();
      }

      System.out.println();

      for (int j = 0; j < g.proposalList.size(); j++) {
        System.out.println("Proposal " + j + " " + g.proposalList.get(j).statusVar);
      }

      System.out.println("**************************************");

      // Run a price cycle
      */
      g.runCycle();
    }
  }

}
