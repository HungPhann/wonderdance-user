package tk.wonderdance.user.payload.dance_genre.get;

import tk.wonderdance.user.model.DanceGenreName;

import java.util.Set;

public class GetDanceGernreResponse {

    private DanceGenreName[] dance_genre;

    public GetDanceGernreResponse(DanceGenreName[] dance_genre) {
        this.dance_genre = dance_genre;
    }

    public DanceGenreName[] getDance_genre() {
        return dance_genre;
    }

    public void setDance_genre(DanceGenreName[] dance_genre) {
        this.dance_genre = dance_genre;
    }
}
