package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.util.Serializer;

/**
 * @version 1.1
 * @author Cong Bao
 *
 */
public class MovieBean implements Serializable {
    
    private static final long serialVersionUID = -8797816095960294924L;
    
    private Integer id;
	private String imdb;
	private Integer year;
	private String title;
	private Integer neighbours;
	
	public MovieBean() {
	}

    public MovieBean(Integer id, String imdb, Integer year, String title, Integer neighbours) {
        super();
        this.id = id;
        this.imdb = imdb;
        this.year = year;
        this.title = title;
        this.neighbours = neighbours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Integer neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        MovieBean other = (MovieBean) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
