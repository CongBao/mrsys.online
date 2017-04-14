package online.mrsys.movierecommender.action.ajax;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class CheckPasswordAction extends BaseAction {
    
    private static final long serialVersionUID = 3041564744421311176L;

    private boolean valid;
    
    private String id;
    private String password;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String execute() throws Exception {
        User user = userManager.getUserById(Integer.parseInt(getId()));
        if (user != null) {
            user.setPassword(getPassword());
            if (userManager.validLogin(user) != UserManager.LOGIN_FAIL) {
                setValid(true);
                return SUCCESS;
            }
        }
        setValid(false);
        return SUCCESS;
    }
    
}
