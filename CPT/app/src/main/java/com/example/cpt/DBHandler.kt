package com.example.cpt

import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions

class DBHandler {
    lateinit var credentialsProvider: CognitoCachingCredentialsProvider
    lateinit var factory: LambdaInvokerFactory
    lateinit var myInterface: DBInterface
    lateinit var context: Context

    constructor()

    constructor(context: Context): super() {

        this.context = context

        credentialsProvider = CognitoCachingCredentialsProvider(
            this.context,
            "ca-central-1:9c8421a5-6993-4b68-b4eb-cc7de75107fc",
            Regions.CA_CENTRAL_1
        )

        factory = LambdaInvokerFactory.builder()
            .context(this.context)
            .region(Regions.CA_CENTRAL_1)
            .credentialsProvider(credentialsProvider)
            .build()

        myInterface = factory.build(DBInterface::class.java)
    }
}