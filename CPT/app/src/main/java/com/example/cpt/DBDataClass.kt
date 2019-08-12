package com.example.cpt


/*
      Login
 */
data class Login(var eventname: String, var email: String, var password: String)
data class LoginResult(var email: String, var agegroup: String, var firstname: String, var middlename: String, var lastname: String)
data class Email(var eventname: String, var email: String)

/*
      Sign Up
 */
data class InsertUser(var eventname: String, var email: String, var password: String, var agegroup: String, var firstname: String, var middlename: String, var lastname: String)


/*
      Modify User Information

      Update, SharedPreference, Global Variable
 */
data class UpdateUser(var eventname: String, var email: String, var agegroup: String, var firstname: String, var middlename: String, var lastname: String)


/*
     Ticket Purchase
 */
// (Now)
data class InsertTicket_Now(var eventname: String, var email: String, var tickettype: String, var agegroup: String, var ticketprice: Double, var cardname: String, var cardnumber: String, var ticketcount: Int, var expiretimemilli: Long)
// (Later)
data class InsertTicket_Later(var eventname: String, var email: String, var tickettype: String, var agegroup: String, var ticketprice: Double, var cardname: String, var cardnumber: String, var ticketcount: Int)


/*
     Ticket Validation

     Load_Tickets
     InsertTicket_Update (Available -> Validated)
 */
data class Load_Available_Tickets(var eventname: String, var email: String)
data class Load_All_Tickets(var eventname: String, var email: String)
data class Load_All_ValidTickets(var eventname: String, var email: String, var lastname: String, var expiretimemilli: Long)
data class Tickets_Result(var email: String, var sequence: Int, var tickettype: String, var agegroup: String)
data class Validate_Ticket(var eventname: String, var email: String, var sequence: Int, var expiretimemilli: Long)