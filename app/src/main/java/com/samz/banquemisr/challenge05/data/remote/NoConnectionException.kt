package com.samz.banquemisr.challenge05.data.remote

import java.io.IOException

class NoConnectionException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Data connection"

}