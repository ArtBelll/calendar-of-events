package ru.korbit.ceserver;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Data
@RequiredArgsConstructor
public class DateRange {
    @NonNull private LocalDate start;
    @NonNull private LocalDate finish;

    public static DateRange empty() {
        return new DateRange(LocalDate.ofEpochDay(0), LocalDate.ofEpochDay(0));
    }
}
