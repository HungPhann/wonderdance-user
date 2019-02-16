package tk.wonderdance.user.payload.user.get;

import java.util.Map;

public class GetUserResponse {

    private boolean success;
    private Map data;

    public GetUserResponse(boolean success, Map data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
