package ru.korbit.cecommon.services;

import org.redisson.api.RFuture;
import org.redisson.api.annotation.RRemoteAsync;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@RRemoteAsync(RamblerKassaService.class)
public interface RamblerKassaAsyncService {

    RFuture<Void> load();
}
