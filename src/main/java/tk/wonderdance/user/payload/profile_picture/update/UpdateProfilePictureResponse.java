package tk.wonderdance.user.payload.profile_picture.update;

public class UpdateProfilePictureResponse {

    private String profile_picture;

    public UpdateProfilePictureResponse(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
