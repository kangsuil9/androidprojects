package com.example.directlambdacall

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

interface MyInterface {
    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    fun directlambdacall(nameInfo: NameInfo): String
    @LambdaFunction
    fun connectionmysql(nameInfo: NameInfo): String
    //fun test(nameInfo: NameInfo): String

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    //@LambdaFunction(functionName = "echo")
    //fun noEcho(nameInfo: NameInfo): Unit
}