package hkarabakla.model;

public class LastSession {

    private User user;

    public LastSession() {
    }

    public LastSession(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
