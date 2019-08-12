package com.example.cpt

class Card {
    var cardname: String = ""
    var cardnumber: String = ""

    constructor()
    constructor(cardname: String, cardnumber:String) {
        this.cardname = cardname
        this.cardnumber = cardnumber
    }

    fun getcardname():String {
        return cardname
    }
    fun getcardnumber():String {
        return cardnumber
    }
}