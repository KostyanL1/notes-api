package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.model.User;
import com.legenkiy.note_api.service.api.CookieService;
import com.legenkiy.note_api.service.api.JwtService;
import com.legenkiy.note_api.service.api.NoteService;
import com.legenkiy.note_api.service.api.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<Note>> getAllByUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(noteService.findAllByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable("id") Long id, Authentication authentication) {
        Note note = noteService.findById(id);
        if (Objects.equals(note.getUser().getId(), userService.findByUsername(authentication.getName()).getId())){
            return ResponseEntity.ok(note);
        }else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message", "note not found"
                            )
                    );
        }

    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Long>> saveNote(@RequestBody NoteDto noteDto, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        long newNoteId = noteService.save(noteDto, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "noteId", newNoteId
                        )
                );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, Long>> updateNote(@RequestBody NoteDto noteDto, @PathVariable("id") Long id, Authentication authentication) {
        long updatedNoteId = noteService.update(noteDto, id, authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "noteId", updatedNoteId
                        )
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteNote(@PathVariable("id") Long id, Authentication authentication) {
        noteService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "message", "Note was deleted"
                        )
                );
    }

}
