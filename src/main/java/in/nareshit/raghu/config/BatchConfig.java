package in.nareshit.raghu.config;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import in.nareshit.raghu.listener.StudentListener;
import in.nareshit.raghu.model.Student;
import in.nareshit.raghu.processor.StudentProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	//6. JobBuilderFactory autowire
	@Autowired
	private JobBuilderFactory jf;

	//7. Job Bean
	@Bean
	public Job jobA() {
		return jf.get("jobA")
				.incrementer(new RunIdIncrementer())
				.listener(new StudentListener())
				.start(stepA())
				//.next(stepB())
				.build();
	}

	//4. StepBuilderFactory Autowire
	@Autowired
	private StepBuilderFactory sf;

	//5. Step Bean
	@Bean
	public Step stepA() {
		return sf.get("stepA")
				.<Student,Student>chunk(3)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}


	//3. Writer Bean
	@Bean
	public ItemWriter<Student> writer() {
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
		//File Location + name 
		writer.setResource(new FileSystemResource("F://batchoutput//students.csv"));
		//create one line based on one object
		writer.setLineAggregator(new DelimitedLineAggregator<Student>() {{
			setDelimiter(",");
			//read data from object variable names
			setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {{
				setNames(new String[]{"stdId","stdName","stdFee"});
			}});
		}});
		return writer;
	}

	//2. Processor Bean
	@Bean
	public ItemProcessor<Student, Student> processor(){
		return new StudentProcessor();	
	}

	//1. Reader Bean
	@Bean
	public ItemReader<Student> reader() {
		JdbcCursorItemReader<Student> reader  = new JdbcCursorItemReader<>();
		//provide SQL query
		reader.setSql("SELECT sid,sname,sfee FROM student_tab");
		//specify database connection
		reader.setDataSource(dataSource());
		//convert one ResultSet Row into One Student Object
		reader.setRowMapper( 
				(rs,rowNum)-> new Student(
						rs.getInt("sid"), 
						rs.getString("sname"), 
						rs.getDouble("sfee"))
				);
		return reader;
	}
	/**
	 * Database connection to fetch data 
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/boot6pm");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
}
