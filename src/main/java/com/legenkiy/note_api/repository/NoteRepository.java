package com.legenkiy.note_api.repository;

import com.legenkiy.note_api.model.Note;

import com.legenkiy.note_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserId(User user);

    Optional<Note> findByIdAndUserUsername(Long id, String username);

}



