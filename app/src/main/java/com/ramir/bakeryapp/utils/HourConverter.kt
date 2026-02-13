package com.ramir.bakeryapp.utils

class HourConverter {
    companion object{
        fun convert(hour:Int):String{
            if(hour >= 0 && hour <= 9){
                return "0$hour"
            }
            return hour.toString()
        }
    }
}