package in.nareshit.raghu.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class StudentListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution je) {
		System.out.println("before job:"+je.getStatus());
	}
	
	@Override
	public void afterJob(JobExecution je) {
		System.out.println("after job:"+je.getStatus());
		
	}
}
