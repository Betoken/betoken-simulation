import java.util.*;

/*
1) Try to randomize kairoSupportRatio or make it easier to tweak
2) Figure out an easier way to create new Players
3) Currently no way to distribute Kairo unevenly among players (would need
to keep track of add'l variables)
*/

public class Player {

  // Amount of Kairo the Player has
  public double kairoBalance;

  // The threshold probabilities assigned to each proposal
  // (If Math.random() < proposalProbability, it will be Support, else Against.)
  public ArrayList<Double> proposalProbability = new ArrayList<>();

  // Pseudo-mapping of Proposal Indices to Support or Against (true or false, respectively)
  public ArrayList<Boolean> proposalSupport = new ArrayList<>();

  // Unused for now, default assumption is everyone's proportion is the same
  public double proportion;

  // Amount of Kairo you need to stake
  // Move this to the GroupFund
  public double kairoSupportRatio = 0.25;
  public double kairoAgainstRatio = 0.25;

  // Reference to GroupFund
  public GroupFund g;

  // Constructor
  public Player() {
  }

  // Adds Player to the GroupFund's list
  public void addPlayer() {
    this.g.playerList.add(this);
  }

  // Constructor + adder function
  public static Player addPlayer (double deposit, GroupFund g) {

    // Call constructor
    Player playerVar = new Player();

    // Initialize vars
    playerVar.kairoBalance = deposit;
    playerVar.g = g;
    g.balance += deposit;
    g.kairoTotal += deposit;

    // Add it to the group fund
    playerVar.addPlayer();

    return playerVar;
  }

  // Creates probability of investing
  public void updateProbability(double[] values) {
    for (int i = 0; i < values.length; i++) {
      proposalProbability.add(values[i]);
    }
  }

  // React to Proposal
  public void reactProposal(Proposal p) {
    double prob = Math.random();

    if (prob < proposalProbability.get(p.index)) {
      supportProposal(p);
    }
    else {
      againstProposal(p);
    }

  }

  // Support Proposal
  public void supportProposal(Proposal p) {

    double kairoTransferAmt = (this.kairoBalance * kairoSupportRatio);
    this.kairoBalance -= kairoTransferAmt;
    p.kairoSupport += kairoTransferAmt;
    proposalSupport.add(true);

    // Update the GroupFund's funds to "lock up" the proportional amount pledged to the
    // Proposal
    double balanceTransferAmt = g.balance * (kairoTransferAmt / g.kairoTotal);

    g.balance -= balanceTransferAmt;
    p.balance += balanceTransferAmt;
  }

  // Against Proposal
  public void againstProposal(Proposal p) {
    double kairoTransferAmt = (this.kairoBalance * kairoAgainstRatio);
    this.kairoBalance -= kairoTransferAmt;
    p.kairoAgainst += kairoTransferAmt;
    proposalSupport.add(false);

  }

  // Update function to give someone more control of the balance, relative to their Kairo

}
