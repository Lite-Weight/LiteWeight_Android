package com.konkuk.autotextcorrection

class AutoTextCorrector(
    targetTextList: List<String>,
    val errorCorrectionDistance: Int = 1,
) {

    private val targetWordsMap = mutableMapOf<String, String>()
    private val targetTextDestructedList = targetTextList.map { word ->
        val destructedWord = destructWord(word)
        targetWordsMap[destructedWord] = word
        destructedWord
    }

    fun destructWord(word: String): String {
        var result = StringBuilder()
        for (element in word) {
            var uniVal = element

            if (uniVal.code >= 0xAC00) {
                uniVal = (uniVal.code - 0xAC00).toChar()
                val cho = (uniVal.code / 28 / 21).toChar()
                val joong = (uniVal.code / 28 % 21).toChar()
                val jong = (uniVal.code % 28).toChar()
                result.append(INITIAL_CHARACTER[cho.code] + MID_CHARACTER[joong.code] + FINAL_CHARACTER[jong.code])
            } else {
                result.append(element)
            }
        }
        return String(result)
    }

    fun correctWord(word: String): String {
        if (word.isEmpty()) return word
        val destructedWord = destructWord(word)
        for (destructedChars in targetTextDestructedList) {
            val dp = List(destructedChars.length + 1) { IntArray(destructedWord.length + 1) }
            for (i in 1..destructedChars.length) {
                for (j in 1..destructedWord.length) {
                    if (destructedChars[i - 1] == destructedWord[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1] + 1
                    } else {
                        dp[i][j] = maxOf(dp[i - 1][j], dp[i][j - 1])
                    }
                }
            }
            if (abs(dp.last().last() - destructedChars.length) <= errorCorrectionDistance &&
                abs(dp.last().last() - destructedWord.length) <= errorCorrectionDistance
            ) {
                return targetWordsMap[destructedChars]!!
            }
        }
        return word
    }

    fun correctText(text: String): String {
        return text.split(" ").map { word -> correctWord(word) }.joinToString(" ")
    }

    private fun abs(n: Int) = if (n < 0) -n else n

    companion object {
        private val INITIAL_CHARACTER = listOf(
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ",
            "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ",
        )

        private val MID_CHARACTER = listOf(
            "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ",
            "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ",
        )

        private val FINAL_CHARACTER = listOf(
            "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ",
            "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ",
        )
    }
}
