package tk.wonderdance.user.model;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "user_achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "dance_genre")
    @NotNull
    private DanceGenreName danceGenre;

    @NotNull
    @NotBlank
    @Column(name = "competition")
    private String competition;

    @NotNull
    @Column(name = "year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Achievement(@NotNull String title, DanceGenreName danceGenre, @NotNull String competition, @NotNull int year, User user) {
        this.title = title;
        this.danceGenre = danceGenre;
        this.competition = competition;
        this.year = year;
        this.user = user;
    }

    public Achievement(){

    }

    public Map<String, Object> toJson(){
        Map<String, Object> map = new HashMap<>();

        map.put("achievement_id", id);
        map.put("title", title);
        map.put("dance_genre", danceGenre);
        map.put("competition", competition);
        map.put("year", year);
        return map;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
