package utils.random;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类-随机数
 * 
 */
public class RandomUtil {

	/**
	 * 获取从a至z，长度为length随机数
	 * 
	 * @return
	 */
	public static String getRandomStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取范围内int值
	 * 
	 * @param 获取范围内int值
	 * @return
	 */
	public static int getRandomRange(int max, int min) {
		return (int) (Math.random() * (max - min) + min);
	}

	/**
	 * 获取随机长度随机字符
	 * 
	 * @param length base
	 * @return
	 */
	public static String getRandomString(int length, String base) { // length表示生成字符串的长度
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取随机长度随机字符
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取随机长度随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumString(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 返回随机数列表
	 * @param start 开始值
	 * @param end 结束值
	 * @return
	 */
	public static List<Integer> getListRandom(int start, int end) {
		int[] numbers = getRangRandom(start, end);
		List<Integer> list = new ArrayList<Integer>();
		if(numbers==null || numbers.length<1) {
			return list;
		}
		for(int num : numbers) {
			list.add(num);
		}
		return list;
	}

	/**
	 * 返回随机数组
	 * 
	 * @param start 开始值
	 * @param end 结束值
	 * @return
	 */
	public static int[] getRangRandom(int start, int end) {
		return getRangRandom(start, end, end - start + 1);
	}

	/**
	 * 返回指定范围指定个数的不重复随机数。
	 * 
	 * @param start
	 * @param end
	 * @param num
	 * @return
	 */
	public static int[] getRangRandom(int start, int end, int num) {

		int length = end - start + 1;
		// 参数错误
		if (length < 1 || num > length) {
			return null;
		} else {
			int[] numbers = new int[length];
			int[] result = new int[num];
			// 循环赋初始值
			for (int i = 0; i < length; i++) {
				numbers[i] = i + start;
			}
			Random random = new Random();
			// 取randomMax次数
			for (int i = 0; i < num; i++) {
				// 随机获取取数的位置
				int m = random.nextInt(length - i) + i;
				result[i] = numbers[m];
				// 交换位置
				int temp = numbers[m];
				numbers[m] = numbers[i];
				numbers[i] = temp;
			}
			return result;
		}
	}

	/*
	 * 生成6位随机数验证码
	 */
	public static String code() {
		Set<Integer> set = GetRandomNumber();
		Iterator<Integer> iterator = set.iterator();
		String temp = "";
		while (iterator.hasNext()) {
			temp += iterator.next();
		}
		return temp;
	}

	public static Set<Integer> GetRandomNumber() {
		Set<Integer> set = new HashSet<Integer>();
		Random random = new Random();
		while (set.size() < 6) {
			set.add(random.nextInt(10));
		}
		return set;
	}

	public static String getNumberForPK() {

		String id = "";

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

		String temp = sf.format(new Date());

		int random = (int) (Math.random() * 10000);

		if (random < 1000) {

			String random1 = "0" + random;

			id = id + temp + random1;

		} else {
			id = id + temp + random;
		}

		return id;

	}

}
