package online.mrsys.movierecommender.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import online.mrsys.movierecommender.vo.RoleBean;
import online.mrsys.movierecommender.vo.UserBean;

public class UserBeanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		UserBean userBean = new UserBean();
		RoleBean roleBean = new RoleBean();
		String[] data = values[0].split(",");
		userBean.setId(Integer.parseInt(data[0]));
		userBean.setAccount(data[1]);
		userBean.setPassword(data[2]);
		userBean.setEmail(data[3]);
		userBean.setMailVerified(Boolean.parseBoolean(data[4]));
		roleBean.setId(Integer.parseInt(data[5]));
		roleBean.setRole(data[6]);
		userBean.setRole(roleBean);
		return userBean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		UserBean userBean = (UserBean) o;
		return userBean.toString();
	}

}
