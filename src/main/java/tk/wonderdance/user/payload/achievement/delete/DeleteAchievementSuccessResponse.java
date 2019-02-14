package tk.wonderdance.user.payload.achievement.delete;

public class DeleteAchievementSuccessResponse {

    private boolean success;

    public DeleteAchievementSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
