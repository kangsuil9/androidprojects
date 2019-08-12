package com.example.kangs.wherearewe

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_status_result.*

class StatusResult : AppCompatActivity() {

    val urlAgreePage : String = "https://services3.cic.gc.ca/ecas/security.do?app=ecas&lang=en"
    val urlLoginPage : String = "https://services3.cic.gc.ca/ecas/authenticate.do"
    val urlResultPage : String = "https://services3.cic.gc.ca/ecas/dataaccessexception.do"

    var alreadyEvaluated : Boolean = false

    var bottomSheetBehavior : BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_result)
        //supportActionBar!!.hide()
        wvTest.visibility = View.INVISIBLE

        var bundle = intent.extras

        tvResultSurName.setText(bundle?.get("SURNAME").toString())
        tvResultIdTypeString.setText(bundle?.get("IDTYPESTRING").toString())
        tvResultIdType.setText(bundle?.get("IDTYPE").toString())
        tvResultDateOfBirth.setText(bundle?.get("DATEOFBIRTH").toString())
        tvResultPlaceOfBirthString.setText(bundle?.get("PLACEOFBIRTHSTRING").toString())
        tvResultPlaceOfBirth.setText(bundle?.get("PLACEOFBIRTH").toString())
        tvResultIdNumber.setText(bundle?.get("IDNUMBER").toString())
        alreadyEvaluated = false

        wvTest.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        wvTest.loadUrl(urlAgreePage)

        wvTest.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                finishedPage()
            }
        })

        srlRefresh.setOnRefreshListener {
            wvTest.loadUrl(urlAgreePage)
            finishedPage()
        }
        nsClose.setOnClickListener {
            nsWindowClose()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(nsoptional)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusresult, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.mnResetPassword -> {
                var intent = Intent(this, PasswordSetting::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                return true
            }
            R.id.mnReSignIn -> {
                BackToLogin()
                return true
            }
            R.id.mnContactUs -> {
                contactUs()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun finishedPage() {
        if (!alreadyEvaluated ) {
            alreadyEvaluated = true
            if(wvTest!!.url.toString().equals(urlAgreePage)) {
                wvTest.visibility = View.INVISIBLE
                doContinue(wvTest)
            }
            else if(wvTest!!.url.toString().equals(urlLoginPage)) {
                wvTest.visibility = View.INVISIBLE
                doLogin(wvTest)
            }
            else if(wvTest!!.url.toString().equals(urlResultPage)){
                //doShowResult(wvTest)
                wvTest.visibility = View.VISIBLE
                srlRefresh.isRefreshing = false
            }
        }
        else {
            alreadyEvaluated = false
        }
    }

    private fun BackToLogin() {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun contactUs() {
        nsoptional.visibility = View.VISIBLE
        tvNestedViewcontent.setText("Email : kangsuil9@gmail.com")
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun nsWindowClose() {
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onBackPressed() {
    }

    private fun doContinue(view: WebView) {
        view.evaluateJavascript(
                "javascript:document.getElementById(\"agree\").checked = true;" +
                        "javascript:document.getElementsByName(\"_target1\")[0].click();",
                { _ ->

                }
        )
        //view!!.loadUrl("javascript:document.getElementById(\"agree\").checked = true;")
        //view!!.loadUrl("javascript:document.getElementsByName(\"_target1\")[0].click();")
    }

    private fun doLogin(view: WebView) {
        view.evaluateJavascript(
                "javascript:(function(){document.getElementById('idTypeLabel').value = '${tvResultIdType.text.toString()}';" +
                        "document.getElementById('idNumberLabel').value = '${tvResultIdNumber.text.toString()}';" +
                        "document.getElementById('surnameLabel').value = '${tvResultSurName.text.toString()}';" +
                        "document.getElementById('dobDate').value = '${tvResultDateOfBirth.text.toString()}';" +
                        "document.getElementById('cobLabel').value = '${tvResultPlaceOfBirth.text.toString()}';" +
                        "document.getElementsByName(\"_submit\")[0].click(); })()",
                { _ ->

                }
        )
    }

    private fun doShowResult(view: WebView) {
        view.evaluateJavascript("(function() { return (document.getElementsByTagName('p')[2].innerHTML); })();",
                {
                    _ ->
                }
        )
    }
}