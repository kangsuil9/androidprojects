package com.example.kangs.dbmanagertest

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DBManager {

    object DBService {

        fun testVolley(context: Context, URL: String, map: HashMap<String, String>, success: (String) -> Unit) {

            val queue = Volley.newRequestQueue(context)
            val response: JSONObject? = null

            val postRequest = object : StringRequest(Request.Method.POST, URL, Response.Listener<String>
            {
                response ->
                success(response.toString())
            },
                    Response.ErrorListener { e ->
                        success(e.toString())
                    }
            ) {
                override fun getParams(): Map<String, String> {
                    //Creating HashMap
                    var params = HashMap<String, String>()
                    params = map
                    return params
                }
            }
            queue.add(postRequest)
        }
    }


}