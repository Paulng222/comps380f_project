/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author emilylau
 */

public class Poll implements Serializable{
    
    private long id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private final Map<Long, PollComment> pollComments = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    //public Poll(){}

    public Poll(Long id, String question, String option1, String option2, String option3, String option4){
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }
/*
    public void deleteComment(PollComment pollComment) {
        pollComment.setPoll(null);
        this.comments.remove(pollComment);
    }
*/
}

    
   

