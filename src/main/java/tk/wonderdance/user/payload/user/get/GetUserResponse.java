package tk.wonderdance.user.payload.user.get;

import java.util.Map;

public class GetUserResponse {

    private Map data;

    public GetUserResponse(Map data) {
        this.data = data;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
