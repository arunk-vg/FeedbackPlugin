package net.valueglobal.jira.plugins.feedback.dao;

import java.util.List;

//import net.valueglobal.jira.plugins.feedback.model.ProjectModel;
import net.valueglobal.jira.plugins.feedback.model.ProjectModel; 

public interface ProjectDao {
	
	
	//ProjectModel save(ProjectModel project,String keyFilter);

	
	ProjectModel get(int projectId);
	List<ProjectModel> getAll();
	
	//List<ProjectModel> getAll(final String projectKey);
	 ProjectModel save1(ProjectModel project, String pkey) ;

	
	ProjectModel save(ProjectModel project);
	void remove(int projectId);	
	List<ProjectModel> getByProjectKey(String projectKey);
	
	
	/*List<ProjectModel> getEnabledByProjectKey(String projectKey);
	List<ProjectModel> getEnabledByProjectKey(String projectKey, int templateId);*/
	//List<ProjectModel> getByProjectKey(String projectKey, int templateid);

}
