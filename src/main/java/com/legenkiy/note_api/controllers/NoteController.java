package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.NoteService;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<NoteDto>> getAllByUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<NoteDto> noteDtoList = noteService.findAllByUserId(user.getId()).stream()
                .map(n -> new NoteDto(
                        n.getTitle(),
                        n.getDescription(),
                        n.getTags(),
                        n.isPinned(),
                        n.isArchive()
                        )).toList();
        return ResponseEntity.ok(noteDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable("id") Long id, Authentication authentication) {
        Note note = noteService.findById(id, authentication);
        return ResponseEntity.ok(note);

    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveNote(@RequestBody NoteDto noteDto, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        long newNoteId = noteService.save(noteDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newNoteId);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Long> updateNote(@RequestBody NoteDto noteDto, @PathVariable("id") Long id, Authentication authentication) {
        long updatedNoteId = noteService.update(noteDto, id, authentication);
        return ResponseEntity.ok(updatedNoteId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteNote(@PathVariable("id") Long id, Authentication authentication) {
        noteService.delete(id, authentication);
        return ResponseEntity.ok(id);

    }

}
