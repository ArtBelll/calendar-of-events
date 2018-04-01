package ru.korbit.cecommon.domain;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CityEvent implements Serializable {
    @NonNull private Long event;
    @NonNull private Long city;
}
