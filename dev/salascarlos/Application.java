import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.worker.Worker;

/**
 * The Class Application.
 * 
 * Class to configure the WorkFlow example.
 * 
 * @author Carlos Salas
 * @since 1.0
 * @version 1.0
 */
public class Application {
	
	/**
	 * The main method, which will serve as our Task Listener
	 *
	 * @param args the arguments to use in this method.
	 */
	
	public static void main(String[] args) {
		
		Logger logger = 
			LoggerFactory.getLogger(Application.class);
		TaskClient taskClient = new TaskClient();
		
		// The Conductor's API URL
		taskClient.setRootURI("http://127.0.0.1:8080/api/");
    
    // The max. Number of parallel tasks the workers will be executing
		int threadCount = 2;
		
		// Configuring each Worker to execute all task of the WorkFlow
		Worker starterWorker = 
			new StarterWorker("get_starting_params");
		
		// Create WorkflowTaskCoordinator
		WorkflowTaskCoordinator.Builder builder = 
			new WorkflowTaskCoordinator.Builder();
		WorkflowTaskCoordinator coordinator = builder.
				withWorkers( starterWorker ).
				withThreadCount(threadCount).
			withTaskClient(taskClient).build();
				
		//Start for polling and execution of the tasks
		logger.info("Initiating Worker Manager...");
		coordinator.init();
	}

}
