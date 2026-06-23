package com.example.paroli

import kotlin.random.Random

/**
 * Настройки, какие группы символов разрешено использовать в пароле.
 */
data class CharacterOptions(
    val useLowercase: Boolean,
    val useUppercase: Boolean,
    val useDigits: Boolean,
    val useSpecialChars: Boolean
) {
    fun hasAnyOptionSelected(): Boolean {
        return useLowercase || useUppercase || useDigits || useSpecialChars
    }
}

/**
 * Генерирует случайные пароли из набора символов, собранного
 * на основе выбранных пользователем настроек.
 */
class PasswordGenerator {

    companion object {
        private const val LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz"
        private const val UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val DIGIT_CHARS = "0123456789"
        private const val SPECIAL_CHARS = "!@#$%^&*"
    }

    /**
     * Собирает пул символов, доступных для генерации, на основе выбранных опций.
     */
    fun buildCharacterPool(options: CharacterOptions): String {
        val pool = StringBuilder()
        if (options.useLowercase) pool.append(LOWERCASE_CHARS)
        if (options.useUppercase) pool.append(UPPERCASE_CHARS)
        if (options.useDigits) pool.append(DIGIT_CHARS)
        if (options.useSpecialChars) pool.append(SPECIAL_CHARS)
        return pool.toString()
    }

    /**
     * Генерирует один пароль заданной длины из переданного пула символов.
     */
    fun generatePassword(length: Int, characterPool: String): String {
        require(characterPool.isNotEmpty()) { "Пул символов не может быть пустым" }

        return (1..length)
            .map { characterPool[Random.nextInt(characterPool.length)] }
            .joinToString(separator = "")
    }

    /**
     * Генерирует несколько паролей заданной длины (не обязательно уникальных).
     */
    fun generatePasswords(count: Int, length: Int, options: CharacterOptions): List<String> {
        val characterPool = buildCharacterPool(options)
        return (1..count).map { generatePassword(length, characterPool) }
    }
}
