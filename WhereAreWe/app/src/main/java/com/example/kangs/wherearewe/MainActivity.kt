package com.example.kangs.wherearewe

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    var hIdentificationType = HashMap<String, String>()
    var hPlaceOfBirth = HashMap<String, String>()

    var bIdentificationType : String? = null
    var bIdentificationTypemString : String? = null
    var bIdentificationTypePosition : Int? = null
    var bIdentificationNumber : String? = null
    var bSurName : String? = null
    var bDateOfBirth : String? = null
    var bPlaceOfBirth : String? = null
    var bPlaceOfBirthString : String? = null
    var bPlaceOfBirthPosition : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        init()
        if(loadData())
            login()

        btnLogin.setOnClickListener {
            login()
        }

        btnClearAll.setOnClickListener {
            clearall()
        }

        tvLoginDateOfBirth.setOnClickListener{
            val fDatepicker = DatePicker()
            fDatepicker.show(supportFragmentManager, "Date Picker")
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    private fun clearall() {
        spnLoginIdType.setSelection(0)
        etLoginSurName.setText("")
        etLoginIdNumber.setText("")
        spnLoginPlaceOfBirth.setSelection(0)
        tvLoginWarning.setText("")

        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        var temp_month : String = (month+1).toString().padStart(2, '0')
        tvLoginDateOfBirth.setText("$year-$temp_month-$day".toString())
    }

    private fun init() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        var temp_month : String = (month+1).toString().padStart(2, '0')
        tvLoginDateOfBirth.setText("$year-$temp_month-$day".toString())

        spnLoginIdType!!.setOnItemSelectedListener(this)
        var arrAdapterIdType = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.resources.getStringArray(R.array.identificationtype))
        spnLoginIdType!!.adapter = arrAdapterIdType

        spnLoginPlaceOfBirth!!.setOnItemSelectedListener(this)
        var arrAdapterPlace = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.resources.getStringArray(R.array.placeofbirth))
        spnLoginPlaceOfBirth!!.adapter = arrAdapterPlace

        IdentificationType()
        PlaceOfBirth()
    }

    private fun login() {
        if(hIdentificationType.get(spnLoginIdType.selectedItem.toString()).equals("-1") ||
                etLoginIdNumber.text.toString().isEmpty() ||
                etLoginSurName.text.toString().isEmpty() ||
                tvLoginDateOfBirth.text.toString().isEmpty() ||
                hPlaceOfBirth.get(spnLoginPlaceOfBirth.selectedItem.toString()).equals("-1")) {
            tvLoginWarning.setText("This Form is incomplete. Please provide the necessary information below.\n")
            return
        }
        else {
            bSurName = etLoginSurName.text.toString()
            bIdentificationNumber = etLoginIdNumber.text.toString()
            bDateOfBirth = tvLoginDateOfBirth.text.toString()
            bIdentificationType = hIdentificationType.get(spnLoginIdType.selectedItem.toString()).toString()
            bIdentificationTypemString = spnLoginIdType.selectedItem.toString()
            bIdentificationTypePosition = spnLoginIdType.selectedItemPosition.toInt()
            bPlaceOfBirth = hPlaceOfBirth.get(spnLoginPlaceOfBirth.selectedItem.toString()).toString()
            bPlaceOfBirthString = spnLoginPlaceOfBirth.selectedItem.toString()
            bPlaceOfBirthPosition = spnLoginPlaceOfBirth.selectedItemPosition.toInt()

            saveData(bIdentificationType!!, bIdentificationTypemString!!, bIdentificationTypePosition!!, bIdentificationNumber!!, bSurName!!, bDateOfBirth!!, bPlaceOfBirth!!, bPlaceOfBirthString!!, bPlaceOfBirthPosition!!)

            val intent = Intent(this, StatusResult::class.java)
            intent.putExtra("IDTYPE", bIdentificationType)
            intent.putExtra("IDTYPESTRING", bIdentificationTypemString)
            intent.putExtra("IDNUMBER", bIdentificationNumber)
            intent.putExtra("SURNAME", bSurName)
            intent.putExtra("DATEOFBIRTH", bDateOfBirth)
            intent.putExtra("PLACEOFBIRTH", bPlaceOfBirth)
            intent.putExtra("PLACEOFBIRTHSTRING", bPlaceOfBirthString)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent!!.id) {
            R.id.spnLoginIdType -> {
                bIdentificationType = hIdentificationType.get(spnLoginIdType.selectedItem.toString()).toString()
                bIdentificationTypemString = spnLoginIdType.selectedItem.toString()
                bIdentificationTypePosition = spnLoginIdType.selectedItemPosition.toInt()
            }
            R.id.spnLoginPlaceOfBirth -> {
                bPlaceOfBirth = hPlaceOfBirth.get(spnLoginPlaceOfBirth.selectedItem.toString()).toString()
                bPlaceOfBirthString = spnLoginPlaceOfBirth.selectedItem.toString()
                bPlaceOfBirthPosition = spnLoginPlaceOfBirth.selectedItemPosition.toInt()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun IdentificationType() {
        hIdentificationType.put("Identification Type", "-1")
        hIdentificationType.put("Client ID Number / Unique Client Identifier", "1")
        hIdentificationType.put("Receipt Number (IMM 5401)", "2")
        hIdentificationType.put("Application Number / Case Number", "3")
        hIdentificationType.put("Record of Landing Number", "4")
        hIdentificationType.put("Permanent Resident Card Number", "5")
        hIdentificationType.put("Citizenship Receipt Number", "6")
        hIdentificationType.put("Citizenship File Number / Group Number", "7")
        hIdentificationType.put("Confirmation of Permanent Regidence Number", "8")
    }

    private fun PlaceOfBirth() {
        hPlaceOfBirth.put("Please select","-1")
        hPlaceOfBirth.put("Afghanistan","252")
        hPlaceOfBirth.put("Africa NES","199")
        hPlaceOfBirth.put("Aland Island","401")
        hPlaceOfBirth.put("Albania","81")
        hPlaceOfBirth.put("Algeria","131")
        hPlaceOfBirth.put("Andorra","82")
        hPlaceOfBirth.put("Angola","151")
        hPlaceOfBirth.put("Anguilla","620")
        hPlaceOfBirth.put("Antigua and Barbuda","621")
        hPlaceOfBirth.put("Argentina","703")
        hPlaceOfBirth.put("Armenia","49")
        hPlaceOfBirth.put("Aruba","658")
        hPlaceOfBirth.put("Asia NES","299")
        hPlaceOfBirth.put("Australia","305")
        hPlaceOfBirth.put("Australia NES","399")
        hPlaceOfBirth.put("Austria","11")
        hPlaceOfBirth.put("Azerbaijan","50")
        hPlaceOfBirth.put("Azores","35")
        hPlaceOfBirth.put("Bahamas","622")
        hPlaceOfBirth.put("Bahrain","253")
        hPlaceOfBirth.put("Bailwick of Jersey","412")
        hPlaceOfBirth.put("Bangladesh","212")
        hPlaceOfBirth.put("Barbados","610")
        hPlaceOfBirth.put("Belarus","51")
        hPlaceOfBirth.put("Belgium","12")
        hPlaceOfBirth.put("Belize","541")
        hPlaceOfBirth.put("Benin","160")
        hPlaceOfBirth.put("Bermuda","601")
        hPlaceOfBirth.put("Bhutan","254")
        hPlaceOfBirth.put("Bolivia","751")
        hPlaceOfBirth.put("Bonaire, Sint Eustatius, Saba","402")
        hPlaceOfBirth.put("Bosnia and Herzegovina","48")
        hPlaceOfBirth.put("Botswana","153")
        hPlaceOfBirth.put("Bouvet Island","403")
        hPlaceOfBirth.put("Brazil","709")
        hPlaceOfBirth.put("British Indian Ocean Territory","404")
        hPlaceOfBirth.put("British Virgin Islands","633")
        hPlaceOfBirth.put("Brunei Darussalam","255")
        hPlaceOfBirth.put("Bulgaria","83")
        hPlaceOfBirth.put("Burkina Faso","188")
        hPlaceOfBirth.put("Burma (Myanmar)","241")
        hPlaceOfBirth.put("Burundi","154")
        hPlaceOfBirth.put("Cabo Verde","911")
        hPlaceOfBirth.put("Cambodia","256")
        hPlaceOfBirth.put("Cameroon","155")
        hPlaceOfBirth.put("Canada","511")
        hPlaceOfBirth.put("Canary Islands","39")
        hPlaceOfBirth.put("Cayman Islands","624")
        hPlaceOfBirth.put("Central African Republic","157")
        hPlaceOfBirth.put("Central America NES","549")
        hPlaceOfBirth.put("Chad","156")
        hPlaceOfBirth.put("Channel Islands","9")
        hPlaceOfBirth.put("Chile","721")
        hPlaceOfBirth.put("China","202")
        hPlaceOfBirth.put("China (Hong Kong SAR)","200")
        hPlaceOfBirth.put("China (Macao SAR)","198")
        hPlaceOfBirth.put("Christmas Island","405")
        hPlaceOfBirth.put("Colombia","722")
        hPlaceOfBirth.put("Comoros","905")
        hPlaceOfBirth.put("Cook Islands","840")
        hPlaceOfBirth.put("Costa Rica","542")
        hPlaceOfBirth.put("Croatia","43")
        hPlaceOfBirth.put("Cuba","650")
        hPlaceOfBirth.put("Curaçao","406")
        hPlaceOfBirth.put("Cyprus","221")
        hPlaceOfBirth.put("Czech Republic","15")
        hPlaceOfBirth.put("Czechoslovakia","14")
        hPlaceOfBirth.put("Democratic Rep. of Congo","158")
        hPlaceOfBirth.put("Denmark","17")
        hPlaceOfBirth.put("Djibouti","183")
        hPlaceOfBirth.put("Dominica","625")
        hPlaceOfBirth.put("Dominican Republic","651")
        hPlaceOfBirth.put("East Timor","916")
        hPlaceOfBirth.put("Ecuador","753")
        hPlaceOfBirth.put("Egypt","101")
        hPlaceOfBirth.put("El Salvador","543")
        hPlaceOfBirth.put("England","2")
        hPlaceOfBirth.put("Equatorial Guinea","178")
        hPlaceOfBirth.put("Eritrea","162")
        hPlaceOfBirth.put("Estonia","18")
        hPlaceOfBirth.put("Ethiopia","161")
        hPlaceOfBirth.put("Europe NES","99")
        hPlaceOfBirth.put("Falkland Islands","912")
        hPlaceOfBirth.put("Faroe Islands","408")
        hPlaceOfBirth.put("Federated States of Micronesia","835")
        hPlaceOfBirth.put("Fiji","801")
        hPlaceOfBirth.put("Finland","21")
        hPlaceOfBirth.put("Fr. South. and Antarctic Lands","821")
        hPlaceOfBirth.put("France","22")
        hPlaceOfBirth.put("French Guiana","754")
        hPlaceOfBirth.put("French Polynesia","845")
        hPlaceOfBirth.put("Gabon","163")
        hPlaceOfBirth.put("Gambia","164")
        hPlaceOfBirth.put("Georgia","52")
        hPlaceOfBirth.put("German Democratic Republic","46")
        hPlaceOfBirth.put("Germany, Federal Republic Of","24")
        hPlaceOfBirth.put("Ghana","165")
        hPlaceOfBirth.put("Gibraltar","84")
        hPlaceOfBirth.put("Greece","25")
        hPlaceOfBirth.put("Greenland","521")
        hPlaceOfBirth.put("Grenada","626")
        hPlaceOfBirth.put("Guadeloupe","653")
        hPlaceOfBirth.put("Guam","832")
        hPlaceOfBirth.put("Guatemala","544")
        hPlaceOfBirth.put("Guernsey","409")
        hPlaceOfBirth.put("Guinea","166")
        hPlaceOfBirth.put("Guinea-Bissau","167")
        hPlaceOfBirth.put("Guyana","711")
        hPlaceOfBirth.put("Haiti","654")
        hPlaceOfBirth.put("Heard and MacDonald Islands","410")
        hPlaceOfBirth.put("Honduras","545")
        hPlaceOfBirth.put("Hong Kong","204")
        hPlaceOfBirth.put("Hungary","26")
        hPlaceOfBirth.put("Iceland","85")
        hPlaceOfBirth.put("India","205")
        hPlaceOfBirth.put("Indonesia","222")
        hPlaceOfBirth.put("Iran","223")
        hPlaceOfBirth.put("Iraq","224")
        hPlaceOfBirth.put("Ireland","27")
        hPlaceOfBirth.put("Isle of Man","411")
        hPlaceOfBirth.put("Israel","206")
        hPlaceOfBirth.put("Italy","28")
        hPlaceOfBirth.put("Ivory Coast","169")
        hPlaceOfBirth.put("Jamaica","602")
        hPlaceOfBirth.put("Japan","207")
        hPlaceOfBirth.put("Jordan","225")
        hPlaceOfBirth.put("Kazakhstan","53")
        hPlaceOfBirth.put("Kenya","132")
        hPlaceOfBirth.put("Kiribati","831")
        hPlaceOfBirth.put("Korea, North (DPRK)","257")
        hPlaceOfBirth.put("Korea, South","258")
        hPlaceOfBirth.put("Kosovo","64")
        hPlaceOfBirth.put("Kuwait","226")
        hPlaceOfBirth.put("Kyrgyzstan","54")
        hPlaceOfBirth.put("Laos","260")
        hPlaceOfBirth.put("Latvia","19")
        hPlaceOfBirth.put("Lebanon","208")
        hPlaceOfBirth.put("Lesotho","152")
        hPlaceOfBirth.put("Liberia","170")
        hPlaceOfBirth.put("Libya","171")
        hPlaceOfBirth.put("Liechtenstein","86")
        hPlaceOfBirth.put("Lithuania","20")
        hPlaceOfBirth.put("Luxembourg","13")
        hPlaceOfBirth.put("Macao","261")
        hPlaceOfBirth.put("Macedonia","70")
        hPlaceOfBirth.put("Madagascar","172")
        hPlaceOfBirth.put("Madeira","36")
        hPlaceOfBirth.put("Malawi","111")
        hPlaceOfBirth.put("Malaysia","242")
        hPlaceOfBirth.put("Maldives","901")
        hPlaceOfBirth.put("Mali","173")
        hPlaceOfBirth.put("Malta","30")
        hPlaceOfBirth.put("Marinas","833")
        hPlaceOfBirth.put("Marshall Islands","834")
        hPlaceOfBirth.put("Martinique","655")
        hPlaceOfBirth.put("Mauritania","174")
        hPlaceOfBirth.put("Mauritius","902")
        hPlaceOfBirth.put("Mayotte","906")
        hPlaceOfBirth.put("Mexico","501")
        hPlaceOfBirth.put("Moldova","55")
        hPlaceOfBirth.put("Monaco","87")
        hPlaceOfBirth.put("Mongolia","262")
        hPlaceOfBirth.put("Montenegro","63")
        hPlaceOfBirth.put("Montserrat","627")
        hPlaceOfBirth.put("Morocco","133")
        hPlaceOfBirth.put("Mozambique","175")
        hPlaceOfBirth.put("Namibia","122")
        hPlaceOfBirth.put("Nauru","341")
        hPlaceOfBirth.put("Nepal","264")
        hPlaceOfBirth.put("Netherlands Antilles, The","652")
        hPlaceOfBirth.put("Netherlands, The","31")
        hPlaceOfBirth.put("Nevis","628")
        hPlaceOfBirth.put("New Caledonia","822")
        hPlaceOfBirth.put("New Zealand","339")
        hPlaceOfBirth.put("Newfoundland, Dominion of","512")
        hPlaceOfBirth.put("Nicaragua","546")
        hPlaceOfBirth.put("Niger","176")
        hPlaceOfBirth.put("Nigeria","177")
        hPlaceOfBirth.put("Niue","414")
        hPlaceOfBirth.put("North Vietnam","271")
        hPlaceOfBirth.put("Northern Ireland","6")
        hPlaceOfBirth.put("Northern Mariana Islands","830")
        hPlaceOfBirth.put("Norway","32")
        hPlaceOfBirth.put("Oceania NES","899")
        hPlaceOfBirth.put("Oman","263")
        hPlaceOfBirth.put("Pakistan","209")
        hPlaceOfBirth.put("Palestinian Authority","213")
        hPlaceOfBirth.put("Panama","547")
        hPlaceOfBirth.put("Panama Canal Zone","548")
        hPlaceOfBirth.put("Papau","343")
        hPlaceOfBirth.put("Papua New Guinea","342")
        hPlaceOfBirth.put("Paraguay","755")
        hPlaceOfBirth.put("Peru","723")
        hPlaceOfBirth.put("Philippines","227")
        hPlaceOfBirth.put("Pitcairn Islands","842")
        hPlaceOfBirth.put("Poland","33")
        hPlaceOfBirth.put("Portugal","34")
        hPlaceOfBirth.put("Puerto Rico","656")
        hPlaceOfBirth.put("Qatar","265")
        hPlaceOfBirth.put("Republic of Congo","159")
        hPlaceOfBirth.put("Republic of Palau","836")
        hPlaceOfBirth.put("Réunion","903")
        hPlaceOfBirth.put("Romania","88")
        hPlaceOfBirth.put("Russia","56")
        hPlaceOfBirth.put("Rwanda","179")
        hPlaceOfBirth.put("Saint Helena","915")
        hPlaceOfBirth.put("Saint Kitts and Nevis","629")
        hPlaceOfBirth.put("Saint Lucia","630")
        hPlaceOfBirth.put("Saint Pierre and Miquelon","531")
        hPlaceOfBirth.put("Saint-Barthelemy","407")
        hPlaceOfBirth.put("Saint-Martin","415")
        hPlaceOfBirth.put("Samoa","844")
        hPlaceOfBirth.put("Samoa, American","843")
        hPlaceOfBirth.put("San Marino","89")
        hPlaceOfBirth.put("Sao Tome and Principe","914")
        hPlaceOfBirth.put("Saudi Arabia","231")
        hPlaceOfBirth.put("Scotland","7")
        hPlaceOfBirth.put("Senegal","180")
        hPlaceOfBirth.put("Serbia and Montenegro","61")
        hPlaceOfBirth.put("Serbia, Republic of","62")
        hPlaceOfBirth.put("Seychelles","904")
        hPlaceOfBirth.put("Sierra Leone","181")
        hPlaceOfBirth.put("Sikkim (Asia)","266")
        hPlaceOfBirth.put("Singapore","246")
        hPlaceOfBirth.put("Sint-Maarten","416")
        hPlaceOfBirth.put("Slovakia","16")
        hPlaceOfBirth.put("Slovenia","47")
        hPlaceOfBirth.put("Soloman Islands","825")
        hPlaceOfBirth.put("Solomon Islands","824")
        hPlaceOfBirth.put("Somalia","182")
        hPlaceOfBirth.put("South Africa, Republic of","121")
        hPlaceOfBirth.put("South America NES","799")
        hPlaceOfBirth.put("South Sudan","189")
        hPlaceOfBirth.put("Spain","37")
        hPlaceOfBirth.put("Sri Lanka","201")
        hPlaceOfBirth.put("St. Vincent and the Grenadines","631")
        hPlaceOfBirth.put("Sudan","185")
        hPlaceOfBirth.put("Suriname","752")
        hPlaceOfBirth.put("Swaziland","186")
        hPlaceOfBirth.put("Sweden","40")
        hPlaceOfBirth.put("Switzerland","41")
        hPlaceOfBirth.put("Syria","210")
        hPlaceOfBirth.put("Taiwan","203")
        hPlaceOfBirth.put("Tajikistan","57")
        hPlaceOfBirth.put("Tanzania","130")
        hPlaceOfBirth.put("Thailand","267")
        hPlaceOfBirth.put("Tibet (Autonomous Region)","268")
        hPlaceOfBirth.put("Togo","187")
        hPlaceOfBirth.put("Tokelau","417")
        hPlaceOfBirth.put("Tonga","846")
        hPlaceOfBirth.put("Trinidad and Tobago","605")
        hPlaceOfBirth.put("Tunisia","135")
        hPlaceOfBirth.put("Turkey","45")
        hPlaceOfBirth.put("Turkmenistan","58")
        hPlaceOfBirth.put("Turks and Caicos Islands","632")
        hPlaceOfBirth.put("Tuvalu","826")
        hPlaceOfBirth.put("U.S. Minor outlying Islands","418")
        hPlaceOfBirth.put("Uganda","136")
        hPlaceOfBirth.put("UK - British subject","1")
        hPlaceOfBirth.put("Ukraine","59")
        hPlaceOfBirth.put("UN specialized agency","981")
        hPlaceOfBirth.put("Union Of Soviet Socialist Rep","42")
        hPlaceOfBirth.put("United Arab Emirates","280")
        hPlaceOfBirth.put("United States of America","461")
        hPlaceOfBirth.put("Uruguay","724")
        hPlaceOfBirth.put("Uzbekistan","60")
        hPlaceOfBirth.put("Vanuatu","823")
        hPlaceOfBirth.put("Vatican City State","90")
        hPlaceOfBirth.put("Venezuela","725")
        hPlaceOfBirth.put("Vietnam","270")
        hPlaceOfBirth.put("Virgin Islands, U.S.","657")
        hPlaceOfBirth.put("Wales","8")
        hPlaceOfBirth.put("Wallis and Futuna Is., Terr.","841")
        hPlaceOfBirth.put("West Indies NES","699")
        hPlaceOfBirth.put("Western Sahara","184")
        hPlaceOfBirth.put("Yemen","273")
        hPlaceOfBirth.put("Yugoslavia","44")
        hPlaceOfBirth.put("Zambia","112")
        hPlaceOfBirth.put("Zimbabwe","113")
    }

    private fun saveData(type: String, typeString: String, typePosition: Int, number: String, name: String, date:String, place:String, placeString: String, placePosition: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putString("TYPE", type).
                putString("TYPESTRING", typeString).
                putInt("TYPEPOSITION", typePosition).
                putString("NUMBER", number).
                putString("NAME", name).
                putString("DATE", date).
                putString("PLACE", place).
                putString("PLACESTRING", placeString).
                putInt("PLACEPOSITION", placePosition).
                apply()
    }

    private fun loadData() : Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val type = pref.getString("TYPE", "0")
        val typePosition = pref.getInt("TYPEPOSITION", 0)
        val number = pref.getString("NUMBER", "0")
        val name = pref.getString("NAME", "0")
        val date = pref.getString("DATE", "0")
        val placePosition = pref.getInt("PLACEPOSITION", 0)

        if(type!!.equals("0")) {
            return false
        }
        else {
            spnLoginIdType.setSelection(typePosition)
            etLoginIdNumber.setText(number)
            etLoginSurName.setText(name)
            tvLoginDateOfBirth.setText(date)
            spnLoginPlaceOfBirth.setSelection(placePosition)
            return true
        }
    }
}
