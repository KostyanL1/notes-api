package com.legenkiy.note_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    @ElementCollection
    @CollectionTable(
            name = "note_tags",
            joinColumns = @JoinColumn(name = "note_id")
    )
    @Column(name = "tag")
    private Set<String> tags;
    @Column(name = "pinned")
    private boolean pinned;
    @Column(name = "archived")
    private boolean archive;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;



}
