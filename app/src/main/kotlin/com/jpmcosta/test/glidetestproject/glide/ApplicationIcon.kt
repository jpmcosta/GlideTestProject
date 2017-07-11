package com.jpmcosta.test.glidetestproject.glide

import android.net.Uri

class ApplicationIcon(val packageName: String) {

    companion object {

        const val URI_SCHEME_APPLICATION_ICON = "application-icon"


        fun createUri(packageName: String): Uri = Uri.fromParts(URI_SCHEME_APPLICATION_ICON, packageName, null)
    }
}