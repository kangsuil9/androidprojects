package com.example.kangs.guestapp

class Mytrip_FoodModel {

    var main_city: String? = null
    var sub_country: String? = null
    var sub_city: String? = null
    var sub_type: String? = null
    var sub_price: String? = null
    var sub_date: String? = null
    var main_icon : Int = 0
    var sub_food : Int = 0
    var sub_profile : Int = 0
    var sub_flag : Int = 0

    fun getMainCity(): String {
        return main_city.toString()
    }
    fun setMainCity(name: String) {
        this.main_city = name
    }

    fun getSubCountry(): String {
        return sub_country.toString()
    }
    fun setSubCountry(name: String) {
        this.sub_country = name
    }

    fun getSubCity(): String {
        return sub_city.toString()
    }
    fun setSubCity(name: String) {
        this.sub_city = name
    }

    fun getSubType(): String {
        return sub_type.toString()
    }
    fun setSubType(name: String) {
        this.sub_type = name
    }

    fun getSubPrice(): String {
        return sub_price.toString()
    }
    fun setSubPrice(name: String) {
        this.sub_price = name
    }

    fun getSubDate(): String {
        return sub_date.toString()
    }
    fun setSubDate(name: String) {
        this.sub_date = name
    }

    fun getMainIcon(): Int {
        return main_icon
    }
    fun setMainIcon(main_icon: Int) {
        this.main_icon = main_icon
    }

    fun getSubFood(): Int {
        return sub_food
    }
    fun setSubFood(sub_food: Int) {
        this.sub_food = sub_food
    }

    fun getSubProfile(): Int {
        return sub_profile
    }
    fun setSubProfile(sub_profile: Int) {
        this.sub_profile = sub_profile
    }

    fun getSubFlag(): Int {
        return sub_flag
    }
    fun setSubFlag(sub_flag: Int) {
        this.sub_flag = sub_flag
    }
}