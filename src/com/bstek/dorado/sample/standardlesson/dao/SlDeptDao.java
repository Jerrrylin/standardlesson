package com.bstek.dorado.sample.standardlesson.dao;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.hibernate.HibernateDao;
import com.bstek.dorado.sample.standardlesson.entity.SlDept;
@Repository//表识dao组件
public class SlDeptDao extends HibernateDao<SlDept,Long > {

}
