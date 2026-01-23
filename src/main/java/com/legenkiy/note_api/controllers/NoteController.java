package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.service.api.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;


    @GetMapping()
    public ResponseEntity<List<Note>> getAllByUser() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Long>> saveNote(@RequestBody NoteDto noteDto) {
        long newNoteId = noteService.save(noteDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "noteId", newNoteId
                        )
                );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, Long>> updateNote(@RequestBody NoteDto noteDto, @PathVariable("id") Long id) {
        long updatedNoteId = noteService.update(noteDto, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        Map.of(
                                "noteId", updatedNoteId
                        )
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteNote(@PathVariable("id") Long id){
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
