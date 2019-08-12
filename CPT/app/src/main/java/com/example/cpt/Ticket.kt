package com.example.cpt

import java.util.*

class Ticket {
    var tickettype: String = ""
    var agegroup: String = ""
    var card: Card? = Card()
    var timeleft: Long = 0L
    var price: Double = 0.0
    var sequence: Int = 0
    var purchaseddate: String = ""
    var validateddate: String = ""
    var ticketstate: String = ""
    var expiretimemilli: Long = 0L

    constructor()

    constructor(tickettype: String, agegroup: String) {
        this.tickettype = tickettype
        this.agegroup = agegroup
    }

    constructor(tickettype: String, agegroup: String, card: Card, price: Double) {
        this.tickettype = tickettype
        this.agegroup = agegroup
        this.card = card
        this.price = price
    }

    fun gettickettype(): String {
        return tickettype
    }
    fun getagegroup(): String {
        return agegroup
    }
    fun getcard(): Card {
        return card!!
    }
    fun gettimeleft(): Long {
        return timeleft
    }
    fun getprice(): Double {
        return price
    }
    fun getsequence(): Int {
        return sequence
    }
    fun getpurchaseddate(): String {
        return purchaseddate
    }
    fun getvalidateddate(): String {
        return validateddate
    }
    fun getticketstate(): String {
        return ticketstate
    }
    fun getexpiretimemilli(): Long {
        return expiretimemilli
    }
}