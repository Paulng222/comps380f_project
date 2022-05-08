package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.Poll;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Resource
    private LectureRepository lectureRepo;
    private Map<Long, Lecture> lectureDatabase = new ConcurrentHashMap<>();
    @Resource
    private PollRepository pollRepo;
    private Map<Long, Poll> pollDatabase = new ConcurrentHashMap<>();

    @GetMapping
    public String index() {
        return "redirect:/course/list";
    }

    @GetMapping("/user/comment/list")
    public ModelAndView userComment(Principal principal) {
        ModelAndView commentHistoryPage = new ModelAndView("commentList");
        commentHistoryPage.addObject("lectureComments", lectureRepo.getUserAllLectureComment(principal.getName()));
        commentHistoryPage.addObject("pollComments", pollRepo.getUserAllPollComment(principal.getName()));

        return commentHistoryPage;
    }
  
}
