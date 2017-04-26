package online.mrsys.movierecommender.action;

import online.mrsys.movierecommender.action.base.BaseAction;

public class SearchAction extends BaseAction {

    private static final long serialVersionUID = 7595095813274775579L;
    
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
}
