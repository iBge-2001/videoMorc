package com.show.admin.scetc.controller;

import java.util.List;

import com.show.admin.scetc.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.show.admin.scetc.pojo.AdminUser;
import com.show.admin.scetc.service.RolesService;
import com.show.admin.scetc.utils.CommonUtils;
import com.show.admin.scetc.utils.LybJsonResult;

/**
 * 对权限栏目的增删改查
 * 
 * @author ibge
 *
 */
@RestController
@RequestMapping("/role")
public class RolesController {
	@Autowired
	private RolesService rolesService;// 权限服务类
	// 查询所有的权限数据

	@RequestMapping("queryAllRole")
	@SysLog
	public LybJsonResult queryAllRole() {
		return LybJsonResult.ok(rolesService.queryAllRoles());
	}

	// 查询角色下所拥有的人物
	@RequestMapping("/queryAllAdminUsersByRoleId")
	@SysLog
	public LybJsonResult queryAllAdminUserByRoleId(String roleId) {

		if (CommonUtils.isEmpty(roleId)) {
			return LybJsonResult.errorMsg("当前roleId不能为空");
		}
		List<AdminUser> list = rolesService.queryAllAdminUserByRoleId(roleId);

//        查询当前roleId下面所拥有的所有管理员账户
		return LybJsonResult.ok(list);
	}

	// 查询所有的能力
	@RequestMapping("queryAllPower")
	@SysLog
	public LybJsonResult queryAllPower() {
		return LybJsonResult.ok(rolesService.queryAllPowers());
	}

	// 查詢所有的能力于角色的關係
	@RequestMapping("queryAllRoleToPower")
	@SysLog
	public LybJsonResult queryAll() {
		return LybJsonResult.ok(rolesService.queryAllRolesToPower());
	}

	// 查询所有管理员与角色的对应关系
	@RequestMapping("queryAllAdminToRole")
	@SysLog
	public LybJsonResult queryAllAdminToRole() {
		return LybJsonResult.ok(rolesService.queryAllAdminToRole());
	}

	// 将用户添加到指定的角色中
	@RequestMapping("addAdminUserAndRole")
	@SysLog
	public LybJsonResult addAdminUserAndRole(String adminId, String roleId) {
		if (CommonUtils.isEmpty(adminId) || CommonUtils.isEmpty(roleId)) {
			return LybJsonResult.errorMsg("用户id或者角色id不能为空");
		}
		// 判断数据库中是否存在这条信息 如果存在就不能添加了
		boolean isExsit = rolesService.isExsits(adminId, roleId);
		if (!isExsit) {
			rolesService.addAdminUserAndRoles(adminId, roleId);
			return LybJsonResult.ok();
		} else {
			return LybJsonResult.errorMsg("已经添加进去了，请不要反复添加");
		}
	}

}
