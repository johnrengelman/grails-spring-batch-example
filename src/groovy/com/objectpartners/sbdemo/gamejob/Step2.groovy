package com.objectpartners.sbdemo.gamejob

import com.objectpartners.sbdemo.persistant.Game
import com.objectpartners.sbdemo.persistant.Team
import com.objectpartners.sbdemo.service.SbdemoService
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException

class GameReader implements ItemReader<Game> {

    private int offset = 0

    @Override
    Game read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Game.withNewSession {
            List<Game> games = Game.list(max: 1, offset: offset)
            offset++
            if (games.isEmpty() || games[0] == null) {
                return null
            } else {
                return games.first()
            }
        }
    }
}

class StandingProcessor implements ItemProcessor<Game, Collection<Team>> {

    static boolean cleared = false

    SbdemoService sbdemoService

    @Override
    Collection<Team> process(Game game) throws Exception {
        println "Processing standing for $game"
        if (!cleared) {
            sbdemoService.resetStandings()
            cleared = true
        }
        //Need to do this because the HibernateCursorItemReader doesn't attach
        //the instance of the session
        if (!game.isAttached()) {
            game.attach()
        }
        sbdemoService.updateTeamStandings(game)
    }
}

class StandingWriter implements ItemWriter<Collection<Team>> {

    SbdemoService sbdemoService

    @Override
    void write(List<? extends Collection<Team>> collections) throws Exception {
        println "Processing standings for teams: $collections"
        collections.each { teams ->
            sbdemoService.saveTeams(teams)
        }
    }
}
