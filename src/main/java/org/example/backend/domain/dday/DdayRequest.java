package org.example.backend.domain.dday;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DdayRequest {
    private String title;
    private LocalDate dayDate;
}
