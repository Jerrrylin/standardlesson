package com.bstek.dorado.sample.standardlesson.service;

import java.util.Collection;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.map.Map;
import com.bstek.dorado.sample.standardlesson.dao.SlDeptDao;
import com.bstek.dorado.sample.standardlesson.entity.SlDept;
import com.bstek.dorado.sample.standardlesson.entity.SlEmployee;

@Component
public class DeptService {

	@Resource
	private SlDeptDao slDeptDao;
	
	@DataProvider
	public Collection<SlDept> getTopDept(){
		//返回slDept表示上级部门，上级部门没有id就表示没有上级部门
		return slDeptDao.find("from SlDept where slDept.deptId is null");
	}
	
	@DataProvider
	public Collection<SlDept> getDeptByParentId(Integer parentId){
		HashMap resultMap  =new HashMap();
		if(null!=parentId){
			String hql = "from SlDept where slDept.deptId =:deptId";
			resultMap.put("deptId", parentId);
			return slDeptDao.find(hql, resultMap);
		}else{
			return null;
		}
	}
	
	@DataResolver
	@Transactional
	public void saveAll(Collection<SlDept> depts){
		for(SlDept dept:depts){
			slDeptDao.persistEntity(dept);
			Collection<SlDept>childs = dept.getSlDeptSet();
			if(null!=childs){
				for (SlDept child : childs) {
					//维护关联关系
					child.setSlDept(dept);
				}
				slDeptDao.persistEntities(childs);
				saveAll(childs);
			}
		}
	}
	
}
