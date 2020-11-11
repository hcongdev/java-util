package utils.validate;


import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类
 */
public class ValidateUtil {
	private static Map<String, Object> map;

	
	/**
	 * 密码格式校验
	 * @param pwd
	 * @return
	 */
	public static String isPwd(String pwd) {
		String result = "";
	    if (pwd.length() < 8 || pwd.length() >16) {
	    	result = "密码长度必须是8-16位！";
	    	return result;
	    }
	    boolean b1 = Pattern.compile("[0-9]").matcher(pwd).find();
		boolean b2 = Pattern.compile("[a-zA-Z]").matcher(pwd).find();
		//boolean b3 = Pattern.compile("[A-Z]").matcher(pwd).find();
		boolean b4 = Pattern.compile("[`~!@#$%^&*()_\\-+=<,>.?/:;\"'\\{\\[\\}\\]\\|\\\\]").matcher(pwd).find();
		
		if(b1 && b2 && b4){
			result = "";
		}else{
			result = "密码必须是大写字母+小写字母+数字+特殊字符组成";
		}
		return result;
	}
	

	/**
	 * 校验是否中文
	 * @param str 字符串
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isChinese(String str) {
		Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
		Matcher matcher = regex.matcher(StringUtil.isNull(str));
		return matcher.matches();
	}

	/**
	 * 校验Email格式
	 * @param email 输入邮箱
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isEmail(String email) {
		Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(StringUtil.isNull(email));
		return matcher.matches();
	}

	/**
	 * 校验手机号格式
	 * @param phone 手机号
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isPhone(String phone) {
		Pattern regex = Pattern.compile("^1[3,4,5,7,8]\\d{9}$");
		Matcher matcher = regex.matcher(StringUtil.isNull(phone));
		boolean isMatched = matcher.matches();
		return isMatched;
	}
	
	/**
	 * 校验手机号
	 * @param phone 手机号
	 * @return 校验结果和消息
	 */
	public static Map<String, Object> checkPhone(String phone) {
		map = new HashMap<String, Object>();
		map.put("success", false);
		if (StringUtil.isBlank(phone)) {
			map.put("message", "请输入手机号！");
			return map;
		}
		if (!isPhone(phone)) {
			map.put("message", "请输入正确的11位手机号！");
		} else {
			map.put("success", true);
		}
		return map;
	}
	
	/**
	 * 判断是否是整数
	 * @param obj
	 * @return
	 */
	public static boolean isInteger(Object obj) {
		if(obj == null){
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
	    return pattern.matcher(obj.toString()).matches();  
	}
	
}
