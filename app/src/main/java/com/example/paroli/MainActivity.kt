package com.example.paroli

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MIN_LENGTH = 6
        private const val MAX_LENGTH = 20
        private const val DEFAULT_LENGTH = 12

        private const val MIN_COUNT = 1
        private const val MAX_COUNT = 15
        private const val DEFAULT_COUNT = 1
    }

    private lateinit var etLength: EditText
    private lateinit var etCount: EditText
    private lateinit var cbLowercase: CheckBox
    private lateinit var cbUppercase: CheckBox
    private lateinit var cbDigits: CheckBox
    private lateinit var cbSpecialChars: CheckBox
    private lateinit var tvResult: TextView

    private val passwordGenerator = PasswordGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        val btnGenerate: Button = findViewById(R.id.btnGenerate)
        btnGenerate.setOnClickListener {
            onGenerateClicked()
        }
    }

    private fun bindViews() {
        etLength = findViewById(R.id.etLength)
        etCount = findViewById(R.id.etCount)
        cbLowercase = findViewById(R.id.cbLowercase)
        cbUppercase = findViewById(R.id.cbUppercase)
        cbDigits = findViewById(R.id.cbDigits)
        cbSpecialChars = findViewById(R.id.cbSpecialChars)
        tvResult = findViewById(R.id.tvResult)

        etLength.setText(DEFAULT_LENGTH.toString())
        etCount.setText(DEFAULT_COUNT.toString())
    }

    private fun onGenerateClicked() {
        val length = etLength.text.toString().trim().toIntOrNull()
        val count = etCount.text.toString().trim().toIntOrNull()

        if (length == null || count == null) {
            showError("Введите корректные целые числа в поля длины и количества")
            return
        }

        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            showError("Длина пароля должна быть от $MIN_LENGTH до $MAX_LENGTH")
            return
        }

        if (count < MIN_COUNT || count > MAX_COUNT) {
            showError("Количество паролей должно быть от $MIN_COUNT до $MAX_COUNT")
            return
        }

        val options = readCharacterOptions()
        if (!options.hasAnyOptionSelected()) {
            showError("Выберите хотя бы один тип символов")
            return
        }

        val passwords = passwordGenerator.generatePasswords(count, length, options)
        displayResult(passwords)
    }

    private fun readCharacterOptions(): CharacterOptions {
        return CharacterOptions(
            useLowercase = cbLowercase.isChecked,
            useUppercase = cbUppercase.isChecked,
            useDigits = cbDigits.isChecked,
            useSpecialChars = cbSpecialChars.isChecked
        )
    }

    private fun displayResult(passwords: List<String>) {
        tvResult.text = passwords.joinToString(separator = "\n")
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
