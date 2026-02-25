package org.count;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Dataretriever retriever = new Dataretriever();

//        System.out.println("totalVotes : "+retriever.countAllVotes());
//        System.out.println(retriever.countVotesByType());
//        System.out.println(retriever.countValidVotesByCandidate());
        System.out.println(retriever.computeVoteSummary());
//        System.out.println(retriever.computeTurnoutRate());
//        System.out.println(retriever.findWinner());
        }
}
