package com.example.directlambdacall

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val echotest = "ca-central-1:9c8421a5-6993-4b68-b4eb-cc7de75107fc"
        val credentialsProvider = CognitoCachingCredentialsProvider(
            this@MainActivity.applicationContext,
            "ca-central-1:9c8421a5-6993-4b68-b4eb-cc7de75107fc",
            Regions.CA_CENTRAL_1
        )

        val f = LambdaInvokerFactory.builder()
            .context(this@MainActivity.applicationContext)
            .region(Regions.CA_CENTRAL_1)
            .credentialsProvider(credentialsProvider)
            .build()

        val myInterface = f.build(MyInterface::class.java)

        val nameInfo = NameInfo("SOOIL", "KANG");

        thread(start = true) {
            // Invoke "echo" method.  In case it fails, it will throw an exception
            //val name: String = myInterface.directlambdacall(nameInfo)
            val email: String = myInterface.connectionmysql(nameInfo)

            runOnUiThread {
                text1.setText(email)
              //  text2.setText(name)
            }
        }
    }
}
