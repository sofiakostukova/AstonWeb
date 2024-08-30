package org.example.service;

import org.example.models.Moderator;
import org.example.repository.impl.ModeratorRepositoryImpl;

import java.util.List;

public class ModeratorService {
    private ModeratorRepositoryImpl moderatorRepository;

    public ModeratorService(ModeratorRepositoryImpl repository) {
        this.moderatorRepository = repository;
    }

    public List<Moderator> getAllModerators() {
        return moderatorRepository.findAll();
    }

    public Moderator getModeratorById(Integer id) {
        return moderatorRepository.findById(id);
    }

    public Moderator saveModerator(Moderator moderator) {
        return moderatorRepository.save(moderator);
    }

    public void updateModerator(Moderator moderator) {
        Moderator existingModerator = getModeratorById(moderator.getId());
        existingModerator.setModeratorRole(moderator.getModeratorRole());
        moderatorRepository.update(existingModerator);
    }

    public void deleteModerator(Integer id) {
        moderatorRepository.delete(id);
    }
}