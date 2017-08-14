package com.bstek.dorado.sample.standardlesson.dao;

import org.springframework.stereotype.Repository;
import com.bstek.dorado.hibernate.HibernateDao;
import com.bstek.dorado.sample.standardlesson.entity.SlCompany;
@Repository//标注dao组件
public class SICompanyDao extends HibernateDao<SlCompany, Long> {
//HibernateDao已经具备基本的增删查该
}
