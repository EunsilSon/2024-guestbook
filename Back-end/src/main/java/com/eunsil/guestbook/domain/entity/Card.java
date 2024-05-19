package com.eunsil.guestbook.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postDate;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList;

}
