package com.user.JunitTest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.user.service.UserService;
import com.user.model.*;;

public class TestUserDAO {

	private UserService dao;
	private List<User> userList;
	private static final int id = 1001;
	
	@Before
	public void setUp() throws Exception {
		dao = new UserService();
		userList = new ArrayList<User>();
	}
	@After
	public void tearDown() throws Exception {
		
	}
	@Test
	public void testFetchAll() throws Exception {
		userList = dao.fetchAll();
		assertNotNull(userList);
		assertEquals(userList.get(0).getFirstName(),"Nagendra");
		assertEquals(userList.get(0).getLastName(),"Bushan");
		assertEquals(userList.get(0).getMiddleName(),"");
				
	}
	
	@Test
	public void testFetchOne()throws Exception{
		
		User one = dao.fetchOne(id);
		assertEquals(one.getFirstName(),"Nagendra");
		assertEquals(one.getLastName(),"Bushan");
		assertEquals(one.getMiddleName(),"");
		
	}
	
	@Test
	public void testUpdate()throws Exception{
		
		User one = new User();
	         one.setFirstName("Nagen");
	         one = dao.update(id, one);
	         assertEquals(one.getFirstName(),"Nagen");
		
	}
	

}