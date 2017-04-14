package online.mrsys.movierecommender.action.user;

import online.mrsys.movierecommender.action.base.BaseAction;

public class LoadProfileAction extends BaseAction {

    private static final long serialVersionUID = -4961780923283262163L;
    
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String execute() throws Exception {// TODO load favorite, recommendation list
        return SUCCESS;
    }
    
}
