package net.valueglobal.jira.plugins.feedback.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.valueglobal.jira.plugins.feedback.ao.ProjectMapping;
import net.valueglobal.jira.plugins.feedback.dao.ProjectDao;
//import net.valueglobal.jira.plugins.feedback.dao.feedbackDao;
import net.valueglobal.jira.plugins.feedback.model.ProjectModel;
import net.valueglobal.jira.plugins.feedback.services.feedbackservice;

import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
public class Defaultfeedbackservice implements feedbackservice  {

	
	//private final Logger LOGGER = LoggerFactory.getLogger(feedbackservice.class);

	private final ProjectDao projectDao;
	//private final feedbackDao templateDao;
	
	
	

	
/*	private final Function<ProjectModel, Template> projectToTemplateFunction = new Function<ProjectModel, Template>() {
		@Override
		public Template apply(@Nullable ProjectModel from) {
			if (from == null) {
				return null;
			}
			Template template=getTemplateById(from.getId());
			return template;
		}
	};*/
	
	public Defaultfeedbackservice(ProjectDao projectDao) {
		super();
		this.projectDao = projectDao;

	}

	
	public ProjectModel getTemplateById(int templateId) {
		return projectDao.get(templateId);
	}	
	
	@Override
	public ProjectModel getTemplateById(String templateIdStr) {
		int templateId = Integer.parseInt(templateIdStr);
		return getTemplateById(templateId);
	}

	
	public ProjectModel getProjectById(int projectId) {
		
	return projectDao.get(projectId);
		}

	
	@Override
	public ProjectModel getProjectById(String key) {
		int projectId = Integer.parseInt(key);
		return getProjectById(projectId);

	}
	

	
	
	@Override
	public ProjectModel saveTemplate(ProjectModel template) {
		return projectDao.save(template);
	}

	public ProjectModel saveProject1(ProjectModel project, String prj) {		
		//projectKey(pkey);
		return projectDao.save1(project,prj);
	}

	@Override
	public ProjectModel saveProject(ProjectModel project) {		
		//projectKey(pkey);
		return projectDao.save(project);
	}

	
	
	
	
	@Override
	public void removeTemplate(ProjectModel template) {
		projectDao.remove(template.getId());
	}
	
	@Override
	public void removeProject(ProjectModel project) {
		projectDao.remove(project.getId());
	}


	@Override
	public List<ProjectModel> getAllTemplates() {
		return projectDao.getAll();
	}

	@Override
	public List<ProjectModel> getAllProjects() {
		return projectDao.getAll();
		}

/*	@Override
	public List<ProjectModel> getAllProjects(String keyFilter) {
		return projectDao.getAll(keyFilter);
		}*/
	
	
	@Override
	public ProjectModel saveTemplate(String key, ProjectModel modified) {
		ProjectModel orginal = getTemplateById(key);
		

		if (modified.getProjectKey() !=null )				
		orginal.setProjectKey(modified.getProjectKey());
		
		if (modified.getQuestion() !=null )				
			orginal.setQuestion(modified.getQuestion());
		
		if (modified.getWeightage() !=  null )				
			orginal.setWeightage(modified.getWeightage());
		
		return saveTemplate(orginal);
	}
	@Override
	public ProjectModel saveProject(String key, ProjectModel modified) {
		
		ProjectModel orginal = getProjectById(key);
	
			if (modified.getProjectKey() !=null )				
			orginal.setProjectKey(modified.getProjectKey());
			
			if (modified.getQuestion() !=null )				
				orginal.setQuestion(modified.getQuestion());
			
			if (modified.getWeightage() !=  null )				
				orginal.setWeightage(modified.getWeightage());
			
		return saveProject(orginal);
	}

	

	
	
	@Override
	public List<ProjectModel> getProjects(String projectKey) {
		return projectDao.getByProjectKey(projectKey);
			
	}


/*	@Override
	public ProjectModel saveProject(ProjectModel project, String prj) {
		
		project.setProjectKey(prj);
		
		return projectDao.save(project);
	}*/
	
	
	/*@Override
	public List<ProjectModel> getProjectsByProjectKey(String projectKey) {
		
		List<ProjectModel> tempaltes= getAllProjects();
		List<ProjectModel> projects = projectDao.getByProjectKey(projectKey);
		List<ProjectModel> projectTemplates = new ArrayList<ProjectModel>() ;
		
		
		for ( ProjectModel p : projects)
		{
			projectTemplates.add(getProjectById(p.getProjectKey()));
		}
		
		for (ProjectModel temp : tempaltes) {
			
			if (!projectTemplates.contains(temp) )
			{
				ProjectModel save=new ProjectModel();
				save.setProjectKey(projectKey);
				//save.setTemplateId(temp.getId());
				projectDao.save(save);
			}
			
		}
		
		for (ProjectModel temp : projectTemplates) {
			
			if (!projectTemplates.contains(temp) )
			{
				List<ProjectModel> save= getProjectByTemplate(projectKey, temp.getId());
				for (ProjectModel p : save)
				{
					projectDao.remove(p.getId());
				}
			}
			
		}
		
		return Lists.newArrayList(Iterables.filter(Lists.transform(projectDao.getByProjectKey(projectKey), new Function<ProjectModel, ProjectModel>() {
			@Override
			public ProjectModel apply(@Nullable ProjectModel from) {
				if (from != null && validateAndEnrichProject(from)) {
					return from;
				} else {
					return null;
				}
			}
		}), Predicates.notNull()));
	}

	*/
	/*private List<ProjectModel> getProjectByTemplate(String projectKey, int templateId) {
		return projectDao.getByProjectKey(projectKey, templateId);
	}*/

	

/*
	@Override
	public void enableTemplate(int projectId, boolean enabled) {
		ProjectModel project = getProjectById(projectId);
		if (project != null) {
			project.setEnabled(enabled);
			projectDao.save(project);
		}
	}
*/
/*	@Override
	public List<ProjectModel> getEnabledTemplates(String projectKey) {
		return Lists.newArrayList(Iterables.filter(Lists.transform(projectDao.getEnabledByProjectKey( projectKey), new Function<ProjectModel, ProjectModel>() {
			@Override
			public ProjectModel apply(@Nullable ProjectModel from) {
				if (from != null && validateAndEnrichProject(from)) {
					return from;
				} else {
					return null;
				}
			}
		}), Predicates.notNull()));
	}*/


	
/*	@Override
	public List<ProjectModel> refreshTemplates(String projectKey) {
		// TODO Auto-generated method stub
		
		return getProjectsByProjectKey( projectKey);
	}*/

	



	/*@Override
	public ProjectModel enableProject(final String key, final ProjectModel project) {
		ProjectModel orginal = getProjectById(Integer.parseInt(key));
		//orginal.setEnabled(!orginal.isEnabled());
		ProjectModel saved = saveProject(orginal);
		saved = getProjectById(saved.getId());
		return saved;
		
	}*/

/*	public String pkey=null;


	@Override
	public ProjectModel projectKey(String projectkey) {
		
		pkey=projectkey;
		
		ProjectModel p= new ProjectModel();
		p.setProjectKey(pkey);
		
		return p;
	}
*/




	
	
}

