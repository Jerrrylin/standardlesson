package com.bstek.dorado.sample.standardlesson.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.bstek.dorado.sample.standardlesson.dao.SlEmployeeDao;
import com.bstek.dorado.sample.standardlesson.entity.SlEmployee;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@Component
public class EmployeeService {

	@Resource
	private SlEmployeeDao employeeDao;
	@DataProvider
	public Collection<SlEmployee> getAll(){
		return employeeDao.getAll();
	}
	//分页
	@DataProvider
	public void getAllForPage(Page<SlEmployee>page){
		employeeDao.getAll(page);
	}
	//分页+查询   criteria是查询条件
	@DataProvider
	public void getAllForFilter(Page<SlEmployee>page,Criteria criteria)throws Exception{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SlEmployee.class);
//		System.err.println("detachedCriteria="+detachedCriteria);
//		System.err.println("criteria="+criteria);
		if(criteria!=null){
			employeeDao.find(page, HibernateUtils.createFilter(detachedCriteria, criteria));
		}else{
			employeeDao.getAll(page);
		}
	}
	//条件查询方法
	@DataProvider
	public void queryForCondition(Page<SlEmployee>page,java.util.Map<String,Object>params){
		
		if(null != params){
			String employeeCode = (String)params.get("employeeCode");
			String employeeName = (String) params.get("employeeName");
			String userName = (String)params.get("userName");
			String email = (String)params.get("email");
			String phone = (String)params.get("phone");
			String mobile = (String)params.get("mobile");
			String position = (String)params.get("position");
			
			String whereCase = "";
			if(StringHelper.isNotEmpty(employeeCode)){
				whereCase += " AND employeeCode like '%" + employeeCode + "%'";
			}
			
			if(StringHelper.isNotEmpty(employeeName)){
				whereCase = " AND employeeName like '%" + employeeName + "%'";
			}
			
			if(StringHelper.isNotEmpty(userName)){
				whereCase += " AND userName like  '%" + userName + "%'";
			}
			
			if(StringHelper.isNotEmpty(email)){
				whereCase += " AND email like  '%" + email + "%'";
			}
			
			if(StringHelper.isNotEmpty(phone)){
				whereCase += " AND phone like  '%" + phone + "%'";
			}
			
			if(StringHelper.isNotEmpty(mobile)){
				whereCase += " AND mobile like  '%" + mobile + "%'";
			}
			
			if(StringHelper.isNotEmpty(position)){
				whereCase += " AND position like  '%"+ position + "%'";
			}
			
			employeeDao.find(page," from SlEmployee where 1 = 1 " + whereCase);
		}else {
			employeeDao.getAll(page);
		}
	}
	
	@DataResolver
	@Transactional//事务管理
	public void saveAll(Collection<SlEmployee> slEmployees){
		employeeDao.persistEntities(slEmployees);//延迟性保存
	}
	
	@DataProvider
	public Collection<SlEmployee> getEmployeeByUserName(String userName){
		Map param = new HashMap();
		if(StringHelper.isNotEmpty(userName)){
			param.put("userName", userName);
			return 	employeeDao.find("from SlEmployee where userName = :userName", param);
		}else{
			return null;
		}
	}
	
	@DataProvider
	public void getEmployeeByDeptId(Page<SlEmployee> page, Integer deptId){
		System.out.println("deptId="+deptId);
		if(null != deptId){
			String hql = "from SlEmployee where slDept.deptId = :deptId";
			HashMap param = new HashMap();
			param.put("deptId", deptId);
			employeeDao.find(page, hql, param);
		}
	}
}
