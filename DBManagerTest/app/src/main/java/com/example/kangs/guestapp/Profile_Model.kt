package com.example.kangs.guestapp

class Profile_Model {

    var main_image : Int = 0
    var main_name : String? = null

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
}