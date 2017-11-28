import java.util.*;

public class GroupFund {

  public double balance;

  public double kairoTotal;

  public ArrayList<Proposal> proposalList = new ArrayList<>();
  public ArrayList<Player> playerList = new ArrayList<>();

  public GroupFund() {
  }

  // Appends proposal to the GroupFund list
  public void addProposal(Proposal p) {
    this.proposalList.add(p);
  }

  // Runs the updating of prices / Kair
  public void runCycle() {

    // Go through each Proposal
    for (Proposal p : this.proposalList) {

      // Have each player Support/Against the Proposal
      // (Kairo is staked here and goes into the Proposal.)
      for (Player player : this.playerList) {
        player.reactProposal(p);
      }

      // Update the prices
      p.update();
    }

    // Go through each Proposal and update the GroupFund balance
    for (Proposal p : this.proposalList) {
      double newBalance = p.price * p.balance;
      this.balance += newBalance;
      p.balance = 0;
    }

    // Redistribute Kairo:
    // Iterate through each Proposal
    for (int i = 0; i < this.proposalList.size(); i++) {
      int supportCount = 0;

      for (Player player : this.playerList) {
        if (player.proposalSupport.get(i)) {
          supportCount++;
        }
      }

      double kairoToDistribute = (proposalList.get(i).kairoAgainst +
      proposalList.get(i).kairoSupport);

      proposalList.get(i).kairoSupport = 0;
      proposalList.get(i).kairoAgainst = 0;

      // If Proposal was a WINNER
      if (proposalList.get(i).statusVar.toString().equals("WINNER")) {

        // Determine amount to distribute (note that we're divvying up the KairoAGAINST)
        kairoToDistribute = (kairoToDistribute / supportCount);

        // Iterate through players
        for (Player player : this.playerList) {
          if (player.proposalSupport.get(i)) {
            player.kairoBalance += kairoToDistribute;
          }
        }
      }

      // If Proposal was a LOSER
      else {

        // Determine amount to distribute (note that we're divvying up the KairoSUPPORT)
        kairoToDistribute = (kairoToDistribute / (playerList.size() - supportCount));

        // Iterate through players
        for (Player player : this.playerList) {
          if (! player.proposalSupport.get(i)) {
            player.kairoBalance += kairoToDistribute;
          }
        }
      }
    }
  }
}
