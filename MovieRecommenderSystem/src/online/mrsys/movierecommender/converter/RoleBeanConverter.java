package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.vo.RoleBean;

public class RoleBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		RoleBean roleBean = new RoleBean();
		String[] data = values[0].split(",");
		roleBean.setId(Integer.parseInt(data[0]));
		roleBean.setRole(data[1]);
		return roleBean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		RoleBean roleBean = (RoleBean) o;
		return roleBean.toString();
	}

}
