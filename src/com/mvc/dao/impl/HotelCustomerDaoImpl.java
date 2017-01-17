package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.HotelCustomerDao;

/**
 * 酒店对客服务信息统计
 * @author wanghuimin
 * @date 2017年1月10日
 */
@Repository("HotelCustomerDaoImpl")
public class HotelCustomerDaoImpl implements HotelCustomerDao{
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findHotelService(Map<String, Object> map) {
		String starttime = null;
		String endtime = null;
		if ((String) map.get("start_Time")!=null) {
			starttime=(String) map.get("start_Time");//开始时间
		}
		if ((String) map.get("end_Time")!=null) {
			endtime=(String) map.get("end_Time");//结束时间
		}
		
		if ((String) map.get("start_time")!=null) {
			starttime=(String) map.get("start_time");//开始时间
		}
		if ((String) map.get("end_time")!=null) {
			endtime=(String) map.get("end_time");//结束时间
		}
	
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();		
		sql.append("select * from (select d.department_name,count(d.department_name) depart,"
				+ " coalesce(sum(aa.time_out_flag),0.00) timeout,coalesce(sum(aa.use_time),0.00) amount_time "
				+ " from (select ca.case_id,ca.close_time,ca.depart_id,ca.use_time,cs.service_sort,ca.time_out_flag "
				+ " from case_info ca left join call_info cs on ca.call_id=cs.call_id "
				+ " where cs.customer_service_flag=1 GROUP by ca.case_id) as aa "
				+ " left join department_info d on aa.depart_id=d.department_id "
				+ " where aa.close_time between '"+ starttime +"' and '"+ endtime +"' group by d.department_name) as tmp "
				+ " where tmp.department_name is not null ");

		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}
	/*
	 * ***********************************王慧敏报表1*******************************
	 */
	//查询部门对客服务工作量统计
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findDepartmentLoad(Map<String, Object> map) {
		String starttime = null;
		String endtime= null;
		String depart= null;
		
		if ((String) map.get("start_time")!=null) {
			starttime=(String) map.get("start_time");//开始时间
		}
		if ((String) map.get("end_time")!=null) {
			endtime=(String) map.get("end_time");//结束时间
		}
		if ((String) map.get("depart")!=null) {
			depart=(String) map.get("depart");//部门
		}
		
		if ((String) map.get("start_Time")!=null) {
			starttime=(String) map.get("start_Time");//开始时间
		}
		if ((String) map.get("end_Time")!=null) {
			endtime=(String) map.get("end_Time");//结束时间
		}
		if ((String) map.get("depart_Id")!=null) {
			depart=(String) map.get("depart_Id");//部门
		}
		
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();			
		sql.append("select aa.author_name,st.Staff_no,count(aa.author_name) amount,coalesce(sum(aa.time_out_flag),0.00) timeout,coalesce(sum(aa.use_time),0.00) amount_time,aa.department_name "
				+ "from "
				+ "(select * from((select ca.case_id,ca.author_name,ca.close_time,ca.depart_id,ca.use_time,cs.service_sort,ca.time_out_flag,ca.case_author from case_info ca "
				+ "left join call_info  cs on ca.call_id=cs.call_id where cs.customer_service_flag=1 and ca.depart_id='"+ depart +"') as a "
				+ "left join department_info d on a.depart_id=d.department_id ) ) as aa  "
				+ "left join staff_info st on aa.case_author=st.Staff_id "
				+ "where aa.close_time between '"+ starttime +"' and '"+ endtime +"'and st.Staff_no is not null group by st.Staff_no ");
		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}
	
	
	/*
	 * ***********************************王慧敏报表2*******************************
	 */
	//查询部门对客服务类型统计
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findRoomType(Map<String, Object> map) {
		String starttime = null;
		String endtime= null;
		String depart= null;
		
		if ((String) map.get("start_time")!=null) {
			starttime=(String) map.get("start_time");//开始时间
		}
		if ((String) map.get("end_time")!=null) {
			endtime=(String) map.get("end_time");//结束时间
		}
		if ((String) map.get("depart")!=null) {
			depart=(String) map.get("depart");//部门
		}
		
		if ((String) map.get("start_Time")!=null) {
			starttime=(String) map.get("start_Time");//开始时间
		}
		if ((String) map.get("end_Time")!=null) {
			endtime=(String) map.get("end_Time");//结束时间
		}
		if ((String) map.get("depart_Id")!=null) {
			depart=(String) map.get("depart_Id");//部门
		}
		
		EntityManager em=emf.createEntityManager();
		StringBuilder sql=new StringBuilder();
		String sql0="(select ca.case_id,ca.close_time,ca.given_time,ca.depart_id,ca.use_time,cs.service_sort,ca.time_out_flag "
				+ " from case_info ca left join call_info  cs on ca.call_id=cs.call_id "
				+ " where cs.customer_service_flag=1 ) as aa ";
		
		String sql00=" select aa.service_sort,count(aa.service_sort) amount,aa.given_time,coalesce(sum(aa.use_time),0.00) amount_time,coalesce(sum(aa.time_out_flag),0.00) timeout,d.department_name from "+ sql0 +" left join department_info d on aa.depart_id=d.department_id where aa.depart_id='"+ depart +"' and aa.close_time between '"+ starttime +"' and '"+ endtime +"' group by aa.service_sort ";

		sql.append(sql00);
		
		Query query=em.createNativeQuery(sql.toString());
		List<Object> list=query.getResultList();
		em.close();
		return list;
	}
	/*
	 * ***********************************王慧敏报表需求*******************************
	 */
	//根据部门ID筛选员工信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findStaffByDepId(String departid) {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select s.Staff_id,s.Department_id,ss.Staff_name "
				+ "from staff_dep_rela s left join staff_info ss on s.Staff_id=ss.Staff_id where s.Department_id=:department  ";
		Query query = em.createNativeQuery(selectSql);
		query.setParameter("department", departid);
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

}
