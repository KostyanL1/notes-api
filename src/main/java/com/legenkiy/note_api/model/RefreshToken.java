package com.legenkiy.note_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "refreshToken")
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
    private User userId;
    @Column(name = "revoked")
    private boolean revoked;

}
