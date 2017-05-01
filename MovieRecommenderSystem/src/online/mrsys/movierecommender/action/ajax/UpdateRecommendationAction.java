package online.mrsys.movierecommender.action.ajax;

import java.util.List;
import java.util.Map;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.util.Serializer;

public class UpdateRecommendationAction extends BaseAction {

    private static final long serialVersionUID = 6258552612584543035L;
    
    private Map<String, List<String>> updates;

    public Map<String, List<String>> getUpdates() {
        return updates;
    }

    public void setUpdates(Map<String, List<String>> updates) {
        this.updates = updates;
    }

    @Override
    public String execute() throws Exception {
        getUpdates().forEach((k, v) -> {
            User user = userManager.getUserById(Integer.parseInt(k));
            if (user == null) {
                return;
            }
            byte[] serializedRecom = Serializer.serialize(v);
            if (serializedRecom != null) {
                try {
                    userManager.updateRecommendation(user, serializedRecom);
                } catch (Exception e) {
                }
            }
        });
        return SUCCESS;
    }

}
