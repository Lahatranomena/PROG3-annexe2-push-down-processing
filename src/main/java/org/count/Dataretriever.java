package org.count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Dataretriever {

    DBConnection connection = new DBConnection();

    long countAllVotes() {
        try (Connection conn = connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                select count(voter.id) as total_votes from voter;
                """);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("total_votes");
            }
            throw new RuntimeException("Error to count all votes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<VoteTypeCount> countVotesByType() {
        List<VoteTypeCount> voteTypeCounts = new ArrayList<>();

        try (Connection conn = connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                select vote.vote_type, count(vote.id) as total_votes from vote group by vote.vote_type;
                """);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VoteTypeCount voteTypeCount = new VoteTypeCount();

                voteTypeCount.setVoteType(VoteTypeEnum.valueOf(rs.getString("vote_type")));
                voteTypeCount.setCount(rs.getInt("total_votes"));
                voteTypeCounts.add(voteTypeCount);
            }
            return voteTypeCounts;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<CandidateVoteCount> countValidVotesByCandidate() {
        List<CandidateVoteCount> candidateVoteCounts = new ArrayList<>();

        try (Connection conn = connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                select candidate.name, count(vote.id) as valid_vote from candidate
                    left join vote on candidate.id = vote.candidate_id
                    and vote.vote_type = 'VALID'
                group by candidate.name;
                """);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CandidateVoteCount candidateVoteCount = new CandidateVoteCount();
                candidateVoteCount.setCandidateName(rs.getString("name"));
                candidateVoteCount.setValidVoteCount(rs.getString("valid_vote"));

                candidateVoteCounts.add(candidateVoteCount);
            }
            return candidateVoteCounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    VoteSummary computeVoteSummary() {

        try (Connection conn = connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                select
                    sum(case when vote_type = 'VALID' then 1 else 0 end) as valid,
                    sum(case when vote_type = 'BLANK' then 1 else 0 end) as blank,
                    sum(case when vote_type = 'NULL' then 1 else 0 end) as null
                from vote;""");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VoteSummary voteSummary = new VoteSummary();
                voteSummary.setValidCount(rs.getInt("valid"));
                voteSummary.setBlankCount(rs.getInt("blank"));
                voteSummary.setNullCount(rs.getInt("null"));

                return voteSummary;
            }
            throw new RuntimeException("Error to compute vote summary");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    double computeTurnoutRate() {

        try ( Connection conn = connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                    select 
                        (count(distinct vote.voter_id) * 100.0 / count(distinct voter.id)) as taux_participation
                    from voter
                    left join vote
                    on voter.id = vote.voter_id;""");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("taux_participation");
            }
            throw new RuntimeException("Error to compute turnout rate");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
