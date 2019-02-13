package tk.wonderdance.user.payload.user.create;

public class CreateUserSuccessResponse {

    private boolean success;

    public CreateUserSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
