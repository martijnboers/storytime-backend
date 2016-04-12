package users;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UserDAO;
import model.user.Child;
import model.user.Mentor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserDAONoTravis {
	private Mentor mentor;
	private Child child;
	private UserDAO uDAO;
	
	@Before
	public void initObjects() {
		uDAO = new UserDAO();		
	}
	
	@Test
	public void testbAddMentor() throws Exception {
		uDAO = new UserDAO();// Add child and mentor for deleting
		mentor = new Mentor("testedmentor@test.nl", "testMentor", "plainPassword", null, "Mentor naam");
		assertTrue(uDAO.addMentor(mentor));
	}
	
	@Test
	public void testcUserExists() throws Exception {
		assertTrue(uDAO.userExists("testMentor"));
	}
	
	@Test
	public void testdAddChild() throws Exception {
		uDAO = new UserDAO();
		
		child = new Child();
		child.setDateOfBirth(new Date());
		child.setGender("m");
		child.setUsername("testChild");
		child.setPassword("plainPassword");
		child.setName("kind naam");
		
		assertTrue(uDAO.addChild(mentor, child));
	}
	
	@Test
	public void testeDeleteChild() throws Exception {
		uDAO = new UserDAO();
		assertTrue(uDAO.deleteChild(uDAO.getLatestIdChild()));
	}
	
	@Test
	public void testfDeleteMentor() throws Exception {
		uDAO = new UserDAO();
		assertTrue(uDAO.deleteMentor(uDAO.getLatestIdMentor()));
	}
}