package online.mrsys.movierecommender.action.ajax;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;

public class AccountExistAction extends BaseAction {

    private static final long serialVersionUID = 7482984791949541357L;

    private boolean exist;
    
    private String account;

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

    @Override
    public String execute() throws Exception {
        User user = new User();
        user.setAccount(getAccount());
        if (userManager.isUserExist(user)) {
            setExist(true);
        } else  {
            setExist(false);
        }
        return SUCCESS;
    }
    
}
