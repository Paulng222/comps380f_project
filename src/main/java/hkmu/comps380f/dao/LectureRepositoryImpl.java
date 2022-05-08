package hkmu.comps380f.dao;

import static com.oracle.wls.shaded.org.apache.xalan.lib.ExsltDatetime.time;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.LectureComment;
import hkmu.comps380f.model.Notes;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Repository("LectureRepositoryImpl")
public class LectureRepositoryImpl implements LectureRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public LectureRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class LectureExtractor implements ResultSetExtractor<List<Lecture>> {

        @Override
        public List<Lecture> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Lecture> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Lecture lecture = map.get(id);
                if (lecture == null) {
                    lecture = new Lecture();
                    lecture.setId(id);
                    lecture.setUserName(rs.getString("name"));
                    lecture.setSubject(rs.getString("subject"));
                    lecture.setBody(rs.getString("body"));
                    map.put(id, lecture);
                }
                String filename = rs.getString("filename");
                if (filename != null) {
                    Notes notes = new Notes();
                    notes.setName(rs.getString("filename"));
                    notes.setMimeContentType(rs.getString("content_type"));
                    notes.setLectureId(id);
                    lecture.addNotes(notes);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public long createLecture(final String userName, final String subject,
            final String body, List<MultipartFile> notes)
            throws IOException {
        final String SQL_INSERT_Lecture
                = "insert into lecture (name, subject, body) values (?, ?, ?)";
        final String SQL_INSERT_Notes
                = "insert into notes (filename, content, content_type,"
                + " lecture_id) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_Lecture,
                        new String[]{"id"});
                ps.setString(1, userName);
                ps.setString(2, subject);
                ps.setString(3, body);
                return ps;
            }
        }, keyHolder);
        Long lecture_id = keyHolder.getKey().longValue();
        System.out.println("Lecture " + lecture_id + " inserted");
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
        return lecture_id;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> getLecture() {
        final String SQL_SELECT_Lecture = "select l.*, n.filename, n.content_type,"
                + " n.content from lecture as l left join notes as n"
                + " on l.id = n.lecture_id";
        return jdbcOp.query(SQL_SELECT_Lecture, new LectureExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> getLecture(long id) {
        final String SQL_SELECT_Lecture = "select l.*, n.filename, n.content_type,"
                + " n.content from lecture as l left join notes as n"
                + " on l.id = n.lecture_id where l.id = ?";
        return jdbcOp.query(SQL_SELECT_Lecture, new LectureExtractor(), id);
    }

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

    @Override
    @Transactional
    public void deleteLecture(long id) {
        final String SQL_DELETE_Lecture = "delete from lecture where id=?";
        final String SQL_DELETE_Notes = "delete from notes where lecture_id=?";
        jdbcOp.update(SQL_DELETE_Notes, id);
        jdbcOp.update(SQL_DELETE_Lecture, id);
        System.out.println("Lecture " + id + " deleted");
    }

    @Override
    @Transactional
    public void deleteNotes(long lectureId, String name) {
        final String SQL_DELETE_Notes
                = "delete from notes where lecture_id=? and filename=?";
        jdbcOp.update(SQL_DELETE_Notes, lectureId, name);
        System.out.println("Notes " + name + " of Lecture " + lectureId + " deleted");
    }

    private static final class NotesRowMapper implements RowMapper<Notes> {

        @Override
        public Notes mapRow(ResultSet rs, int i) throws SQLException {
            Notes entry = new Notes();
            entry.setName(rs.getString("filename"));
            entry.setMimeContentType(rs.getString("content_type"));
            Blob blob = rs.getBlob("content");
            byte[] bytes = blob.getBytes(1l, (int) blob.length());
            entry.setContents(bytes);
            entry.setLectureId(rs.getInt("lecture_id"));
            return entry;
        }
    }

    @Override
    @Transactional
    public Notes getNotes(long lectureId, String name) {
        final String SQL_SELECT_Notes = "select * from notes where lecture_id=? and filename=?";
        return jdbcOp.queryForObject(SQL_SELECT_Notes, new NotesRowMapper(), lectureId, name);
    }

    private static final class LectureCommentExtractor implements ResultSetExtractor<List<LectureComment>> {

        @Override
        public List<LectureComment> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, LectureComment> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                LectureComment lectureComment = map.get(id);
                if (lectureComment == null) {
                    lectureComment = new LectureComment();
                    lectureComment.setId(id);
                    lectureComment.setUsername(rs.getString("username"));
                    lectureComment.setComment(rs.getString("comment"));
                    lectureComment.setTime(rs.getString("creatTime"));
                    map.put(id, lectureComment);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public long createLectureComment(final long lectureCommentId, final String uName, final String comment) throws IOException {
        DateTimeFormatter dateTime= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        final String creatTime = dateTime.format(now);
        final String SQL_INSERT_LectureComment
                = "insert into lecture_comments (username, comment, creatTime,lectureComment_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LectureComment,
                        new String[]{"id"});
                ps.setString(1, uName);
                ps.setString(2, comment);
                ps.setString(3, creatTime);
                ps.setLong(4, lectureCommentId);
                return ps;
            }
        }, keyHolder);
        Long lectureComment_id = keyHolder.getKey().longValue();
        System.out.println("Lecture Comment " + lectureComment_id + " inserted");
        return lectureComment_id;
    }

    @Override
    @Transactional
    public void deleteLectureComment(long commentId) {
        final String SQL_DELETE_LectureComment = "delete from lecture_comments where id=?";
        jdbcOp.update(SQL_DELETE_LectureComment, commentId);
        System.out.println("Lecture comment" + commentId + " deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureComment> getLectureComment(long id) {
        final String SQL_SELECT_LectureComment = "select * from lecture_comments WHERE lectureComment_id = ?";
        return jdbcOp.query(SQL_SELECT_LectureComment, new LectureCommentExtractor(), id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureComment> getUserAllLectureComment(String username) {
        final String SQL_SELECT_ALL_LectureComment = "select * from lecture_comments WHERE username = ?";
        return jdbcOp.query(SQL_SELECT_ALL_LectureComment, new LectureCommentExtractor(), username);
    }

}
