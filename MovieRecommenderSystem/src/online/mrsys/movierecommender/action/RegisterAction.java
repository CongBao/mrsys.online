package online.mrsys.movierecommender.action;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;

public class RegisterAction extends BaseAction {
    
    private User user;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
}
