package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.function.Serializer;
import online.mrsys.movierecommender.vo.RoleBean;

public class RoleBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
	    return Serializer.deserialize(values[0].getBytes());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		RoleBean roleBean = (RoleBean) o;
		return roleBean.toString();
	}

}
