/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author emilylau
 */

public class Vote implements Serializable{

    private long id;
    private String username;
    private Integer voteOption;
    private Date createdAt;
    private long pollId;

       // public Vote(){}
    public Vote(String username, Integer voteOption){
        this.username = username;
        this.voteOption = voteOption;
    } 
/*
    public Vote(Long id, String username, Integer voteOption, Date createdAt, long pollId){
        this.id = id;
        this.username = username;
        this.voteOption = voteOption;
        this.createdAt = createdAt;
        this.pollId = pollId;
    } 
*/
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

    public Integer getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(Integer voteOption) {
        this.voteOption = voteOption;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getPollId() {
        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

       
    }


