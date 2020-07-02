package co.touchlab.kampstarter.redux

object SL {
    private val factoriesMap: HashMap<InjectionTypes, () -> Any> = HashMap()
    private val singletonsBuiltMap: HashMap<InjectionTypes, Any> = HashMap()
    private val singletonsMap: HashMap<InjectionTypes, () -> Any> = HashMap()

    operator fun <T> get(name: InjectionTypes): T {
        if (isBaseRegistered(name)) {
            return baseGet(name) as T
        }
        if (isSingletonRegistered(name)) {
            return singletonGet(name) as T
        }
        throw IllegalArgumentException(
            "Don't know how to create an instance of " + name
        )
    }
    
    fun <T : Any> set(name: InjectionTypes, factory: () -> Any) {
        unset<T>(name)
        factoriesMap[name] = factory
    }

    fun <T : Any> setSingle(name: InjectionTypes, factory: () -> Any) {
        unset<T>(name)
        singletonsMap[name] = factory
    }

    private fun <T : Any> unset(name: InjectionTypes) {
        val key = name
        factoriesMap.remove(key)
        singletonsMap.remove(key)
        singletonsBuiltMap.remove(key)
    }

    private fun baseGet(name: InjectionTypes): Any {
        val factory: () -> Any =
            factoriesMap[name] as () -> Any
        return factory.invoke()
    }

    private fun singletonGet(name: InjectionTypes): Any {
        if (!singletonsBuiltMap.containsKey(name)) {
            val factory: () -> Any = singletonsMap[name] as () -> Any
            val built: Any = factory.invoke()
            singletonsBuiltMap[name] = built
        }
        return singletonsBuiltMap[name]!!
    }

    private fun isBaseRegistered(name: InjectionTypes): Boolean {
        return factoriesMap.containsKey(name)
    }

    private fun isSingletonRegistered(name: InjectionTypes): Boolean {
        return singletonsMap.containsKey(name)
    }
}