package org.example.service;

import org.example.models.Author;
import org.example.repository.impl.AuthorRepositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorService {
    @Autowired
    private AuthorRepositoryImpl authorRepository;

    public AuthorService(AuthorRepositoryImpl repository) {
        this.authorRepository = repository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void updateAuthor(Author author) {
        Author existingAuthor = authorRepository.findById(author.getId());
        existingAuthor.setName(author.getName());
        authorRepository.update(existingAuthor);
    }

    public void deleteAuthor(Integer id) {
        authorRepository.delete(id);
    }
}