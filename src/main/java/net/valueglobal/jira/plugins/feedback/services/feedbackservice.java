package net.valueglobal.jira.plugins.feedback.services;

import java.util.List;

import net.valueglobal.jira.plugins.feedback.model.ProjectModel;

public interface feedbackservice {

	//ProjectModel saveProject(ProjectModel project, String keyFilter);

	ProjectModel getTemplateById(String templateId);
	ProjectModel getProjectById(String projectId);

	ProjectModel saveTemplate(ProjectModel template);
	ProjectModel saveProject(ProjectModel project);
	
	ProjectModel saveProject1(ProjectModel project,String prj);

	
	void removeTemplate(ProjectModel template);
	void removeProject(ProjectModel project);
	
	
	List<ProjectModel> getAllTemplates();
	
	
	List<ProjectModel> getAllProjects();
	
	
	//List<ProjectModel> getAllProjects(String keyFilter);


	
	ProjectModel saveTemplate(String key, ProjectModel template);
	ProjectModel saveProject(String key, ProjectModel project);

	
	//List<ProjectModel> getProjectsByProjectKey(String projectKey);
	
	//List<ProjectModel> refreshTemplates(String projectKey);
	//ProjectModel getProjectById(String key);
	
	List<ProjectModel> getProjects(String keyFilter);
	//ProjectModel enableProject(String key, ProjectModel project);
	
 //ProjectModel projectKey(String pkey);
	
}
