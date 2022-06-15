package com.example.student.configuration.batch;

import com.example.student.model.Student;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class StudentReader extends FlatFileItemReader<Student> implements ItemReader<Student> {
    public StudentReader() {
        setResource(new FileSystemResource("grades.csv"));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    /**
     * Initializes the line mapper to be used within Student batch processing.
     * @return New line mapper
     */
    public DefaultLineMapper<Student> getDefaultLineMapper() {
        DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<Student>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setNames(new String[] {
                "lastName",
                "firstName",
                "ssn",
                "grade"
        });

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper<Student> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Student>();
        beanWrapperFieldSetMapper.setTargetType(Student.class);
        beanWrapperFieldSetMapper.setDistanceLimit(1);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return defaultLineMapper;

    }
}

