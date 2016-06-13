package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logging.Level;
import model.category.Category;
import model.roadmap.Achievement;
import model.roadmap.Roadmap;
import model.roadmap.Step;
import model.user.Child;
import model.user.Mentor;

public class RoadmapDAO extends DataAccesObject {
    private PreparedStatement statement;

    public RoadmapDAO() {
        super();
    }

    public Roadmap getRoadmapById(int id) {
        Roadmap roadmap = null;
        try {
            statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name, Roadmap.description, Roadmap.mentor_id, Roadmap.achievement_id FROM Roadmap WHERE Roadmap.roadmap_id = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("name"), result.getString("description"));

                CategoryDAO categoryDAO = new CategoryDAO();
                for (Category category : categoryDAO.getCategoriesByRoadmap(roadmap)) {
                    roadmap.addCategory(category);
                }

                for (Step step : getAllStepByRoadmap(roadmap)) {
                    roadmap.addStep(step);
                }

                AchievementDAO achievementDAO = new AchievementDAO();
                roadmap.setAchievement(achievementDAO.getAchievementsById(result.getInt("achievement_id")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return roadmap;
    }

    public List<Roadmap> getAllRoadmaps() {
        List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
        try {
            statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name, Roadmap.description, Roadmap.mentor_id, Roadmap.achievement_id FROM Roadmap");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("name"), result.getString("description"));
                if (!theRoadmaps.contains(roadmap)) {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    for (Category category : categoryDAO.getCategoriesByRoadmap(roadmap)) {
                        roadmap.addCategory(category);
                    }

                    for (Step step : getAllStepByRoadmap(roadmap)) {
                        roadmap.addStep(step);
                    }

                    AchievementDAO achievementDAO = new AchievementDAO();
                    roadmap.setAchievement(achievementDAO.getAchievementsById(result.getInt("achievement_id")));

                    theRoadmaps.add(roadmap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return theRoadmaps;
    }

    public List<Roadmap> getAllRoadmapsByMentor(Mentor mentor) {
        List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
        try {
            statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.order_id as orderID, Step.name as stepName, Step.description as stepDescription, Achievement.achievement_id, Achievement.name as achievementName, Achievement.points "
                    + "FROM Roadmap "
                    + "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id "
                    + "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id "
                    + "WHERE Roadmap.mentor_id = ?;");
            statement.setInt(1, mentor.getMentorId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"));
                if (!theRoadmaps.contains(roadmap)) {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    for (Category category : categoryDAO.getCategoriesByRoadmap(roadmap)) {
                        roadmap.addCategory(category);
                    }

                    for (Step step : getAllStepByRoadmap(roadmap)) {
                        roadmap.addStep(step);
                    }

                    AchievementDAO achievementDAO = new AchievementDAO();
                    roadmap.setAchievement(achievementDAO.getAchievementsById(result.getInt("achievement_id")));

                    theRoadmaps.add(roadmap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return theRoadmaps;
    }

    public List<Roadmap> getAllRoadmapsByChild(Child child) {
        List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
        try {
            statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.order_id as orderID, Step.name as stepName, Step.description as stepDescription, Achievement.achievement_id, Achievement.name as achievementName, Achievement.points "
                    + "FROM Roadmap "
                    + "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id "
                    + "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id "
                    + "JOIN Child_has_Roadmap ON Roadmap.roadmap_id = Child_has_Roadmap.roadmap_id "
                    + "WHERE Child_has_Roadmap.child_id = ?");
            statement.setInt(1, child.getChildId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"));
                if (!theRoadmaps.contains(roadmap)) {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    for (Category category : categoryDAO.getCategoriesByRoadmap(roadmap)) {
                        roadmap.addCategory(category);
                    }

                    for (Step step : getAllStepByRoadmap(roadmap)) {
                        roadmap.addStep(step);
                    }

                    AchievementDAO achievementDAO = new AchievementDAO();
                    roadmap.setAchievement(achievementDAO.getAchievementsById(result.getInt("achievement_id")));

                    theRoadmaps.add(roadmap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return theRoadmaps;
    }

    public List<Roadmap> getAllRoadmapsByCategory(Category category) {
        List<Roadmap> theRoadmaps = new ArrayList<Roadmap>();
        try {
            statement = con.prepareStatement("SELECT Roadmap.roadmap_id, Roadmap.name AS roadmapName, Roadmap.description AS roadmapDescription, Step.step_id, Step.order_id as orderID, Step.name as stepName, Step.description as stepDescription, Achievement.achievement_id, Achievement.name as achievementName, Achievement.points "
                    + "FROM Roadmap "
                    + "JOIN Step ON Roadmap.roadmap_id = Step.roadmap_id "
                    + "JOIN Achievement ON Roadmap.achievement_id = Achievement.achievement_id "
                    + "JOIN Category_has_Roadmap ON Category_has_Roadmap.roadmap_id = Roadmap.roadmap_id "
                    + "WHERE Category_has_Roadmap.category_id = ?");
            statement.setInt(1, category.getCategoryId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Roadmap roadmap = new Roadmap(result.getInt("roadmap_id"), result.getString("roadmapName"), result.getString("roadmapDescription"));
                if (!theRoadmaps.contains(roadmap)) {
                    Achievement achievement = new Achievement(result.getInt("achievement_id"), result.getString("achievementName"), result.getDouble("points"));
                    roadmap.setAchievement(achievement);
                    Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"));
                    roadmap.addStep(step);
                    theRoadmaps.add(roadmap);
                } else {
                    for (Roadmap r : theRoadmaps) {
                        Step step = new Step(result.getInt("step_id"), result.getInt("orderID"), result.getString("stepName"), result.getString("stepDescription"));
                        r.getSteps().add(step);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return theRoadmaps;
    }

    public boolean addRoadmap(Roadmap roadmap) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("INSERT INTO Roadmap (`name`, `description`, `mentor_id`, `achievement_id`) VALUES (?, ?, ?, ?);");
            statement.setString(1, roadmap.getName());
            statement.setString(2, roadmap.getDescription());
            statement.setInt(3, roadmap.getMentor().getMentorId());
            statement.setInt(4, roadmap.getAchievement().getId());

            if (statement.executeUpdate() > 0) {
                for (Step step : roadmap.getSteps()) {
                    addStep(step, roadmap);
                }
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Adding Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    public boolean updateRoadmap(Roadmap roadmap) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("UPDATE Roadmap SET `name` = ?, `description` = ?, `mentor_id` = ?, `achievement_id` = ? WHERE Roadmap.roadmap_id = ?;");
            statement.setInt(5, roadmap.getId());
            statement.setString(1, roadmap.getName());
            statement.setString(2, roadmap.getDescription());
            statement.setInt(3, roadmap.getMentor().getMentorId());
            statement.setInt(4, roadmap.getAchievement().getId());

            if (statement.executeUpdate() > 0) {
                for (Step step : roadmap.getSteps()) {
                    updateStep(step);
                }
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Updating Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    public boolean deleteRoadmap(Roadmap roadmap) {
        boolean succes = false;
        try {
            if (deleteChildHasRoadmap(roadmap) && deleteCategoryHasRoadmap(roadmap)) {
                for (Step step : roadmap.getSteps()) {
                    deleteStep(step);
                }

                statement = con.prepareStatement("DELETE FROM Roadmap WHERE Roadmap.roadmap_id = ?");
                statement.setInt(1, roadmap.getId());
                if (statement.executeUpdate() > 0) {
                    succes = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Deleting Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    public boolean addRoadmapHasChild(Roadmap roadmap, Child child) {
        boolean succes = false;
        try {
            System.out.println(roadmap.getId() + " - " + child.getChildId());
            statement = con.prepareStatement("INSERT INTO `storytime`.`Child_has_Roadmap` (`child_id`, `roadmap_id`) VALUES (?, ?);");
            statement.setInt(1, child.getChildId());
            statement.setInt(2, roadmap.getId());

            if (statement.executeUpdate() > 0) {
                for (Step step : roadmap.getSteps()) {
                    addStepHasChild(step, child);
                }
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Adding Child_has_Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    public boolean addRoadmapHasCategory(Roadmap roadmap, Category category) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("INSERT INTO `storytime`.`Category_has_Roadmap` (`category_id`, `roadmap_id`) VALUES (?, ?);");
            statement.setInt(1, category.getCategoryId());
            statement.setInt(2, roadmap.getId());

            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Adding Category_has_Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    /**
     * @param roadmap
     * @return True is a Child_has_Roadmap is deleted, false is something failed.
     */
    private boolean deleteChildHasRoadmap(Roadmap roadmap) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("DELETE FROM `storytime`.`Child_has_Roadmap` WHERE `Child_has_Roadmap`.`roadmap_id` = ?");
            statement.setInt(1, roadmap.getId());
            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Deleting Child_has_Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    /**
     * @param roadmap
     * @return True is a Category_has_Roadmap is deleted, false is something failed.
     */
    private boolean deleteCategoryHasRoadmap(Roadmap roadmap) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("DELETE FROM `storytime`.`Category_has_Roadmap` WHERE `Category_has_Roadmap`.`roadmap_id` = ?");
            statement.setInt(1, roadmap.getId());
            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Deleting Category_has_Roadmap went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    /**
     * @param roadmap_id
     * @return Returns a list with all the steps of a roadmap.
     */
    private List<Step> getAllStepByRoadmap(Roadmap roadmap) {
        List<Step> theSteps = new ArrayList<Step>();

        try {
            statement = con.prepareStatement("SELECT * FROM `Step` WHERE Step.roadmap_id = ? ORDER BY order_id ASC");
            statement.setInt(1, roadmap.getId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Step Step = new Step(result.getInt("step_id"), result.getInt("order_id"), result.getString("name"), result.getString("description"));
                if (!theSteps.contains(Step)) {
                    theSteps.add(Step);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return theSteps;
    }

    private boolean addStep(Step step, Roadmap roadmap) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("INSERT INTO `storytime`.`Step` (`step_id`, `order_id`, `name`, `description`, `roadmap_id`) VALUES (NULL, ?, ?, ?, ?);");
            statement.setInt(1, step.getOrderID());
            statement.setString(2, step.getName());
            statement.setString(3, step.getDescription());
            statement.setInt(4, roadmap.getId());

            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Adding Step went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    private boolean updateStep(Step step) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("UPDATE `storytime`.`Step` SET `order_id` = ?, `name` = ?, `description` = ? WHERE `Step`.`step_id` = ?;");
            statement.setInt(4, step.getId());
            statement.setInt(1, step.getOrderID());
            statement.setString(2, step.getName());
            statement.setString(3, step.getDescription());

            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Updating Step went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    /**
     * @param id
     * @return True is a step is deleted, false is something failed.
     */
    public boolean deleteStep(Step step) {
        boolean succes = false;
        try {
            if (deleteStepHasChild(step)) {
                statement = con.prepareStatement("DELETE FROM `storytime`.`Step` WHERE `Step`.`step_id` = ?");
                statement.setInt(1, step.getId());
                if (statement.executeUpdate() > 0) {
                    succes = true;
                }
            } else {
                succes = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Deleting Step went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    private boolean addStepHasChild(Step step, Child child) {
        boolean succes = false;
        try {
            System.out.println(step.getId() + " - " + child.getChildId());
            statement = con.prepareStatement("INSERT INTO `storytime`.`Step_has_Child` (`step_id`, `child_id`, `completed`) VALUES (?, ?, NULL);");
            statement.setInt(1, step.getId());
            statement.setInt(2, child.getChildId());
            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Adding Step_has_Child went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    /**
     * @param step
     * @return True is a Step_has_Child is deleted, false is something failed.
     */
    private boolean deleteStepHasChild(Step step) {
        boolean succes = false;
        try {
            statement = con.prepareStatement("DELETE FROM `storytime`.`Step_has_Child` WHERE `Step_has_Child`.`step_id` = ?");
            statement.setInt(1, step.getId());
            if (statement.executeUpdate() > 0) {
                succes = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.out(Level.ERROR, "", "Deleting Step_has_Child went wrong");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.out(Level.ERROR, "", "Statement isn't closed");
                e.printStackTrace();
            }
        }
        return succes;
    }

    public ArrayList<Roadmap> getSuggestedRoadmap(String[] keywords) throws SQLException {
        ArrayList<Roadmap> roadmaps = new ArrayList<>();

        for (String keyword : keywords) {
            if (keyword.length() > 3) {
                try {
                    statement = con.prepareStatement("SELECT roadmap_id FROM `Roadmap` WHERE `name` LIKE ? OR `description` LIKE ? LIMIT 0 , 6");

                    statement.setString(1, "%" + keyword + "%");
                    statement.setString(2, "%" + keyword + "%");

                    ResultSet result = statement.executeQuery();
                    while (result.next()) {
                        roadmaps.add(getRoadmapById(result.getInt("roadmap_id")));
                    }

                } catch (SQLException e) {
                    statement.close();
                    throw new SQLException("Iets misgegaan met het ophalen van suggesties");
                }
            }
        }
        return roadmaps;
    }

    // For testing purpose
    public int getLatestIdRoadmap() {
        int questionId = 0;
        try {
            statement = con.prepareStatement("SELECT MAX(roadmap_id) FROM Roadmap");
            ResultSet result = statement.executeQuery();
            result.next();
            questionId = result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return questionId;
    }
}