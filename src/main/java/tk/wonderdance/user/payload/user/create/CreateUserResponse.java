package tk.wonderdance.user.payload.user.create;

public class CreateUserResponse {

    private boolean success;

    public CreateUserResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
