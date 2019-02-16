package tk.wonderdance.user.payload.users.get;

import java.util.Map;
import java.util.Set;

public class GetUsersSuccessResponse {

    private boolean success;
    private Set<Map<String, Object>> data;

    public GetUsersSuccessResponse(boolean success, Set<Map<String, Object>> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<Map<String, Object>> getData() {
        return data;
    }

    public void setData(Set<Map<String, Object>> data) {
        this.data = data;
    }
}
