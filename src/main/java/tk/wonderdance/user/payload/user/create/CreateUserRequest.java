package tk.wonderdance.user.payload.user.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotNull
    @Min(1)
    private Long user_id;

    @NotNull
    private String email;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    public CreateUserRequest(@NotNull Long user_id, @NotNull String email, @NotNull String first_name, @NotNull String last_name) {
        this.user_id = user_id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
