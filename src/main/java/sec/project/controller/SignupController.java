package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.domain.Account;
import sec.project.repository.SignupRepository;
import sec.project.repository.AccountRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String defaultMapping() {
      return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
      return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String social_sec) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String username = auth.getName();
      Account user = accountRepository.findByUsername(username);
      signupRepository.save(new Signup(name, social_sec, user.getId()));
      return "done";
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String viewSignup(@PathVariable(value="id") Long id, Model model) {
      Signup signup = signupRepository.findById(id);
      model.addAttribute("signup", signup);
      return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateSignup(@PathVariable(value="id") Long id, @RequestParam String name, @RequestParam String social_sec, Model model) {
      Signup signup = signupRepository.findById(id);
      signup.setName(name);
      signup.setSocial_sec(social_sec);
      signupRepository.save(signup);
      return "done";
    }

    
    @RequestMapping("/")
    public String listAll(Model model){
      List<Signup> attendees = signupRepository.findAll();
      model.addAttribute("attendees", attendees);
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String username = auth.getName();
      Account user = accountRepository.findByUsername(username);
      model.addAttribute("user", user);
      return "all";
    }

}
