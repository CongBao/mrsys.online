package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.util.Serializer;
import online.mrsys.movierecommender.vo.UserBean;

public class UserBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
	    return Serializer.deserialize(values[0].getBytes());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		UserBean userBean = (UserBean) o;
		return userBean.toString();
	}

}
