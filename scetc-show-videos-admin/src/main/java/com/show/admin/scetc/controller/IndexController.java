package com.show.admin.scetc.controller;

import javax.servlet.http.HttpServletRequest;

import com.show.admin.scetc.annotation.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.show.admin.scetc.pojo.AdminUser;
import com.show.admin.scetc.utils.LybJsonResult;

@Controller
@RequestMapping("")
public class IndexController extends BasicController {


	/**
	 * 返回主頁
	 * 
	 * @param request
	 * @return
	 */
	@SysLog
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {

		// 从request中获取用户的基本信息
		ModelAndView modelAndView = new ModelAndView("thymeleaf/index");
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
		if (adminUser == null) {
			return new ModelAndView("thymeleaf/login");
		}
		// 将数据渲染到页面上
		modelAndView.addObject("adminUser", adminUser);
		return modelAndView;
	}
	@SysLog
	@RequestMapping("/")
	public ModelAndView show(HttpServletRequest request) {

		// 从request中获取用户的基本信息
		ModelAndView modelAndView = new ModelAndView("thymeleaf/index");
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
		if (adminUser == null) {
			return new ModelAndView("thymeleaf/login");

		}
		// 将数据渲染到页面上
		modelAndView.addObject("adminUser", adminUser);
		return modelAndView;
	}

	/**
	 * 主页初始化代码
	 * 
	 * @return
	 */
	@SysLog
	@RequestMapping("/init")
	public LybJsonResult init() {

		return LybJsonResult.ok();
	}

	// 500 错误页面
	@RequestMapping("/500")
	public String errorPage() {
		return "thymeleaf/500";
	}

	// 404 页面
	@RequestMapping("/404")
	public String notFoundPage() {
		return "thymeleaf/404";
	}

}
