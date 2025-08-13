package com.jagdish.SocailSphere.model.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reel(String caption, String videoUrl, User user) {
    }
}

