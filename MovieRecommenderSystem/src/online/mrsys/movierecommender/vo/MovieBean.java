package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.function.Serializer;

public class MovieBean implements Serializable {
	
    private static final long serialVersionUID = -2023229095791525535L;
    
    private Integer id;
	private Integer imdb;
	private Integer year;
	private String title;
	
	public MovieBean() {
	}

	public MovieBean(Integer id, Integer imdb, Integer year, String title) {
		super();
		this.id = id;
		this.imdb = imdb;
		this.year = year;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getImdb() {
		return imdb;
	}

	public void setImdb(Integer imdb) {
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

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
