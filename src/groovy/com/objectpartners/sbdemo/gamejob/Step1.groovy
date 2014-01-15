package com.objectpartners.sbdemo.gamejob

import com.objectpartners.sbdemo.item.GameItem
import com.objectpartners.sbdemo.persistant.Game
import com.objectpartners.sbdemo.persistant.Team
import com.objectpartners.sbdemo.service.SbdemoService
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.LineMapper

import java.text.ParseException
import java.text.SimpleDateFormat

class GameLineMapper implements LineMapper<GameItem> {


    @Override
    GameItem mapLine(String line, int lineNumber) throws Exception {
        GameItem game = new GameItem()

        String[] tokens = line.split(',')

        if (tokens.length != 7) return null

        game.homeTeamName = tokens[0]
        game.homeTeamNickName = tokens[1]
        game.homeTeamScore = tokens[2].toInteger()
        game.visitingTeamName = tokens[3]
        game.visitingTeamNickName = tokens[4]
        game.visitingTeamScore = tokens[5].toInteger()
        game.date = parseDate(tokens[6])

        return game
    }

    Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat('MM/dd/yyyy:HH:mm')
        return format.parse(dateString)
    }
}

class GameItemProcessor implements ItemProcessor<GameItem, Game> {

    SbdemoService sbdemoService

    @Override
    public Game process(GameItem gameItem) throws Exception {
        println "Processing a game: $gameItem"

        Game game = new Game()

        Team homeTeam = sbdemoService.findTeamByName(gameItem.homeTeamName, gameItem.homeTeamNickName)
        Team visitingTeam = sbdemoService.findTeamByName(gameItem.visitingTeamName, gameItem.visitingTeamNickName)

        game.with {
            date = gameItem.date
            home = homeTeam
            visitor = visitingTeam
            homeScore = gameItem.homeTeamScore
            visitorScore = gameItem.visitingTeamScore
        }

        return game
    }
}

class GameWriter implements ItemWriter<Game> {

    SbdemoService sbdemoService

    @Override
    void write(List<? extends Game> games) throws Exception {
        sbdemoService.saveGames(games)
    }
}
