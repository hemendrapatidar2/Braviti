package com.tm.braveti.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tm.braveti.model.UserDTO;
import com.tm.braveti.util.HibernateUtil;

public class UserServiceImpl implements UserServiceI {

	/*
	 * @This Method save all register users information
	 */
	@Override
	public void save(UserDTO dto) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(dto);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(UserDTO dto) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List search(UserDTO dto) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(UserDTO.class);
		List<UserDTO> list = criteria.list();
		System.out.println("List Size " + list.size());
		return null;
	}

	@Override
	public UserDTO get(UserDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		UserServiceImpl impl = new UserServiceImpl();
		try {
			impl.search(new UserDTO());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
