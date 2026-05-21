package com.studyspot.review;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CleanBot {

    private static final List<Pattern> BLOCKED_PATTERNS = List.of(
            Pattern.compile("(?i)fuck|shit|bitch"),
            Pattern.compile("(?i)idiot|stupid"),
            Pattern.compile("(?i)개새|씨발|시발|병신|꺼져|좆")
    );

    public CleanBotResult clean(String text) {
        String result = text == null ? "" : text.trim();
        boolean clean = true;

        for (Pattern pattern : BLOCKED_PATTERNS) {
            if (pattern.matcher(result).find()) {
                clean = false;
                result = pattern.matcher(result).replaceAll("***");
            }
        }

        return new CleanBotResult(result, clean);
    }
}
