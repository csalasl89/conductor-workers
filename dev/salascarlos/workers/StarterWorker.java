import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import dev.salascarlos.utils.*;

/**
 * The Class StarterWorker.
 * 
 * Worker Class designed to implement a call to a Microservice based on an input parameter 
 * 
 * @author Carlos Salas
 * @since 1.0
 * @version 1.0
 */
public class StarterWorker implements Worker {
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(TemplateWorker.class);
	
	/** The task definition name, present in the Workflow Definition. */
	private String taskDefName;
	
	/**
	 * Instantiates a new worker.
	 *
	 * @param taskDefName the task def name
	 */
	public TemplateWorker(String taskDefName) {
		this.taskDefName = taskDefName;
	}

	/* (non-Javadoc)
	 * @see com.netflix.conductor.client.worker.Worker#getTaskDefName()
	 */
	@Override
	public String getTaskDefName() {
		return taskDefName;
	}

	/* (non-Javadoc)
	 * @see com.netflix.conductor.client.worker.Worker#execute(com.netflix.conductor.common.metadata.tasks.Task)
	 */
	@Override
	public TaskResult execute(Task task) {
		
		logger.info("Executing {}.", taskDefName);
		
		TaskResult result = new TaskResult(task);
		
		result.setStatus(Status.COMPLETED);
		
		// Configuring some flow cases 
		if ( "get_starting_params".compareTo( task.getReferenceTaskName() ) == 0 ) {
			processGetStartingParams( task, result );
	    } 
			
		return result;
		
	}
	
	/**
	 * Process get_starting_params.
	 *
	 * @param task the task called from Conductor
	 * @param result the result to return to Conductor
	 */
	private void processGetStartingParams(Task task,TaskResult result) {
		
		String confStarter = Utils.getStarter((String)task.getInputData().get("start_id"));
		
		logger.info("-----\n");
		logger.info("Running task: "+task.getTaskDefName());
		logger.info("Input: ");
		logger.info("Starter Param:   {}", (String) task.getInputData().get("start_id") );
		logger.info("Output: ");
		logger.info("Starter Configuration: {}", confStarter );
		logger.info("-----\n");
		
		//Register the output of the task
		result.getOutputData().put("conf_starter", confStarter);
	}
	

}
