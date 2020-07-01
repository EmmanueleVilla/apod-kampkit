package co.touchlab.kampstarter.redux

object SL {
    private val factoriesMap: HashMap<String, () -> Any> = HashMap()
    private val singletonsBuiltMap: HashMap<String, Any> = HashMap()
    private val singletonsMap: HashMap<String, () -> Any> = HashMap()

    operator fun <T : Any> get(type: T): T {
        return get(type, "")
    }

    operator fun <T : Any> get(type: T, name: String): T {
        if (isBaseRegistered(type, name)) {
            return baseGet(type, name)
        }
        if (isSingletonRegistered(type, name)) {
            return singletonGet(type, name)
        }
        throw IllegalArgumentException(
            "Don't know how to create an instance of " + buildName(
                type,
                name
            )
        )
    }
    
    fun <T : Any> register(type: T, factory: () -> Any) {
        register(type, "", factory)
    }

    fun <T : Any> register(type: T, name: String, factory: () -> Any) {
        unregister(type, name)
        factoriesMap[buildName(type, name)] = factory
    }

    fun <T : Any> registerSingleton(type: T, factory: () -> Any) {
        unregister(type, "")
        singletonsMap[buildName(type, "")] = factory
    }

    fun <T : Any> unregister(type: T) {
        unregister(type, "")
    }

    private fun <T : Any> unregister(type: T, name: String) {
        val key = buildName(type, name)
        factoriesMap.remove(key)
        singletonsMap.remove(key)
        singletonsBuiltMap.remove(key)
    }

    fun <T : Any> isRegistered(type: T): Boolean {
        return isBaseRegistered(type) || isSingletonRegistered(type)
    }

    fun clear() {
        factoriesMap.clear()
        singletonsMap.clear()
    }

    val registrationCount: Int
        get() = factoriesMap.size + singletonsMap.size

    private fun <T : Any> baseGet(type: T, name: String): T {
        val factory: () -> Any =
            factoriesMap[buildName(type, name)] as () -> Any
        return factory.invoke() as T
    }

    private fun <T : Any> singletonGet(type: T): T {
        return singletonGet(type, "")
    }

    private fun <T : Any> singletonGet(type: T, name: String): T {
        val key = buildName(type, name)
        if (!singletonsBuiltMap.containsKey(key)) {
            val factory: () -> Any = singletonsMap[key] as () -> Any
            val built: Any = factory.invoke()
            singletonsBuiltMap[key] = built
        }
        return singletonsBuiltMap[key] as T
    }

    private fun <T : Any> isBaseRegistered(type: T): Boolean {
        return isBaseRegistered(type, "")
    }

    private fun <T : Any> isBaseRegistered(type: T, name: String): Boolean {
        return factoriesMap.containsKey(buildName(type, name))
    }

    private fun <T : Any> isSingletonRegistered(type: T): Boolean {
        return isSingletonRegistered(type, "")
    }

    private fun <T : Any> isSingletonRegistered(type: T, name: String): Boolean {
        return singletonsMap.containsKey(buildName(type, name))
    }

    private fun <T : Any> buildName(type: T, name: String): String {
        return type::class.simpleName  + "_" + name
    }
}