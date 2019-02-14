package tk.wonderdance.user.payload.achievement.update;

public class UpdateAchievementSuccessResponse {

    private boolean success;

    public UpdateAchievementSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
