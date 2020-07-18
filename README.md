# SpringBoot2BatchMySQLToCsvFile
This is Spring Boot Batch Processing MySQL Database to CSV File
Spring Boot Batch MySQL Database to CSV File

 ItemReader:  JdbcCursorItemReader<T> 
 		SQL, datasource, rowMapper

 ItemWriter: FlatFileItemWriter
              Resource, lineAggregator (Delimeter, FieldExtractor)
	      FieldExtractor(names)

*) SQL - This is Query used to fetch data from one table.
*) datasource - It indicates Database connection.
*) RowMapper - Result is given as ResultSet format that contains rows.
                One Row should be converted to one Object.

*) Resource - File Location and Name
*) LineAggregator - Convert one Object to one Row into File
*) FieldExtractor - Extract(read) data based on Fields(variable names) 
                    from Object into CSV File

---------------------------------------------------
1. Model class
2. Processor
3. Listener
4. BatchConfig
5. JobRunner
---------------------------------------------------
CREATE TABLE
CREATE TABLE student_tab (
	sid   INT,
	sname   VARCHAR(20),
	sfee   DOUBLE
);
INSERT INTO student_tab values(10,'SAM',2500.0);
INSERT INTO student_tab values(11,'SYED',3500.0);
INSERT INTO student_tab values(12,'RAM',4000.0);
INSERT INTO student_tab values(13,'KHAN',2500.0);
INSERT INTO student_tab values(14,'JAI',5000.0);
