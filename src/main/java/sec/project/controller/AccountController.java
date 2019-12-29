package sec.project.controller;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.repository.AccountRepository;
import sec.project.domain.Account;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {
          accountRepository.save(new Account("maxwell_smart",  passwordEncoder.encode("kenkapuhelin")));
          accountRepository.save(new Account("smart", passwordEncoder.encode("kenkapuhelin")));
    }
 
   @GetMapping("/login")
   public String login(){
     return "login";
   }
   
   @GetMapping("/register")
   public String register(){
     return "register";
   }

   
   @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }

    @PostMapping("/register")
    public String add(@RequestParam String username, @RequestParam String password) {
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/register";
        }
        
        Account a = new Account(username, passwordEncoder.encode(password));
        accountRepository.save(a);
        return "redirect:/login";
    }

}
