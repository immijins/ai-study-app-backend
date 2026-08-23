package org.example.backend.domain.dday;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DdayResponse {
    private final Long id;
    private final String title;
    private final LocalDate dayDate;

    public DdayResponse(Dday dday) {
        this.id = dday.getId();
        this.title = dday.getTitle();
        this.dayDate = dday.getDayDate();
    }
}
