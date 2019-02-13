package tk.wonderdance.user.payload.user.follow;

public class FollowUserSuccessResponse {

    private boolean success;

    public FollowUserSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
