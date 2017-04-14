package online.mrsys.movierecommender.action.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import online.mrsys.movierecommender.action.base.BaseAction;

public class RefreshMoviesAction extends BaseAction {

    private static final long serialVersionUID = 1617437325137567131L;

    private Map<String, String> newMovies;

    private List<Integer> oldMovies;

    public Map<String, String> getNewMovies() {
        return newMovies;
    }

    public void setNewMovies(Map<String, String> newMovies) {
        this.newMovies = newMovies;
    }

    public List<Integer> getOldMovies() {
        return oldMovies;
    }

    public void setOldMovies(List<Integer> oldMovies) {
        this.oldMovies = oldMovies;
    }

    @Override
    public String execute() throws Exception {
        Map<String, String> ids = new HashMap<>();
        Random random = new Random();
        long num = movieManager.getMovieCount();
        for (int i = 0; i < 10; i++) {
            int id = random.nextInt((int) num) + 1;
            if (getOldMovies().contains(id)) {
                i--;
                continue;
            }
            ids.put(String.valueOf(id), movieManager.getMovieBeanById(id).getImdb());
        }
        setNewMovies(ids);
        return SUCCESS;
    }

}
