package me.elhakimi.vroom.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String title;
    private String description;
    private String telephone;
    private double price;
    private boolean isPublished;
    private boolean isArchived;
    private int status;

    @OneToMany
    private List<ArticleImages> articleImages;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "article") // Correct mappedBy to point to the field in Likes
    private List<Likes> likes;

    @OneToOne
    private City city;

    @OneToOne(mappedBy = "article") // Correct mappedBy to point to the field in Model
    private Model model;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
