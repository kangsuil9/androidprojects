package com.example.cpt

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.google.gson.JsonArray

interface DBInterface {
    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    fun connectionmysql(item: Email): String

    @LambdaFunction
    fun connectionmysql(item: Login): JsonArray

    @LambdaFunction
    fun connectionmysql(item: InsertUser): String

    @LambdaFunction
    fun connectionmysql(item: UpdateUser): String

    @LambdaFunction
    fun connectionmysql(item: InsertTicket_Now): String

    @LambdaFunction
    fun connectionmysql(item: InsertTicket_Later): String

    @LambdaFunction
    fun connectionmysql(item: Load_Available_Tickets): JsonArray

    @LambdaFunction
    fun connectionmysql(item: Load_All_Tickets): JsonArray

    @LambdaFunction
    fun connectionmysql(item: Load_All_ValidTickets): JsonArray

    @LambdaFunction
    fun connectionmysql(item: Validate_Ticket): String
}