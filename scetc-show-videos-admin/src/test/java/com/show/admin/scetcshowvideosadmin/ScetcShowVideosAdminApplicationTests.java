package com.show.admin.scetcshowvideosadmin;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.show.admin.scetc.ScetcShowVideosAdminApplication;

/**
 * 编写测试代码
 * 
 * @author Ray
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ScetcShowVideosAdminApplication.class)
public class ScetcShowVideosAdminApplicationTests {
	private TestRestTemplate template = new TestRestTemplate();

//	@Before
//	public void init() {
//		System.out.println("-----初始化代码---------");
//	}

//	@Test
//	public void test1() {
//		String url = "http://localhost:8082/welcome";
//		String a = template.patchForObject(url, null, String.class);
//		System.out.println(a.toString());
//	}
//
//	@Test
//	public void test2() {
//		String url = "http://localhost:8082/index";
//		String a = template.patchForObject(url, null, String.class);
//		System.out.println(a.toString());
//	}
//
//	@Test
//	public void test3() {
//		String url = "http://localhost:8082/adminUser/login";
//		String a = template.patchForObject(url, null, String.class);
//		System.out.println(a.toString());
//	}
//
//	/**
//	 * 测试方法执行后执行
//	 */
//	@After
//	public void after() {
//		System.out.println("-----执行测试完毕-----------");
//
//	}
@Test
// 计算一个字符串的MD5值
public void   calculateMD6() {
	char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	try {
		String s = "111111610492";
		byte[] btInput = s.getBytes();
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		System.out.println(str);
//		return new String(str);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
}
}
