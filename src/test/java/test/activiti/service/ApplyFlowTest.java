package test.activiti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.activiti.AbstractTestBase;

public class ApplyFlowTest extends AbstractTestBase{
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	/**
	 * 部署apply流程
	 */
	@Test
	public void delpoyApplyFlow(){
		repositoryService.createDeployment().addClasspathResource("processes/apply.bpmn").deploy();
		log.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
	} 
	
	/**
	 * 启动apply流程
	 */
	@Test
	public void startApplyFlow(){
		log.info("run----");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "user");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("apply", variables);
		// Verify that we started a new process instance
		log.info(processInstance.getActivityId() + "<<<Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
	}
	
	/**
	 * 查询Apply第一步未完成
	 */
	@Test
	public void findUsertask1(){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("user").list();
		log.info("tasks.size:==>{}", tasks.size());
		for (Task task : tasks) {
			String taskId = task.getId();
			String assignee = task.getAssignee();
			String taskName = task.getName();
			log.info("taskId: {}, assignee: {}, taskName:{} ", taskId, assignee, taskName);
		}
	}
	
	@Test
	public void complateUserrask1(){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("user").taskId("32507").list();
		for (Task task : tasks) {
			String taskId = task.getId();
			String assignee = task.getAssignee();
			String taskName = task.getName();
			log.info("taskId: {}, assignee: {}, taskName:{} ", taskId, assignee, taskName);
			
			Map<String, Object> value = new HashMap<>();
			value.put("name", "xiams");
			value.put("emp", "xiams_emp");
			List<String> strList = new ArrayList<>();
			strList.add("1");
			strList.add("2");
			strList.add("2");
			strList.add("4");
			value.put("strList", strList);
			taskService.complete(taskId, value);
		}
	}
	
}
