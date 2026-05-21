package com.studyspot.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CongestionLevelTest {

    @Test
    void calculatesLowMediumHighByOccupancyRate() {
        assertThat(CongestionLevel.calculate(3, 10)).isEqualTo(CongestionLevel.LOW);
        assertThat(CongestionLevel.calculate(5, 10)).isEqualTo(CongestionLevel.MEDIUM);
        assertThat(CongestionLevel.calculate(8, 10)).isEqualTo(CongestionLevel.HIGH);
    }
}
