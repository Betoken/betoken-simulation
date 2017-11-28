import java.util.*;

public class Proposal {

  // Amount of Kairo staked for/against a Proposal
  public double kairoSupport;
  public double kairoAgainst;

  // Current amount of balance put in the Proposal
  public double balance;

  // The current price the Proposal is at, set to 1 for now to normalize rates
  public double price = 1.0;

  // The percentages that the Proposals grow/fall
  public double growthRate;
  public double fallRate;

  // Probability that either grow/fall occurs
  // A double from 0 to 1
  public double growthThreshold;

  // Proposal ID
  public int index;

  // Pointer to the GroupFund
  public GroupFund g;

  // Need an enum for if it's a Winner, Loser, or Neutral we're in
  public enum Status {WINNER, LOSER, NEUTRAL}

  public Status statusVar;

  // Constructor
  public Proposal() {
  }

  // Appends proposal to the GroupFund list
  public void addProposal() {
    g.proposalList.add(this);
  }

  // Constructor + adder function
  public static Proposal addProposal(double growthRate, double fallRate,
  double growthThreshold, GroupFund g) {

    Proposal proposalVar = new Proposal();

    proposalVar.statusVar = Status.NEUTRAL;
    proposalVar.growthRate = growthRate;
    proposalVar.fallRate = fallRate;
    proposalVar.growthThreshold = growthThreshold;

    proposalVar.g = g;
    proposalVar.index = g.proposalList.size();

    // Adds it to the Proposal
    proposalVar.addProposal();

    return proposalVar;
  }

  // Updates the Proposal's prices
  public void update() {
    double prob = Math.random();
    if (prob < growthThreshold) {
      price = (price * growthRate);
      this.statusVar = Status.WINNER;
    }
    else {
      price = (price * fallRate);
      this.statusVar = Status.LOSER;
    }
  }
}
