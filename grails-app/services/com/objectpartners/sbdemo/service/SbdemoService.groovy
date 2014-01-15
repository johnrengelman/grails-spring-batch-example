package com.objectpartners.sbdemo.service

import com.objectpartners.sbdemo.persistant.Game
import com.objectpartners.sbdemo.persistant.Team

class SbdemoService {

    void saveGames(List<? extends Game> games) {
        games.each { game ->
            game.save()
        }
    }

    void saveTeams(Collection<? extends Team> teams) {
        teams.each { team ->
            team.save()
        }
    }

    void resetStandings() {
        def teams = Team.list()
        teams.each { team ->
            team.setWins(0)
            team.setLosses(0)
        }
        saveTeams(teams)
    }

    List<Team> updateTeamStandings(Game game) {

        // Reload the teams. The win/loss totals may have changed during processing
        Team home = game.home
        Team visitor = game.visitor

        if (game.homeScore > game.visitorScore) {
            home.wins++
            visitor.losses++
        } else {
            home.losses++
            visitor.wins++
        }

        return [home, visitor]
    }

    Team findTeamByName(String name, String nickName) {
        Team.findByNameAndNickName(name, nickName)
    }
}
