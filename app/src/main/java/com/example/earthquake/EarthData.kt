package com.example.earthquake

class EarthData {
    private var Mag: String
    private var City: String
    private var Date: Long
    private var Url: String

    constructor(s: String, s1: String, s2: Long, U: String) {
        Mag = s
        City = s1
        Date = s2
        Url = U
    }

    fun getMag(): String? {
        return Mag
    }

    fun getCity(): String? {
        return City
    }

    fun getDate(): Long {
        return Date
    }

    fun getUrl(): String? {
        return Url
    }
}
