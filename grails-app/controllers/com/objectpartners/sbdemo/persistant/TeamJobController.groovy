package com.objectpartners.sbdemo.persistant

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher

class TeamJobController {

    JobLauncher jobLauncher
    def teamJob

    def index() {
        [:]
    }

    def startJob() {
        JobParametersBuilder builder = new JobParametersBuilder()
        JobExecution exec = jobLauncher.run(teamJob, builder.toJobParameters())
        if (exec) {
            flash.message = "Started Team Job: ${exec.jobId}".toString()
        } else {
            flash.error = "Error starting Team Job"
        }
        redirect(action: 'index')
    }
}
