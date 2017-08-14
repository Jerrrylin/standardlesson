package com.bstek.dorado.sample.standardlesson.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * sl_dept:
 */
@Entity
@Table(name = "sl_dept")
public class SlDept implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * dept_id:
	 */
	private int deptId;

	/**
	 * dept_name:
	 */
	private String deptName;

	/**
	 * sl_dept:
	 */
	private SlDept slDept;

	/**
	 * sl_company:
	 */
	private SlCompany slCompany;

	/**
	 * sl_dept:
	 */
	private Set<SlDept> slDeptSet = new HashSet<SlDept>(0);//制定一个内容为SlDept的集合，初始化容量为0

	/**
	 * sl_employee:
	 */
	private Set<SlEmployee> slEmployeeSet = new HashSet<SlEmployee>(0);//类似 一个部门对一个多个员工

	public SlDept() {
		super();
	}

	public SlDept(int deptId, String deptName, SlDept slDept,
			SlCompany slCompany, Set<SlDept> slDeptSet,
			Set<SlEmployee> slEmployeeSet) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.slDept = slDept;
		this.slCompany = slCompany;
		this.slDeptSet = slDeptSet;
		this.slEmployeeSet = slEmployeeSet;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Id
	@Column(name = "dept_id", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getDeptId() {
		return deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "dept_name", length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setSlDept(SlDept slDept) {
		this.slDept = slDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)//懒加载方式  还有一种方式是eager类被调用就加载
	@JoinColumn(name = "parent_id")//把父类id给SlDept
	public SlDept getSlDept() {
		return slDept;
	}

	public void setSlCompany(SlCompany slCompany) {
		this.slCompany = slCompany;
	}

	@ManyToOne(fetch = FetchType.LAZY)//多个部门对应一个公司
	@JoinColumn(name = "company_id")
	public SlCompany getSlCompany() {
		return slCompany;
	}

	public void setSlDeptSet(Set<SlDept> slDeptSet) {
		this.slDeptSet = slDeptSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "slDept")//对应下级部门信息
	public Set<SlDept> getSlDeptSet() {
		return slDeptSet;
	}

	public void setSlEmployeeSet(Set<SlEmployee> slEmployeeSet) {
		this.slEmployeeSet = slEmployeeSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "slDept")//对应部门下多员工
	public Set<SlEmployee> getSlEmployeeSet() {
		return slEmployeeSet;
	}

	public String toString() {
		return "SlDept [deptId=" + deptId + ",deptName=" + deptName
				+ ",slDept=" + slDept + ",slCompany=" + slCompany
				+ ",slDeptSet=" + slDeptSet + ",slEmployeeSet=" + slEmployeeSet
				+ "]";
	}

}
