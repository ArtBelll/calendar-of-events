package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import ru.korbit.cecommon.services.RamblerKassaService;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Slf4j
public class RamblerKasseLoader implements RamblerKassaService {

    @Override
    public void load() {
        log.debug("SUCCESS");
    }
}
