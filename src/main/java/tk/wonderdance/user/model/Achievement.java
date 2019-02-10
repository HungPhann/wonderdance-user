package tk.wonderdance.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "dance_genre")
    private DanceGenreName danceGenre;

    @NotNull
    @Column(name = "competition")
    private String competition;

    public Achievement(@NotNull String title, DanceGenreName danceGenre, @NotNull String competition) {
        this.title = title;
        this.danceGenre = danceGenre;
        this.competition = competition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DanceGenreName getDanceGenre() {
        return danceGenre;
    }

    public void setDanceGenre(DanceGenreName danceGenre) {
        this.danceGenre = danceGenre;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }
}
