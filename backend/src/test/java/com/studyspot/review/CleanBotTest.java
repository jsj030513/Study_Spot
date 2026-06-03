package com.studyspot.review;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CleanBotTest {

    private final CleanBot cleanBot = new CleanBot();

    @Test
    void masksBlockedWords() {
        CleanBotResult result = cleanBot.clean("시발 너무 시끄러움");

        assertThat(result.clean()).isFalse();
        assertThat(result.cleanedText()).contains("***");
    }

    @Test
    void masksSpacedBlockedWords() {
        CleanBotResult result = cleanBot.clean("ㅅ ㅂ 여기 별로임");

        assertThat(result.clean()).isFalse();
        assertThat(result.cleanedText()).contains("***");
    }

    @Test
    void keepsCleanText() {
        CleanBotResult result = cleanBot.clean("조용하고 좋아요");

        assertThat(result.clean()).isTrue();
        assertThat(result.cleanedText()).isEqualTo("조용하고 좋아요");
    }
}
