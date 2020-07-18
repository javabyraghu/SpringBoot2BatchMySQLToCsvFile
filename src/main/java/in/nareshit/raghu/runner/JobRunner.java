package in.nareshit.raghu.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {
	@Autowired
	private Job job;
	
	@Autowired
	private JobLauncher launcher;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("JOB EXECUTION STARTED..");
		launcher.run(job, new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
	}

}
