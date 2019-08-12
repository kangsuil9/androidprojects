package com.example.kangs.guestapp

class Chat_FoodModel {

    var main_image : Int = 0
    var main_name : String? = null
    var main_content : String? = null
    var main_lastdate : String? = null

    fun getMainImage(): Int {
        return main_image
    }
    fun setMainImage(main_image: Int) {
        this.main_image = main_image
    }

    fun getMainName(): String {
        return main_name.toString()
    }
    fun setMainName(main_name: String) {
        this.main_name = main_name
    }

    fun getMainContent(): String {
        return main_content.toString()
    }
    fun setMainContent(main_content: String) {
        this.main_content = main_content
    }

    fun getMainLastdate(): String {
        return main_lastdate.toString()
    }
    fun setMainLastdate(main_lastdate: String) {
        this.main_lastdate = main_lastdate
    }
}