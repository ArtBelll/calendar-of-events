package ru.korbit.ceramblerkasse;

import lombok.val;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.services.RamblerKassaService;

import java.io.IOException;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Component
public class Main {

    private final RedissonClient redisson;

    private final RamblerKassaService ramblerKassa;

    @Autowired
    public Main(RedissonClient redisson, RamblerKassaService ramblerKassa) {
        this.redisson = redisson;
        this.ramblerKassa = ramblerKassa;
    }

    public static void main(final String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("rambler-kassa-service.xml");
        Main main = ctx.getBean(Main.class);

        if (args.length > 0 && args[1].equals("dev")) {
            Environment.host = Environment.PROXY;
        }

        val service = main.redisson.getRemoteService(Constants.QUEUE_NAME);
        service.register(RamblerKassaService.class, main.ramblerKassa, 2);
    }
}
