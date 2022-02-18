package com.guango.friends.base

import java.util.concurrent.ConcurrentHashMap

object ServiceManager {

    private val mServiceStore = ConcurrentHashMap<Class<in IService>, IService>()

    fun registerService(clazz: Class<in IService>, service: IService) {
        mServiceStore.put(clazz, service)
    }

    fun getService(clazz: Class<in IService>): IService?{
        return mServiceStore.get(clazz)
    }
}
