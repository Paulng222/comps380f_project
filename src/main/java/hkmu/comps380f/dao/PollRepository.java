package hkmu.comps380f.dao;


import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.PollComment;
import hkmu.comps380f.model.Vote;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public interface PollRepository {

    public long createPoll(String question, String option1, String option2, String option3, String option4) throws IOException;

    public List<Poll> getPolls();

    public List<Poll> getPollById(long id);

    public void deletePoll(long id);

    public Integer[] getTotalVotesOfPoll(long id);

    public List<Vote> getVoteHistories(long id, String username);

    public long createVote(String username, Integer voteOption, Date createdAt, long pollId) throws IOException;

    public long createPollComment(long pollId, String username, String content, Date createdAt) throws IOException;

    public void deletePollComment(long commentId);

    public List<PollComment> getPollComment(long id);

    public List<PollComment> getUserAllPollComment(String username);
}

