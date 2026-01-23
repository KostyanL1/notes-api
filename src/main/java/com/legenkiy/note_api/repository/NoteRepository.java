package com.legenkiy.note_api.repository;

import com.legenkiy.note_api.model.Note;

import com.legenkiy.note_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserId(User user);

}



