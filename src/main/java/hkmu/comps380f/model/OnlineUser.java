package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnlineUser implements Serializable {

    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
    private String fullname;
    private String phone;
    private String address;

    public OnlineUser() {
    }

    public OnlineUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = "{noop}" + password;
        this.roles = new ArrayList<>(Arrays.asList(roles));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
