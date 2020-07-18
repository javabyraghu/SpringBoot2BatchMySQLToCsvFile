package in.nareshit.raghu.processor;

import org.springframework.batch.item.ItemProcessor;

import in.nareshit.raghu.model.Student;

public class StudentProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item)
			throws Exception {
		// TODO : logics --TASK
		return item;
	}
}
