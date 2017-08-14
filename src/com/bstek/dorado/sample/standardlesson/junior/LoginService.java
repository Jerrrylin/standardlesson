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

@Component// ������ͨpojoʵ������spring�����У��൱�������ļ��е�<bean id="" class=""/>��
public class LoginService {
	@Resource//������ע��һ������Ϊxxx��bean��
	private SlEmployeeDao  slEmployeeDao;
	//��֤��¼�����Ƿ���ȷ
	@Expose//��¶���� ע�������loginService��ExposedServiceManager�У�������ʽΪloginService#getSystemInfo  
	public Map doLogin(Map param){//�������ֱ�Ӵ�string���͵Ĳ���username��password dorado7�Ļ��Զ�����
		
		String username = (String) param.get("username"); 
		String password = (String)param.get("password");
		Map result  = new HashMap();
		if(isValidate(username,password)){
			//����ɹ���
			result.put("result", "true");
			//ָ����ת��ҳ��
			result.put("url", "com.bstek.dorado.sample.standardlesson.junior.main.Main.d");
		}else{
			//��ʾ������Ϣ
			result.put("erromsg", "�û����������벻��ȷ");
			result.put("result", "false");
		}
//		System.out.println(result);
		return result;
	}
	//У�鷽��  ͨ����ѯ�������û�������ӵ�hibernate��find  �ҵ��û����ٶԱ�password 
	public boolean isValidate(String username,String passwrod){
		//hibernate�Ĳ�ѯ��������
		DetachedCriteria dc = DetachedCriteria.forClass(SlEmployee.class);
		if(null!=username&&""!=username){
			dc.add(Restrictions.eq("userName", username.toUpperCase()));
		}
		List<SlEmployee> employees = slEmployeeDao.find(dc);//hibernate����ķ���
		if(employees.size()>0){//�д����������û���ʱ��
			SlEmployee employee = employees.get(0);//ֱ�ӻ�ȡ��һ���û�������  ���ﲻ�淶�ĵط�����û�������û����Ƿ��ܹ���ͬ
			if(null!=passwrod&&!"".endsWith(passwrod)&&passwrod.equals(employee.getPassword())){
				//��֤�ɹ��������ݽ�ȥsession
				/*DoradoContext��Dorado�������Ķ�����DoradoContext�У�������������������Ϊ�������ͣ��ֱ�Ϊ��
				view dorado��ͼ������  request ����httpservletRequest   session ����httpsession  application ����servletContext
				*/
				DoradoContext ctx = DoradoContext.getCurrent();//�����˼���
				HttpServletRequest request = request = ctx.getRequest();//���󼶱�
				request.getSession().setAttribute("user", employee);//�ػ�����  �ŵ�session��
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	//ע����¼
	@Expose
	public Map doLogout(){
		//����userΪnull
		DoradoContext ctx = DoradoContext.getCurrent();
		HttpServletRequest request = ctx.getRequest();
		request.getSession().setAttribute("user", null);
		//���ص���¼ҳ��
		Map result = new HashMap();
		result.put("url", "com.bstek.dorado.sample.standardlesson.junior.Login.d");
		result.put("result", true);
		return result;
	}
}
