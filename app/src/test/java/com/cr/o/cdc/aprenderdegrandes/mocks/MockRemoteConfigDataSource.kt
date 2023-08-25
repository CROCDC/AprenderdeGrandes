package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource

class MockRemoteConfigDataSource : RemoteConfigDataSource {
    override fun getForceUpdate(): Boolean = forceUpdate

    companion object {
        private var forceUpdate: Boolean = false
        fun setForceUpdate(forceUpdate: Boolean) {
            this.forceUpdate = forceUpdate
        }
    }
}