package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.model.Notes;
import hkmu.comps380f.model.Poll;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/course")
public class LectureController {

    @Resource
    private LectureRepository lectureRepo;
    private Map<Long, Lecture> lectureDatabase = new ConcurrentHashMap<>();
    @Resource
    private PollRepository pollRepo;
    private Map<Long, Poll> pollDatabase = new ConcurrentHashMap<>();

    // Controller methods, Form object, ...
    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("lectureDatabase", lectureRepo.getLecture());
        model.addAttribute("pollDatabase", pollRepo.getPolls());
        return "list";
    }

    @GetMapping("/createLecture")
    public ModelAndView createLecture() {
        return new ModelAndView("addLecture", "lectureForm", new Form());
    }

    public static class Form {

        private String subject;
        private String body;
        private List<MultipartFile> notes;

        // Getters and Setters of subject, body, attachments
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

        public List<MultipartFile> getNotes() {
            return notes;
        }

        public void setNotes(List<MultipartFile> notes) {
            this.notes = notes;
        }
    }

    @PostMapping("/createLecture")
    public String createLecture(Form form, Principal principal) throws IOException {
        long lectureId = lectureRepo.createLecture(principal.getName(),
                form.getSubject(), form.getBody(), form.getNotes());
        return "redirect:/course/viewLecture/lectureId=" + lectureId;
    }

    @GetMapping("/viewLecture/lectureId={lectureId}")
    public String viewLecture(@PathVariable("lectureId") long lectureId, ModelMap model) {
        List<Lecture> lecture = lectureRepo.getLecture(lectureId);
        if (lecture.isEmpty()) {
            return "redirect:/course/list";
        }
        model.addAttribute("lectureId", lectureId);
        model.addAttribute("lecture", lecture.get(0));
        return "viewLecture";
    }

    @GetMapping("/lectureId={lectureId}/notes/{notes:.+}")
    public View download(@PathVariable("lectureId") long lectureId,
            @PathVariable("notes") String name) {
        Notes notes = lectureRepo.getNotes(lectureId, name);
        if (notes != null) {
            return new DownloadingView(notes.getName(),
                    notes.getMimeContentType(), notes.getContents());
        }
        return new RedirectView("/course/list", true);
    }

    @GetMapping("/editLecture/lectureId={lectureId}")
    public ModelAndView showEditLecture(@PathVariable("lectureId") long lectureId,
            Principal principal, HttpServletRequest request) {
        List<Lecture> lectures = lectureRepo.getLecture(lectureId);
        if (lectures.isEmpty()
                || (!request.isUserInRole("ROLE_LECTURER")
                && !principal.getName().equals(lectures.get(0).getUserName()))) {
            return new ModelAndView(new RedirectView("/course/list", true));
        }
        Lecture lecture = lectures.get(0);
        ModelAndView modelAndView = new ModelAndView("editLecture");
        modelAndView.addObject("lectureId", lectureId);
        modelAndView.addObject("lecture", lecture);
        Form lectureForm = new Form();
        lectureForm.setSubject(lecture.getSubject());
        lectureForm.setBody(lecture.getBody());
        modelAndView.addObject("lectureForm", lectureForm);
        return modelAndView;
    }

    @PostMapping("/editLecture/lectureId={lectureId}")
    public String editLecture(@PathVariable("lectureId") long lectureId, Form form,
            Principal principal, HttpServletRequest request) throws IOException {
        List<Lecture> lectures = lectureRepo.getLecture(lectureId);
        if (lectures.isEmpty()
                || (!request.isUserInRole("ROLE_LECTURER")
                && !principal.getName().equals(lectures.get(0).getUserName()))) {
            return "redirect:/course/list";
        }
        lectureRepo.updateLecture(lectureId, form.getSubject(),
                form.getBody(), form.getNotes());
        return "redirect:/course/viewLecture/lectureId=" + lectureId;
    }

    @GetMapping("/lectureId={lectureId}/deleteNotes/{notes:.+}")
    public String deleteNotes(@PathVariable("lectureId") long lectureId,
            @PathVariable("notes") String name) {
        lectureRepo.deleteNotes(lectureId, name);
        return "redirect:/course/editLecture/lectureId=" + lectureId;
    }

    @GetMapping("/deleteLecture/lectureId={lectureId}")
    public String deleteLecture(@PathVariable("lectureId") long lectureId,
            Principal principal, HttpServletRequest request) {
        List<Lecture> lectures = lectureRepo.getLecture(lectureId);
        if (lectures.isEmpty()
                || (!request.isUserInRole("ROLE_LECTURER")
                && !principal.getName().equals(lectures.get(0).getUserName()))) {
            return "redirect:/course/list";
        }
        lectureRepo.deleteLecture(lectureId);
        return "redirect:/course/list";
    }
}
