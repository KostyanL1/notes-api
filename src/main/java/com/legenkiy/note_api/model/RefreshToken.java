package com.legenkiy.note_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "ip")
    private String ip;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "revoked")
    private boolean revoked;

}
