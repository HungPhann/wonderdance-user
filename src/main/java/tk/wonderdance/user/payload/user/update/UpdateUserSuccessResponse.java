package tk.wonderdance.user.payload.user.update;

public class UpdateUserSuccessResponse {

    private boolean success;

    public UpdateUserSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
