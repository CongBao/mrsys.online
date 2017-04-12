package online.mrsys.movierecommender.action.ajax;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;

public class CheckExistAction extends BaseAction {

    private static final long serialVersionUID = 4705780448443882132L;

    private boolean exist;
    
    private String account;
    private String email;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String checkAccount() throws Exception {
        User user = new User();
        user.setAccount(getAccount());
        if (userManager.isUserExist(user)) {
            setExist(true);
        } else  {
            setExist(false);
        }
        return SUCCESS;
    }
    
    public String checkEmail() throws Exception {
        if (userManager.isUserExist(getEmail())) {
            setExist(true);
        } else {
            setExist(false);
        }
        return SUCCESS;
    }
    
}
