package com.bstek.dorado.sample.standardlesson.junior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.sample.standardlesson.dao.SlEmployeeDao;
import com.bstek.dorado.sample.standardlesson.entity.SlEmployee;
import com.bstek.dorado.web.DoradoContext;

@Component// （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public class LoginService {
	@Resource//该属性注入一个名称为xxx的bean。
	private SlEmployeeDao  slEmployeeDao;
	//验证登录密码是否正确
	@Expose//暴露服务 注册服务名loginService在ExposedServiceManager中，服务表达式为loginService#getSystemInfo  
	public Map doLogin(Map param){//这里可以直接传string类型的参数username和password dorado7的会自动适配
		
		String username = (String) param.get("username"); 
		String password = (String)param.get("password");
		Map result  = new HashMap();
		if(isValidate(username,password)){
			//如果成功了
			result.put("result", "true");
			//指定跳转的页面
			result.put("url", "com.bstek.dorado.sample.standardlesson.junior.main.Main.d");
		}else{
			//提示错误信息
			result.put("erromsg", "用户名或者密码不正确");
			result.put("result", "false");
		}
//		System.out.println(result);
		return result;
	}
	//校验方法  通过查询条件（用户名）添加到hibernate的find  找到用户，再对比password 
	public boolean isValidate(String username,String passwrod){
		//hibernate的查询条件工具
		DetachedCriteria dc = DetachedCriteria.forClass(SlEmployee.class);
		if(null!=username&&""!=username){
			dc.add(Restrictions.eq("userName", username.toUpperCase()));
		}
		List<SlEmployee> employees = slEmployeeDao.find(dc);//hibernate里面的方法
		if(employees.size()>0){//有存在这样的用户名时候
			SlEmployee employee = employees.get(0);//直接获取第一个用户的名称  这里不规范的地方就是没有限制用户名是否能够相同
			if(null!=passwrod&&!"".endsWith(passwrod)&&passwrod.equals(employee.getPassword())){
				//验证成功，放数据进去session
				/*DoradoContext是Dorado的上下文对象，在DoradoContext中，将上下文作用域区分为四种类型，分别为：
				view dorado视图作用域  request 等于httpservletRequest   session 等于httpsession  application 等于servletContext
				*/
				DoradoContext ctx = DoradoContext.getCurrent();//程序运级别
				HttpServletRequest request = request = ctx.getRequest();//请求级别
				request.getSession().setAttribute("user", employee);//回话级别  放到session中
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	//注销登录
	@Expose
	public Map doLogout(){
		//返回user为null
		DoradoContext ctx = DoradoContext.getCurrent();
		HttpServletRequest request = ctx.getRequest();
		request.getSession().setAttribute("user", null);
		//返回到登录页面
		Map result = new HashMap();
		result.put("url", "com.bstek.dorado.sample.standardlesson.junior.Login.d");
		result.put("result", true);
		return result;
	}
}
