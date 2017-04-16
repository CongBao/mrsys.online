package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.util.Serializer;

public class FavoriteBean implements Serializable {
    
    private static final long serialVersionUID = -4206053491351201065L;
    
    private UserBean user;
    private MovieBean movie;
    
    public FavoriteBean() {
    }
    
    public FavoriteBean(UserBean user, MovieBean movie) {
        super();
        this.user = user;
        this.movie = movie;
    }
    
    public UserBean getUser() {
        return user;
    }
    
    public void setUser(UserBean user) {
        this.user = user;
    }
    
    public MovieBean getMovie() {
        return movie;
    }
    
    public void setMovie(MovieBean movie) {
        this.movie = movie;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movie == null) ? 0 : movie.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FavoriteBean other = (FavoriteBean) obj;
        if (movie == null) {
            if (other.movie != null)
                return false;
        } else if (!movie.equals(other.movie))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }
    
}
