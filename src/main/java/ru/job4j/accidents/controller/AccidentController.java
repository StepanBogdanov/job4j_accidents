package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с данным ID не найден");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accident/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось обновить инцидент с данным ID");
            return "error/404";
        }
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var isDeleted = accidentService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить инцидент с данным ID");
            return "error/404";
        }
        return "redirect:/index";
    }
}