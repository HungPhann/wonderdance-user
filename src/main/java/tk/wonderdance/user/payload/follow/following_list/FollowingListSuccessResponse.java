package tk.wonderdance.user.payload.follow.following_list;

import java.util.Set;

public class FollowingListSuccessResponse {

    private boolean success;
    private Set<Long> user_ids;

    public FollowingListSuccessResponse(boolean success, Set<Long> user_ids) {
        this.success = success;
        this.user_ids = user_ids;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<Long> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(Set<Long> user_ids) {
        this.user_ids = user_ids;
    }
}
