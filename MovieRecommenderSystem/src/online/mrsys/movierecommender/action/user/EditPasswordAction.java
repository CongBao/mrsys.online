package online.mrsys.movierecommender.action.user;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;

public class EditPasswordAction extends BaseAction {
    
    private User user;
    private String newPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
}
