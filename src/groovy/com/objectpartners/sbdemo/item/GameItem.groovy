package com.objectpartners.sbdemo.item

class GameItem {

    Date date
    String visitingTeamName
    String visitingTeamNickName
    String homeTeamName
    String homeTeamNickName
    Integer visitingTeamScore
    Integer homeTeamScore

    String toString() {
        if (!homeTeamName) {
            return "GameItem not initialized."
        }

        String winningTeam = homeTeamScore > visitingTeamScore ? homeTeamName : visitingTeamName
        String losingTeam = homeTeamScore > visitingTeamScore ? visitingTeamName : homeTeamName

        Integer winningScore = homeTeamScore > visitingTeamScore ? homeTeamScore : visitingTeamScore
        Integer losingScore = homeTeamScore > visitingTeamScore ? visitingTeamScore : homeTeamScore

        String location = homeTeamScore > visitingTeamScore ? 'at home' : 'away from home'

        return "The $winningTeam defeated the $losingTeam $winningScore to $losingScore $location."
    }
}
