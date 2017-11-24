package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CityEvent implements Serializable {
    @NonNull private Long event;
    @NonNull private Long city;
}
