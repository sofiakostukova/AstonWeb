package org.example.controller;

import org.example.models.Moderator;
import org.example.service.ModeratorService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

public class ModeratorController {
    private ModeratorService moderatorService;

    public ModeratorController(ModeratorService service) {
        this.moderatorService = service;
    }

    @GetMapping
    public String getAllModerators(Model model) {
        model.addAttribute("moderators", moderatorService.getAllModerators());
        return "moderators";
    }

    @GetMapping("/{id}")
    public String getModeratorById(@PathVariable Integer id, Model model) {
        model.addAttribute("moderator", moderatorService.getModeratorById(id));
        return "moderator";
    }

    @PostMapping
    public String saveModerator(@ModelAttribute Moderator moderator) {
        moderatorService.saveModerator(moderator);
        return "redirect:/moderators";
    }

    @PutMapping("/{id}")
    public String updateModerator(@PathVariable Integer id, @ModelAttribute Moderator moderator) {
        moderator.setId(id);
        moderatorService.updateModerator(moderator);
        return "redirect:/moderators";
    }

    @DeleteMapping("/{id}")
    public String deleteModerator(@PathVariable Integer id) {
        moderatorService.deleteModerator(id);
        return "redirect:/moderators";
    }
}