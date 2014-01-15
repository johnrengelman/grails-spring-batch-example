package com.objectpartners.sbdemo.persistant

class Team {

    String name
    String nickName

    Integer wins
    Integer losses

    static constraints = {  }

    static transients = ['winPercentage', 'totalGames']

    Integer getTotalGames() {
        wins + losses
    }

    Double getWinPercentage() {
        wins.doubleValue() / totalGames.doubleValue()
    }

    String toString() {
        "$name $nickName"
    }
}
