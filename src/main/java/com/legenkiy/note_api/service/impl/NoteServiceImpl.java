package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.repository.NoteRepository;
import com.legenkiy.note_api.service.api.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;


    @Override
    @Transactional
    public long save(NoteDto noteDto) {
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        note.setTags(noteDto.getTags());
        note.setUserId(noteDto.getUserId());
        return noteRepository.save(note).getId();

    }

    @Override
    public Note findById(long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found!"));
    }

    @Override
    public List<Note> findAllByUser(User user) {
        return noteRepository.findAllByUserId(user);
    }

    @Override
    @Transactional
    public void update(NoteDto noteDto, long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setTitle(noteDto.getTitle());
            note.setDescription(noteDto.getDescription());
            note.setLocalDateTime(LocalDateTime.now());
            note.setTags(noteDto.getTags());
            note.setPinned(noteDto.isPinned());
            note.setArchive(noteDto.isArchived());
            noteRepository.save(note);
        }
        else {
            throw new RuntimeException("Failed to update!");
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (noteRepository.existsById(id)){
            noteRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Failed to delete note!");
        }
    }
}
