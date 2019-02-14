package tk.wonderdance.user.payload.achievement.create;

public class CreateAchievementSuccessResponse {

    private boolean success;
    private long achievement_id;

    public CreateAchievementSuccessResponse(boolean success, long achievement_id) {
        this.success = success;
        this.achievement_id = achievement_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(long achievement_id) {
        this.achievement_id = achievement_id;
    }
}
