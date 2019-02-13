package tk.wonderdance.user.payload.user.unfollow;

public class UnfollowUserSuccessResponse {

    private boolean success;

    public UnfollowUserSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
