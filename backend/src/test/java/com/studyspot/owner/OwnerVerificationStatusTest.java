package com.studyspot.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OwnerVerificationStatusTest {

    @Test
    void parsesStatusIgnoringCase() {
        assertThat(OwnerVerificationStatus.from("pending")).isEqualTo(OwnerVerificationStatus.PENDING);
        assertThat(OwnerVerificationStatus.from("APPROVED")).isEqualTo(OwnerVerificationStatus.APPROVED);
    }
}
