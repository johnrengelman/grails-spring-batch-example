package com.objectpartners.sbdemo.teamjob

import com.objectpartners.sbdemo.persistant.Team
import com.objectpartners.sbdemo.service.SbdemoService
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

class TeamReader implements ItemReader<Team> {

    private BufferedReader reader = null

    TeamReader(String fileName) {
        reader = new File(fileName).newReader()
    }

    @Override
    Team read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String line = null
        line = reader.readLine()
        println "Reading in a team line: $line"
        if (!line) return null

        Team team = new Team()
        String[] tokens = line.split(',')

        if (tokens.length != 2) return null

        team.name = tokens[0]
        team.nickName = tokens[1]

        team.losses = 0
        team.wins = 0

        team
    }
}

class TeamWriter implements ItemWriter<Team> {

    SbdemoService sbdemoService

    @Override
    void write(List<? extends Team> teams) throws Exception {
        println "Writing ${teams.size()} teams."
        sbdemoService.saveTeams(teams)
    }
}
