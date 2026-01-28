package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.dto.NoteDto;

import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NoteService {

    Long save(NoteDto noteDto, User user);

    Note findById(Long id, Authentication authentication);

    List<Note> findAllByUserId(Long id);

    Long update(NoteDto noteDto, Long id, Authentication authentication);

    void delete(Long id, Authentication authentication);

}
