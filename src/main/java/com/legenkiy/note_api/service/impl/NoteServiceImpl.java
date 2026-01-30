package com.legenkiy.note_api.service.impl;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.exceptions.ObjectNotFoundException;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.repository.NoteRepository;
import com.legenkiy.note_api.service.api.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        note.setArchive(noteDto.isArchived());
        note.setUser(user);
        return noteRepository.save(note).getId();
    }

    @Override
    public Note findById(Long id, Authentication authentication) {
        return getNoteIfAuthorized(id, authentication);
    }

    @Override
    public List<Note> findAllByUserId(Long id) {

        return noteRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public Long update(NoteDto noteDto, Long id, Authentication authentication) {
        Note note = getNoteIfAuthorized(id, authentication);
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
    public void delete(Long id, Authentication authentication) {
        noteRepository.deleteById(getNoteIfAuthorized(id, authentication).getId());

    }

    private Note getNoteIfAuthorized(Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));
        Note note;
        if (isAdmin) {
            note = noteRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Note not found"));
        } else {
            note = noteRepository.findByIdAndUserUsername(id, authentication.getName())
                    .orElseThrow(() -> new AccessDeniedException("Not your note"));
        }
        return note;
    }
}
