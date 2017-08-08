package net.valueglobal.jira.plugins.feedback.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.atlassian.jira.project.browse.BrowseContext;

import net.java.ao.Query;
import net.valueglobal.jira.plugins.feedback.ao.ProjectMapping;
//import net.valueglobal.jira.plugins.feedback.ao.FeedbackMapping;
import net.valueglobal.jira.plugins.feedback.dao.ProjectDao;
import net.valueglobal.jira.plugins.feedback.model.ProjectModel;
import net.valueglobal.jira.plugins.feedback.utils.MapRemovingNullCharacterFromStringValues;

public class DefaultProjectDao implements ProjectDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProjectDao.class);
	
	private final ActiveObjects ao;
	private final Function<ProjectMapping, ProjectModel> projectMappingToProjectFunction = new Function<ProjectMapping, ProjectModel>() {
		@Override
		public ProjectModel apply(@Nullable ProjectMapping from) {
			if (from == null) {
				return null;
			}
			ProjectModel project = new ProjectModel(from.getID());
			project.setProjectKey(from.getProjectKey());
			project.setQuestion(from.getQuestion());
			project.setWeightage(from.getWeightage());		
			return project;
			
		}
	};

	public DefaultProjectDao(ActiveObjects ao) {
		this.ao = ao;
	}
	
	

	@Override
	public ProjectModel get(final int projectId) {
		ProjectMapping mapping = ao.executeInTransaction(new TransactionCallback<ProjectMapping>() {
			@Override
			public ProjectMapping doInTransaction() {
								return ao.get(ProjectMapping.class, projectId);
			}
		});
		return projectMappingToProjectFunction.apply(mapping);
	}

	
	
	@Override
	public List<ProjectModel> getAll() {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select()));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);
	}


	/*	@Override
	public List<ProjectModel> getAll(final String keyFilter) {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select().where("PROJECT_KEY = ?", keyFilter)));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);
	}*/
	
	
	public int getQuestionCount(String question){	
		 
		int entity = ao.count(ProjectMapping.class, Query.select().where("LOWER(\"QUESTION\")LIKE LOWER(?)","%" + question + "%"));		
		return entity;
	}
	
	@Override
	public ProjectModel save(final ProjectModel project) {
		LOGGER.debug("Saving {}", project.toString());
		if(getQuestionCount(project.getQuestion()) == 0 && project.getWeightage() != "")
		{
				project.setMessageList(false);
				ProjectMapping mapping = ao.executeInTransaction(new TransactionCallback<ProjectMapping>() {
				@Override
				public ProjectMapping doInTransaction() {
					ProjectMapping mapping;			
					if (project.getId() == 0) {
						//Map<String, Object> map = new MapRemovingNullCharacterFromStringValues();
						Map<String,Object> map1 = new HashMap<String,Object>();
						map1.put(ProjectMapping.PROJECT_KEY, "ADMIN");					
						map1.put(ProjectMapping.QUESTION,project.getQuestion());
						map1.put(ProjectMapping.WEIGHTAGE,project.getWeightage());
						mapping = ao.create(ProjectMapping.class, map1);
						mapping = ao.find(ProjectMapping.class, Query.select().where("ID = ?", mapping.getID()))[0];
					} else {					
						mapping = ao.get(ProjectMapping.class, project.getId());
						mapping.setProjectKey(project.getProjectKey());
						mapping.setQuestion(project.getQuestion());
						mapping.setWeightage(project.getWeightage());
						mapping.save();
					}
					return mapping;
				}
			});
				return projectMappingToProjectFunction.apply(mapping);

		}
		else if(project.getQuestion() == "")
		{
			project.setMessageList(true);
			project.setMessage("Please Enter Some Value for Question");
			return project;
		}
		else if(project.getWeightage() == "")
		{
			project.setMessageList(true);
			project.setMessage("Please Enter Some Value for Weightage");
			return project;
		}
		else
		{
			project.setMessageList(true);
			project.setMessage(project.getQuestion() +" Already Exist, Please Enter Unique value for Question");
			return project;
		}
	}

	@Override
	public ProjectModel save1(final ProjectModel project, final String pkey) {
		LOGGER.debug("Saving {}", project.toString());
		if(getQuestionCount(project.getQuestion()) == 0 && project.getWeightage() != "")
		{
				project.setMessageList(false);
			ProjectMapping mapping = ao.executeInTransaction(new TransactionCallback<ProjectMapping>() {
			@Override
			public ProjectMapping doInTransaction() {
				ProjectMapping mapping;			
				if (project.getId() == 0) {
					//Map<String, Object> map = new MapRemovingNullCharacterFromStringValues();
					Map<String,Object> map1 = new HashMap<String,Object>();				
					if(project.getProjectKey() == null)
					{
						map1.put(ProjectMapping.PROJECT_KEY, pkey);
					}
					else
					{					
						map1.put(ProjectMapping.PROJECT_KEY,project.getProjectKey());
					}					
					map1.put(ProjectMapping.QUESTION,project.getQuestion());
					map1.put(ProjectMapping.WEIGHTAGE,project.getWeightage());
					mapping = ao.create(ProjectMapping.class, map1);
					mapping = ao.find(ProjectMapping.class, Query.select().where("ID = ?", mapping.getID()))[0];
				} else {					
					mapping = ao.get(ProjectMapping.class, project.getId());
					mapping.setProjectKey(project.getProjectKey());
					mapping.setQuestion(project.getQuestion());
					mapping.setWeightage(project.getWeightage());
					mapping.save();
				}
				return mapping;
			}
		});
		return projectMappingToProjectFunction.apply(mapping);
		}
		else if(project.getQuestion() == "")
		{
			project.setMessageList(true);
			project.setMessage("Please Enter Some Value for Question");
			return project;
		}
		else if(project.getWeightage() == "")
		{
			project.setMessageList(true);
			project.setMessage("Please Enter Some Value for Weightage");
			return project;
		}
		else
		{
			project.setMessageList(true);
			project.setMessage(project.getQuestion() +" Already Exist, Please Enter Unique value for Question");
			return project;
		}
	}
	
	
	
	@Override
	public void remove(int projectId) {
		ao.delete(ao.get(ProjectMapping.class, projectId));
		
	}

	@Override
	public List<ProjectModel> getByProjectKey(final String projectKey) {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select().where("\"PROJECT_KEY\" = ?", projectKey)));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);
	}
	
	


	
	/*@Override
	public List<ProjectModel> getByProjectKey (final String projectKey, final int templateid) {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select().where("\"PROJECT_KEY\" = ? AND ID = ? ", projectKey,templateid)));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);
	}*/
	

/*	@Override
	public List<ProjectModel> getEnabledByProjectKey(final String projectKey) {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select().where("PROJECT_KEY = ? AND ENABLED =?", projectKey,Boolean.TRUE)));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);
	}

	@Override
	public List<ProjectModel> getEnabledByProjectKey(final String projectKey,final int templateId) {
		final List<ProjectMapping> mappings = ao.executeInTransaction(new TransactionCallback<List<ProjectMapping>>() {
			@Override
			public List<ProjectMapping> doInTransaction() {
				return Arrays.asList(ao.find(ProjectMapping.class, Query.select().where("\"PROJECT_KEY\" =? AND \"TEMPLATE_KEY\"=?",projectKey,templateId)));
			}
		});
		return Lists.transform(mappings, projectMappingToProjectFunction);

	}*/
}
