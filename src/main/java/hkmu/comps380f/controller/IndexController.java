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

   
    @GetMapping
    public String index() {
        return "redirect:/course/list";
    }
@GetMapping("/cslogin")
    public String login() {
        return "login";
    }
}
