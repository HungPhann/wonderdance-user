package tk.wonderdance.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.wonderdance.user.model.DanceGenreName;
import tk.wonderdance.user.payload.dance_genre.get.GetDanceGernreResponse;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("dance-genre")
public class DancerGenreController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDanceGenreNames(){

        DanceGenreName[] danceGenreNames = DanceGenreName.values();
        GetDanceGernreResponse getDanceGernreResponse = new GetDanceGernreResponse(danceGenreNames);
        return ResponseEntity.ok(getDanceGernreResponse);
    }
}
