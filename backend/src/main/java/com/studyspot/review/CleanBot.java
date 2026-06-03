package com.studyspot.review;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CleanBot {

    private static final List<Pattern> BLOCKED_PATTERNS = List.of(
            Pattern.compile("(?i)fuck|shit|bitch|idiot|stupid"),
            Pattern.compile("시\\s*발|씨\\s*발|ㅅ\\s*ㅂ|ㅆ\\s*ㅂ"),
            Pattern.compile("개\\s*(새끼|같|빡|소리)"),
            Pattern.compile("병\\s*신|븅\\s*신|미\\s*친|꺼\\s*져|닥\\s*쳐"),
            Pattern.compile("존\\s*나|졸\\s*라|염\\s*병")
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
