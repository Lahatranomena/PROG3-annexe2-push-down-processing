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
}
