package org.count;

import java.util.Objects;

public class VoteTypeCount {
    private VoteTypeEnum voteType;
    private int count;

    public VoteTypeCount(VoteTypeEnum voteType) {
        this.voteType = voteType;
        this.count = 0;
    }

    public VoteTypeCount() {}
    public VoteTypeEnum getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteTypeEnum voteType) {
        this.voteType = voteType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VoteTypeCount that)) return false;
        return count == that.count && voteType == that.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteType, count);
    }

    @Override
    public String toString() {
        return "VoteTypeCount : {" +
                "voteType=" + voteType +
                ", count=" + count +
                '}';
    }
}
