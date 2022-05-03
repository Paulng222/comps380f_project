package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Lecture implements Serializable {

    private long id;
    private String userName;
    private String subject;
    private String body;
    private final Map<String, Notes> notes = new HashMap<>();

    public void addNotes(Notes notes) {
        this.notes.put(notes.getName(), notes);
    }

    public Collection<Notes> getNotes() {
        return this.notes.values();
    }

    public int getNumberOfNotes() {
        return this.notes.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
