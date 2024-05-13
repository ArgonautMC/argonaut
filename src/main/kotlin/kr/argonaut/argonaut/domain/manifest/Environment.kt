package kr.argonaut.argonaut.domain.manifest

enum class Environment(
    private val value: String,
    private vararg val alias: String,
) {
    DEVELOP("development", "dev"), PRODUCTION("production", "prod");

    companion object {
        fun fromString(str: String?): Environment {
            val error = lazy { IllegalArgumentException("$str is not environment value") }
            if(str == null) throw error.value

            return Environment.entries
                .find { it.value == str || it.alias.contains(str) }
                ?: throw error.value
        }
    }

    override fun toString(): String = value
}