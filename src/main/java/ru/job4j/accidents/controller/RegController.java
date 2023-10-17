package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.AuthorityRepository;
import ru.job4j.accidents.repository.data.UserRepository;

@Controller
@AllArgsConstructor
public class RegController {

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("userError", true);
            return "redirect:/reg";
        }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}