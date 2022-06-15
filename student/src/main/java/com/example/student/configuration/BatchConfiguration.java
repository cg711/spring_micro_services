package com.example.student.configuration;


import com.example.student.configuration.batch.StudentReader;
import com.example.student.configuration.batch.StudentWriter;
import com.example.student.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory jobBuilderFactory;

//    @Autowired
//    DataSource dataSource;

    @Autowired
    StudentReader studentReader;

    @Autowired
    StudentWriter studentWriter;

    @Bean("ETL")
    public Job jobRun() {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }
    @Bean
    public Step step() {
        String fileName = "";
        return stepBuilderFactory.get("myStep")
                .<Student, Student>chunk(1)
                .reader(reader(fileName))
                .writer(studentWriter)
                .build();
    }
//    @Bean
    @Bean
    @StepScope
    public ItemReader<Student> reader(@Value("#{jobParameters['fileName']}") String fileName) {
        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            String dynamicFileName = (fileName.equals("")) ? "grades.csv" : fileName;
            resources = patternResolver.getResources(dynamicFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiResourceItemReader<Student> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setDelegate(studentReader);
        return reader;
    }

}
//make an api, csv as input
//populate sql table from input

//start implementation of microservices
//make microservices of this batch
//make a microservice of previous task (DTO, Address & User)
//make csv file of User and Addresses and we wil communicate between two microservices and upload as ETL

//Spring Cloud or Eureka (spring cloud more recent)

//Family, Occupation, Recreation, Message