package ru.korbit.ceramblerkasse;

import lombok.val;
import org.redisson.Redisson;
import org.redisson.config.Config;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.services.RamblerKassaService;

import java.io.IOException;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public class Main {

    public static void main(final String[] args) throws IOException {
        val configFile = Main.class.getClassLoader().getResourceAsStream("redisson.json");
        val config = Config.fromJSON( configFile);
        val redisson = Redisson.create(config);

        val service = redisson.getRemoteService(Constants.QUEUE_NAME);
        val ramblerKassaLoader = new RamblerKasseLoader();
        service.register(RamblerKassaService.class, ramblerKassaLoader, 2);
    }
}
