package hkmu.comps380f.dao;

import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.LectureComment;
import hkmu.comps380f.model.Notes;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface LectureRepository {

    public long createLecture(String userName, String subject, String body,
            List<MultipartFile> notes) throws IOException;

    public List<Lecture> getLecture();

    public List<Lecture> getLecture(long id);

    public void updateLecture(long lecture_id, String subject, String body,
            List<MultipartFile> notes) throws IOException;

    public void deleteLecture(long id);

    public void deleteNotes(long lectureId, String name);

    public Notes getNotes(long lectureId, String name);

    public long createLectureComment(long commentId, String username, String comment) throws IOException;

    public void deleteLectureComment(long commentId);

    public List<LectureComment> getLectureComment(long id);
}
