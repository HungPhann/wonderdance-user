package tk.wonderdance.user.payload.user.update;

public class UpdateUserResponse {

    private boolean success;

    public UpdateUserResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
