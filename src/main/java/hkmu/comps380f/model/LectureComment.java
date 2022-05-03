package hkmu.comps380f.model;

import java.io.Serializable;

public class LectureComment implements Serializable {

    private long id;
    private String username;
    private String comment;
    private String time;
    private long lectureId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
