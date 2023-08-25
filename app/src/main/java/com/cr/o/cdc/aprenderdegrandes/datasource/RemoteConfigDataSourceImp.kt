package com.cr.o.cdc.aprenderdegrandes.datasource

import com.cr.o.cdc.aprenderdegrandes.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

interface RemoteConfigDataSource {
    fun getForceUpdate(): Boolean
}

class RemoteConfigDataSourceImp : RemoteConfigDataSource {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
        )
        setDefaultsAsync(R.xml.remote_config_defaults)
        activate()
    }

    override fun getForceUpdate(): Boolean = remoteConfig.getValue("FORCE_UPDATE").asBoolean()
}