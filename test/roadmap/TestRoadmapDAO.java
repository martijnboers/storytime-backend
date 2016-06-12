package roadmap;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.RoadmapDAO;
import dao.UserDAO;
import model.category.Category;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;
import model.roadmap.Step;
import model.user.Child;
import model.user.Mentor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRoadmapDAO {
	private Mentor mentor;
	private Child child;
	private Roadmap roadmap;
	private Step step1, step2;
	private Achievement achievement;
	private Category category;
	private RoadmapDAO roadmapDAO = new RoadmapDAO();
	private UserDAO userDAO = new UserDAO();
	
	@Before
	public void initObjects(){
		roadmap = new Roadmap("JRoadmapName", "JRoadmapDescription");
		roadmap.setMentor(mentor);
		roadmap.setId(roadmapDAO.getLatestIdRoadmap());
		
		mentor = new Mentor("email@email.com", "username", "password", "picture", "naam");
		mentor.setMentorId(userDAO.getLatestIdMentor());
		roadmap.setMentor(mentor);

		child = new Child();
		child.setChildId(11);
		mentor.addChild(child);
		
		category = new Category("JUnitTest");
		category.setCategoryId(1);
		roadmap.addCategory(category);
		
		step1 = new Step(1, "JStep1", "JStepDescription1");
		step2 = new Step(2, "JStep2", "JStepDescription1");
		roadmap.addStep(step1);
		roadmap.addStep(step2);
		
		achievement = new Achievement(1, "JAchievment", 25.5);
		roadmap.setAchievement(achievement);
	}
	
	@Test
	public void testGetAllRoadmaps() {		
		List<Roadmap> theRoadmaps = roadmapDAO.getAllRoadmaps();
		assertTrue(!theRoadmaps.isEmpty() && theRoadmaps.size() > 0);
	}
	
	@Test
	public void testGetAllRoadmapsByCategory() {	
		List<Roadmap> theRoadmaps = roadmapDAO.getAllRoadmapsByCategory(category);
		assertTrue(!theRoadmaps.isEmpty() && theRoadmaps.size() > 0);
	}
	
	@Test
	public void testGetAllRoadmapsByMentor() {		
		List<Roadmap> theRoadmaps = roadmapDAO.getAllRoadmapsByMentor(mentor);
		assertTrue(!theRoadmaps.isEmpty() && theRoadmaps.size() > 0);
	}
	
	@Test
	public void testxGetAllRoadmapsByChild() {
		List<Roadmap> theRoadmaps = roadmapDAO.getAllRoadmapsByChild(child);
		assertTrue(!theRoadmaps.isEmpty() && theRoadmaps.size() > 0);
	}	
	
	@Test
	public void testAddRoadmap() {
		assertTrue(roadmapDAO.addRoadmap(roadmap));
	}
	
	@Test
	public void testaddRoadmapHasChild() {
		assertTrue(roadmapDAO.addRoadmapHasChild(roadmap, child));
	}
	
	@Test
	public void testaddRoadmapHasCategory() {
		assertTrue(roadmapDAO.addRoadmapHasCategory(roadmap, category));
	}
	
	@Test
	public void testyUpdateRoadmap() {
		step1.setName("JUpdate");
		roadmap.setDescription("JRoadmapDescriptionUpdate");
		roadmap.setId(1);
		assertTrue(roadmapDAO.updateRoadmap(roadmap));
	}
	
	@Test
	public void testzDeleteRoadmap(){
		assertTrue(roadmapDAO.deleteRoadmap(roadmap));
	}
}