package tk.wonderdance.user.payload.follow.follow;

import javax.validation.constraints.NotNull;

public class FollowUserRequest {

    @NotNull
    private Integer follower_user_id;

    @NotNull
    private Integer following_user_id;

    @NotNull
    private Boolean follow;

    public FollowUserRequest(@NotNull Integer follower_user_id, @NotNull Integer following_user_id, @NotNull Boolean follow) {
        this.follower_user_id = follower_user_id;
        this.following_user_id = following_user_id;
        this.follow = follow;
    }

    public Integer getFollower_user_id() {
        return follower_user_id;
    }

    public void setFollower_user_id(Integer follower_user_id) {
        this.follower_user_id = follower_user_id;
    }

    public Integer getFollowing_user_id() {
        return following_user_id;
    }

    public void setFollowing_user_id(Integer following_user_id) {
        this.following_user_id = following_user_id;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }
}
