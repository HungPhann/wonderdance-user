package tk.wonderdance.user.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tk.wonderdance.user.helper.aws_s3.service.S3Services;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.profile_picture.update.UpdateProfilePictureResponse;
import tk.wonderdance.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("user/{user_id}/profile-picture")
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    S3Services s3Services;

    @Value("${aws.s3.url}")
    private String awsS3Url;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> uploadProfilePicture(@PathVariable("user_id") long userID,
                                                  @RequestParam("profile_picture")MultipartFile profilePicture){

        try{
            User user = userRepository.findUserById(userID).get();
            String fileName = "user/" + userID + "/profile_picture/" + userID + ".jpg";
            s3Services.uploadFile(fileName, profilePicture);

            String image_url = awsS3Url + fileName;
            user.setProfilePicture(image_url);
            userRepository.save(user);

            UpdateProfilePictureResponse updateProfilePictureResponse = new UpdateProfilePictureResponse(true, image_url);
            return ResponseEntity.ok(updateProfilePictureResponse);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (AmazonServiceException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (AmazonClientException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
