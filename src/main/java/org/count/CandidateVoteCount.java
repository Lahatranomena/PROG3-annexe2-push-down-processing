package org.count;

import java.util.Objects;

public class CandidateVoteCount {
    private String candidateName;
    private String validVoteCount;

    public CandidateVoteCount(String candidateName, String validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public CandidateVoteCount() {}

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(String validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CandidateVoteCount that)) return false;
        return Objects.equals(candidateName, that.candidateName) && validVoteCount == that.validVoteCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateName, validVoteCount);
    }

    @Override
    public String toString() {
        return "CandidateVoteCount{" +
                "candidateName='" + candidateName + '\'' +
                ", validVoteCount=" + validVoteCount +
                '}';
    }
}
