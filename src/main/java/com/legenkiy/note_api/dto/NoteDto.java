package com.legenkiy.note_api.dto;


import com.legenkiy.note_api.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoteDto {

    @Size(min = 1, max = 25, message = "Title can`t be longer then 25 chars!")
    private String title;
    @Size(min = 1, max = 5000, message = "Character limit reached - 5000!")
    @NotBlank(message = "Content must be present!")
    private String description;
    @NotNull(message = "At least one tag must be present!")
    private Set<String> tags;
    private boolean pinned;
    private boolean archived;
    @NotNull(message = "User id must be present!")
    private User userId;

}
