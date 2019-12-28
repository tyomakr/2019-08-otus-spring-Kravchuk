package ru.otus.spring.migration.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;
import ru.otus.spring.migration.service.BatchService;
import ru.otus.spring.migration.service.IOService;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final IOService ioService;

    private JobExecution jobExecution;


    public void launchJob() {
        try {
            jobExecution = jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
            ioService.printMsg(jobExecution.getStatus().toString());
        } catch (Exception e) {
            ioService.printMsg(e.getMessage());
        }
    }

    public void restartJob() {
        try {
            jobOperator.restart(jobExecution.getJobId());
            ioService.printMsg(jobExecution.getStatus().toString());
        } catch (Exception e) {
            ioService.printMsg(e.getMessage());
        }
    }


    @Override
    public boolean isWasLaunched() {
        return jobExecution != null;
    }
}


