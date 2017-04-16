package online.mrsys.movierecommender.action;

import online.mrsys.movierecommender.action.base.BaseAction;

public class SearchAction extends BaseAction {
    
    private static final long serialVersionUID = 1L;
    
    private String s;
    private String page;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
}
