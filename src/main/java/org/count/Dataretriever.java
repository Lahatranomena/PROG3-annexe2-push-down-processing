package org.count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            throw new RuntimeException("Error");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
