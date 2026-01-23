package com.legenkiy.note_api.controllers;


import com.legenkiy.note_api.dto.NoteDto;
import com.legenkiy.note_api.model.Note;
import com.legenkiy.note_api.service.api.NoteService;
import com.legenkiy.note_api.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.metadata.ManagedOperation;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
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
    public ResponseEntity<Note> getNoteById(@PathVariable("id") long id){
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Long>> saveNote(@RequestBody NoteDto noteDto){
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
    public ResponseEntity<Map<String, String>> updateNote(@RequestBody NoteDto noteDto, @PathVariable("id") long id){


    }

}
