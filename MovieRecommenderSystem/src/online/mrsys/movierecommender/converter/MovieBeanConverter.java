package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.vo.MovieBean;

public class MovieBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		MovieBean movieBean = new MovieBean();
		String[] data = values[0].split(",");
		movieBean.setId(Integer.parseInt(data[0]));
		movieBean.setImdb(Integer.parseInt(data[1]));
		movieBean.setYear(Integer.parseInt(data[2]));
		movieBean.setTitle(data[3]);
		return movieBean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		MovieBean movieBean = (MovieBean) o;
		return movieBean.toString();
	}

}
