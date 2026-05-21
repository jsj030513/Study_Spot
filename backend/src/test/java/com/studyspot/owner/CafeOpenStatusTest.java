package com.studyspot.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CafeOpenStatusTest {

    @Test
    void convertsOpenFlagToPublicStatus() {
        CafeOpenStatus open = new CafeOpenStatus("PLACE00000001", true, "영업 중", null);
        CafeOpenStatus closed = new CafeOpenStatus("PLACE00000001", false, "마감", null);

        assertThat(open.toResponse().status()).isEqualTo("OPEN");
        assertThat(closed.toResponse().status()).isEqualTo("CLOSED");
    }
}
