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
public class RangeDate {
    @NonNull private LocalDate start;
    @NonNull private LocalDate finish;

    public static RangeDate empty() {
        return new RangeDate(LocalDate.ofEpochDay(0), LocalDate.ofEpochDay(0));
    }
}
