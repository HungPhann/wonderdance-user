package tk.wonderdance.user.payload.achievement.update;

public class UpdateAchievementResponse {

    private boolean success;

    public UpdateAchievementResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
