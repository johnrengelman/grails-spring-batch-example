package com.objectpartners.sbdemo.persistant

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher

class GameJobController {

    JobLauncher jobLauncher
    def gameJob

    def index() {
        [:]
    }

    def startJob() {
        def f = request.getFile('file')
        File tempFile = File.createTempFile('game', '.csv')
        println "Created temp file at: ${tempFile.path}"
        f.transferTo(tempFile)
        JobParametersBuilder builder = new JobParametersBuilder()
        builder.addString('file', "file:${tempFile.path}")
        JobExecution exec = jobLauncher.run(gameJob, builder.toJobParameters())
        if (exec) {
            flash.message = "Started Game Job ID: ${exec.jobId}".toString()
        } else {
            flash.error = 'Error starting Game Job'
        }
        redirect(action: 'index')
    }
}
