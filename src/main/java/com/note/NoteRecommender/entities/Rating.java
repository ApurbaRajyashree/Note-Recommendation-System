package com.note.NoteRecommender.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rating")
public class Rating implements Comparable<Rating>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rate_id")
    private Long ratingId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="note_id_fk",referencedColumnName = "note_id")
    @JsonBackReference(value = "note_table")
    private Note note;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_fk",referencedColumnName = "user_id")
    @JsonBackReference(value = "user_table")
    private User user;

    private float value;

    private float maxRating=5;

    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;

        return 0;
    }
}
