package com.legenkiy.note_api.service.api;

import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;

import java.util.List;

public interface NoteService {

    void save(NoteDto noteDto);
    Note findById(long id);
    List<Note> findAllByUser(User user);
    void update(NoteDto noteDto, long id);
    void delete(long id);

}
