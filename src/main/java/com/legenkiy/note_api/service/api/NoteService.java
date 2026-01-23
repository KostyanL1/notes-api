package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;

import java.util.List;

public interface NoteService {

    Long save(NoteDto noteDto);
    Note findById(Long id);
    List<Note> findAllByUser(User user);
    Long update(NoteDto noteDto, Long id);
    void delete(Long id);

}
