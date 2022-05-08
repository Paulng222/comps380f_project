package hkmu.comps380f.dao;


import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.PollComment;
import hkmu.comps380f.model.Vote;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("PollRepositoryImpl")
public class PollRepositoryImpl implements PollRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public PollRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class PollExtractor implements ResultSetExtractor<List<Poll>> {

        @Override
        public List<Poll> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Poll> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Poll poll = map.get(id);
                if (poll == null) {
                    poll = new Poll(id, rs.getString("question"), rs.getString("option1"), rs.getString("option2"), rs.getString("option3"), rs.getString("option4"));
           
                    map.put(id, poll);
                }
                
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public long createPoll(final String question, final String option1, final String option2, final String option3, final String option4)
            throws IOException {
        final String sql_insertPoll = "INSERT INTO polls (question, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql_insertPoll, new String[]{"id"});
                ps.setString(1, question);
                ps.setString(2, option1);
                ps.setString(3, option2);
                ps.setString(4, option3);
                ps.setString(5, option4);
                return ps;
            }
        }, keyHolder);
        Long poll_id = keyHolder.getKey().longValue();
        System.out.println("poll " + poll_id + " inserted");
        
        return poll_id;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> getPolls() {
        final String sql_readLectures = "SELECT * FROM polls";
        return jdbcOp.query(sql_readLectures, new PollExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> getPollById(long id) {
        final String sql_readLectureById = "SELECT * FROM polls WHERE id = ?";
        return jdbcOp.query(sql_readLectureById, new PollExtractor(), id);
    }
/*
  @Override
    @Transactional
    public void updateLecture(long lecture_id, String subject, String body,
            List<MultipartFile> notes) throws IOException {
        final String SQL_UPDATE_Lecture = "update lecture set subject=?, body=? where id=?";
        final String SQL_INSERT_Notes
                = "insert into notes (filename,content,content_type,lecture_id) values (?,?,?,?)";
        jdbcOp.update(SQL_UPDATE_Lecture, subject, body, lecture_id);
        System.out.println("Lecture " + lecture_id + " updated");
        for (MultipartFile filePart : notes) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler();
                jdbcOp.update(SQL_INSERT_Notes,
                        new Object[]{filePart.getOriginalFilename(),
                            new SqlLobValue(filePart.getInputStream(),
                                    (int) filePart.getSize(), handler),
                            filePart.getContentType(),
                            lecture_id},
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.INTEGER});
                System.out.println("Notes " + filePart.getOriginalFilename()
                        + " of Lecture " + lecture_id + " inserted");
            }
        }
    }
*/
    @Override
    @Transactional
    public void deletePoll(long id) {
        
        final String sql_deletePollComment = "DELETE FROM poll_comments WHERE poll_id = ?";
        final String sql_deletePollVote = "DELETE FROM votes WHERE poll_id = ?";
        final String sql_deletePoll = "DELETE FROM polls WHERE id = ?";
        jdbcOp.update(sql_deletePollComment, id);
        jdbcOp.update(sql_deletePollVote, id);
        jdbcOp.update(sql_deletePoll, id);
        System.out.println("Poll " + id + " deleted");
    }
    //Votes
    private static final class VoteExtractor implements ResultSetExtractor<Integer[]> {

        @Override
        public Integer[] extractData(ResultSet rs) throws SQLException, DataAccessException {
            Integer[] total= {0,0,0,0};
            while (rs.next()) {
                Integer currentOption = rs.getInt("voteOption");
                if(currentOption == 1){
                    total[0] = rs.getInt("totalCount");
                }
                if(currentOption == 2){
                    total[1] = rs.getInt("totalCount");
                }
                if(currentOption == 3){
                    total[2] = rs.getInt("totalCount");
                }
                if(currentOption == 4){
                    total[3] = rs.getInt("totalCount");
                }
            }
            return total;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer[] getTotalVotesOfPoll(long id) {
        final String sql_getVotes = "SELECT COUNT(v.voteoption) AS totalCount,v.voteoption FROM votes v INNER JOIN (SELECT username, max(created_at) AS MaxDate FROM votes GROUP BY username) tm ON v.username = tm.username AND v.created_at = tm.MaxDate WHERE poll_id = ? GROUP BY v.voteoption";
        System.out.println("Poll " + id + " votes found");
        return jdbcOp.query(sql_getVotes, new VoteExtractor(), id);
    }

    @Override
    @Transactional
    public long createVote(final String username, final Integer voteOption, final Date createdAt, final long pollId)
            throws IOException {
        final String sql_insertVote = "INSERT INTO votes (username, voteOption, created_at, poll_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql_insertVote, new String[]{"id"});
                ps.setString(1, username);
                ps.setInt(2, voteOption);
                ps.setTimestamp(3, new Timestamp(createdAt.getTime()));
                ps.setLong(4, pollId);
                return ps;
            }
        }, keyHolder);
        Long voteId = keyHolder.getKey().longValue();
        System.out.println(voteId + ": " + username + " vote " + voteOption +" for " + pollId + " record inserted");
        
        return voteId;
    }

//Vote histories
    private static final class VoteHistoriesExtractor implements ResultSetExtractor<List<Vote>> {

        @Override
        public List<Vote> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Vote> voteHistories = new ArrayList<>();
            while (rs.next()) {
                Vote vote = new Vote(rs.getString("username"), rs.getInt("voteOption"));
                vote.setId(rs.getLong("id"));
                vote.setPollId(rs.getLong("poll_id"));
                vote.setCreatedAt(new Timestamp(rs.getTimestamp("created_at").getTime()));
                voteHistories.add(vote);
            }
            return voteHistories;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vote> getVoteHistories(long id, String username) {
        final String sql_getVoteHistories = "SELECT * FROM votes WHERE username = ? AND poll_id = ? ORDER BY created_at DESC";
        System.out.println(username + "'s poll " + id + " vote histories found");
        return jdbcOp.query(sql_getVoteHistories, new VoteHistoriesExtractor(), username, id);
    }

    //Comment

    private static final class PollCommentExtractor implements ResultSetExtractor<List<PollComment>> {

        @Override
        public List<PollComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, PollComment> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                PollComment pollComment = map.get(id);
                if (pollComment == null) {
                    pollComment = new PollComment(rs.getString("username"), rs.getString("content"));
                    pollComment.setCreatedAt(new Timestamp(rs.getTimestamp("created_at").getTime()));
                    pollComment.setId(id);
                    map.put(id, pollComment);
                }
                
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollComment> getPollComment(long id) {
        final String sql_getPollComment = "SELECT * FROM poll_comments WHERE poll_id = ?";
        //System.out.println(username + "'s poll " + id + " vote histories found");
        return jdbcOp.query(sql_getPollComment, new PollCommentExtractor(), id);
    }

    @Override
    @Transactional
    public long createPollComment(final long pollId, final String username, final String content)
            throws IOException {
        final String sql_insertPollComment = "INSERT INTO poll_comments (username, content, poll_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql_insertPollComment, new String[]{"id"});
                ps.setString(1, username);
                ps.setString(2, content);
                ps.setLong(3, pollId);
                return ps;
            }
        }, keyHolder);
        Long commentId = keyHolder.getKey().longValue();
        System.out.println(pollId + " Poll comment " + commentId + " inserted");
        
        return commentId;
    }

    @Override
    @Transactional
    public void deletePollComment(long id) {
        
        final String sql_deletePollComment = "DELETE FROM poll_comments WHERE id = ?";
        jdbcOp.update(sql_deletePollComment, id);
        System.out.println("Poll Comment #" + id + " deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollComment> getUserAllPollComment(String username) {
        final String SQL_SELECT_ALL_PollComment = "select * from poll_comments WHERE username = ?";
        return jdbcOp.query(SQL_SELECT_ALL_PollComment, new PollCommentExtractor(), username);
    }
}
