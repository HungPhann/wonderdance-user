package tk.wonderdance.user.payload.achievement.create;

import tk.wonderdance.user.model.DanceGenreName;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateAchievementRequest {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private DanceGenreName dance_genre;

    @NotNull
    @NotBlank
    private String Competition;

    @NotNull
    @Min(1)
    private Integer year;

    public CreateAchievementRequest(@NotNull @NotBlank String title, @NotNull DanceGenreName dance_genre, @NotNull @NotBlank String competition, @NotNull @Min(1) Integer year) {
        this.title = title;
        this.dance_genre = dance_genre;
        Competition = competition;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DanceGenreName getDance_genre() {
        return dance_genre;
    }

    public void setDance_genre(DanceGenreName dance_genre) {
        this.dance_genre = dance_genre;
    }

    public String getCompetition() {
        return Competition;
    }

    public void setCompetition(String competition) {
        Competition = competition;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
