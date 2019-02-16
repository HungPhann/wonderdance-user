package tk.wonderdance.user.payload.achievement.delete;

public class DeleteAchievementResponse {

    private boolean success;

    public DeleteAchievementResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
