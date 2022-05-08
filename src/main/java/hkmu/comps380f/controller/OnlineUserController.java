package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.dao.OnlineUserRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.OnlineUser;
import hkmu.comps380f.model.Poll;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class OnlineUserController {

    @Resource
    private OnlineUserRepository onlineUserRepo;
@Resource
    private LectureRepository lectureRepo;
    private Map<Long, Lecture> lectureDatabase = new ConcurrentHashMap<>();
    @Resource
    private PollRepository pollRepo;
    private Map<Long, Poll> pollDatabase = new ConcurrentHashMap<>();

    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("onlineUsers", onlineUserRepo.findAll());
        return "listUser";
    }

    @GetMapping("/comment/list")
    public ModelAndView userComment(Principal principal) {
        ModelAndView commentHistoryPage = new ModelAndView("commentList");
        commentHistoryPage.addObject("lectureComments", lectureRepo.getUserAllLectureComment(principal.getName()));
        commentHistoryPage.addObject("pollComments", pollRepo.getUserAllPollComment(principal.getName()));

        return commentHistoryPage;
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;
        private String fullname;
        private String phone;
        private String address;


        // Getters and Setters of subject, body, attachments

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

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "onlineUser", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        OnlineUser user = new OnlineUser(form.getUsername(),
                form.getPassword(), form.getRoles(),
                form.getFullname(), form.getPhone(), form.getAddress()
        );
        onlineUserRepo.save(user);
        return new RedirectView("/cslogin", true);
    }

    @GetMapping("/delete/{username}")
    public View deleteTicket(@PathVariable("username") String username) {
        onlineUserRepo.delete(username);
        return new RedirectView("/user/list", true);
    }

    @GetMapping("/list/{username}")
    public ModelAndView userDetail(@PathVariable("username") String username) throws Exception{
        ModelAndView userMV = new ModelAndView("userPage");
        List<OnlineUser> userResults = onlineUserRepo.findById(username);
        OnlineUser userRs = userResults.get(0);
        userMV.addObject("username",userRs.getUsername());
        userMV.addObject("password",userRs.getPassword());
        userMV.addObject("fullname",userRs.getFullname());
        userMV.addObject("phone",userRs.getPhone());
        userMV.addObject("address",userRs.getAddress());
        userMV.addObject("role",userRs.getRoles());

        
        return userMV;
    }

    @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username, @ModelAttribute("onlineUser") Form form,
            Principal principal) {
        List<OnlineUser> users = onlineUserRepo.findById(username);
        OnlineUser user = users.get(0);
        ModelAndView modelAndView = new ModelAndView("editUser");
        boolean studentRole = false;
        boolean lecturerRole = false;
        if(user.getRoles().contains("ROLE_STUDENT")){

            studentRole = true;
            modelAndView.addObject("studentRole", studentRole);
        }
        if(user.getRoles().contains("ROLE_LECTURER")){

            lecturerRole = true;

        }
        modelAndView.addObject("studentRole", studentRole);
        modelAndView.addObject("lecturerRole", lecturerRole);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/edit/{username}")
    public String edit(@PathVariable("username") String username, Form form,
            Principal principal) throws IOException {
        List<OnlineUser> infos = onlineUserRepo.findById(username);
        onlineUserRepo.edit(form.getUsername(), form.getPassword(), form.getFullname(), form.getRoles(), form.getAddress(), form.getPhone(), username);
        return "redirect:/course/list";
    }
   

}
