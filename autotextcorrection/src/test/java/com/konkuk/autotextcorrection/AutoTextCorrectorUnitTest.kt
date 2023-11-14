package com.konkuk.autotextcorrection

import org.junit.Assert
import org.junit.Test

class AutoTextCorrectorUnitTest {

    private val targetTextList = listOf("칼로리", "나트륨", "단백질", "탄수화물")
    private val corrector = AutoTextCorrector(
        targetTextList = targetTextList,
        errorCorrectionDistance = 2,
    )

    @Test
    fun `destruct_corrector가 string을 char로 분해`() {
        Assert.assertEquals(
            targetTextList.map { corrector.destructWord(it) },
            listOf(
                "ㅋㅏㄹㄹㅗㄹㅣ",
                "ㄴㅏㅌㅡㄹㅠㅁ",
                "ㄷㅏㄴㅂㅐㄱㅈㅣㄹ",
                "ㅌㅏㄴㅅㅜㅎㅘㅁㅜㄹ",
            ),
        )
    }

    @Test
    fun `correct_corrector가 word를 수정`() {
        Assert.assertEquals(
            "나트륨",
            corrector.correctWord("니트륨"),
        )

        Assert.assertEquals(
            "탄수화물",
            corrector.correctWord("탐수화믈"),
        )
    }

    @Test
    fun `correct_corrector가 오류를 수정`() {
        Assert.assertEquals(
            "나트륨 정말 맛있어,ㅎㅎ 그리고 나는 탄수화물 매우 좋아하지!! 칼로리 너무 행복",
            corrector.correctText("니트륨 정말 맛있어,ㅎㅎ 그리고 나는 탐수화믈 매우 좋아하지!! 칼로ㅏ 너무 행복"),
        )
    }
}
