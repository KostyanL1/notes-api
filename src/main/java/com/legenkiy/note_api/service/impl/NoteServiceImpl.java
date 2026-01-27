package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.repository.NoteRepository;
import com.legenkiy.note_api.service.api.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;


    @Override
    @Transactional
    public Long save(NoteDto noteDto, User user) {
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        note.setLocalDateTime(LocalDateTime.now());
        note.setTags(noteDto.getTags());
        note.setPinned(noteDto.isPinned());
        note.setArchive(noteDto.isPinned());
        note.setUser(user);
        return noteRepository.save(note).getId();
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found!"));
    }

    @Override
    public List<Note> findAllByUser(User user) {

        return noteRepository.findAllByUserId(user);
    }

    @Override
    @Transactional
    public Long update(NoteDto noteDto, Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));
        Note note;
        if (isAdmin) {
            note = noteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Note not found"));
        } else {
            note = noteRepository.findByIdAndUserUsername(id, authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Not your note"));
        }
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        note.setLocalDateTime(LocalDateTime.now());
        note.setTags(noteDto.getTags());
        note.setPinned(noteDto.isPinned());
        note.setArchive(noteDto.isArchived());
        return noteRepository.save(note).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Failed to delete note!");
        }
    }
}
