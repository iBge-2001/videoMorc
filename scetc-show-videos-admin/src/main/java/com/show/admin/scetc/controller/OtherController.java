package com.show.admin.scetc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.show.admin.scetc.mapper.BgmMapper;
import com.show.admin.scetc.mapper.MessageMapper;
import com.show.admin.scetc.mapper.UserMapper;
import com.show.admin.scetc.mapper.VideoMapper;
import com.show.admin.scetc.pojo.Count;
import com.show.admin.scetc.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.show.admin.scetc.pojo.AdminUser;
import com.show.admin.scetc.service.SettingService;
import com.show.admin.scetc.utils.EmailUtils;
import com.show.admin.scetc.utils.ImageCodeUtils;
import com.show.admin.scetc.utils.LybJsonResult;

/**
 * @author ibge
 */
@RestController
@RequestMapping("other")
public class OtherController extends BasicController {

	private static final String email_smtp_host = "email_smtp_host";
	private static final String email_smtp_username = "email_smtp_username";
	private static final String email_smtp_password = "email_smtp_password";
	private static final String email_from = "email_from";

	@Autowired
	private SettingService settingService;// 配置文件
	@Autowired
	MessageMapper messageMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	BgmMapper bgmMapper;
	/**
	 * 图片验证码
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/imageCode.do")
	public void sendImageCode(HttpServletResponse response, HttpServletRequest request) {
		ImageCodeUtils.sendImageCode(request.getSession(), response);
	}

	/**
	 * 发送html格式的邮件
	 * 
	 * @return
	 */
	@PostMapping("/sendEmail.do")
	public LybJsonResult sendEmail(String to, String subject, String content, HttpServletRequest request) {
		AdminUser adminUserVo = (AdminUser) request.getSession().getAttribute("adminUser");
		SimpleDateFormat formate = new SimpleDateFormat();
		String date = formate.format(new Date());
		Message message = new Message();
		UUID uuid = UUID.randomUUID();
		String idAsString = uuid.toString().replaceAll("-", "").substring(0, 12);
		message.setId(idAsString);
		message.setContent(subject+content);
		message.setAcceptId(to);
		message.setFromId("2");
		message.setTime(new Date());
		message.setIsread(false);
		messageMapper.insertMessage(message);
		redis.lpush(Operate_REDIS_SESSION, date + "&nbsp;&nbsp;&nbsp;" + adminUserVo.getRealName() + ":写下了一封邮件");
		return LybJsonResult.ok();
	};
	@GetMapping("/getCount")
	public Count getCount(){
		Integer videoCount = videoMapper.getVideoCount();
		Integer userCount = userMapper.getUserCount();
		Integer viewCount = videoMapper.getViewCount();
		Integer count = bgmMapper.getCount();
		Count count1 = new Count();
		count1.setBgmCount(count);
		count1.setUseCount(userCount);
		count1.setVideoNum(videoCount);
		count1.setViewCount(viewCount);
		return count1;
	}
}
