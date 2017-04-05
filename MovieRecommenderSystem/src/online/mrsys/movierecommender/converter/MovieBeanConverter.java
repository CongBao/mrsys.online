package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.function.Serializer;
import online.mrsys.movierecommender.vo.MovieBean;

public class MovieBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
	    return Serializer.deserialize(values[0].getBytes());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		MovieBean movieBean = (MovieBean) o;
		return movieBean.toString();
	}

}
