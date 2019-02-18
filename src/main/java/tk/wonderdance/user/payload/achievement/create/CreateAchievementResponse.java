package tk.wonderdance.user.payload.achievement.create;

public class CreateAchievementResponse {

    private long achievement_id;

    public CreateAchievementResponse(long achievement_id) {
        this.achievement_id = achievement_id;
    }

    public long getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(long achievement_id) {
        this.achievement_id = achievement_id;
    }
}
