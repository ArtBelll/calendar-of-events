import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.korbit.cecommon.services.TimeZoneDb;

import java.time.ZoneOffset;

/**
 * Created by Artur Belogur on 13.11.17.
 */
public class TimeZoneDbTest {

    private final static Float LAT_KALININGRAD = 54.7104f;
    private final static Float LNG_KALININGRAD = 20.4522f;

    private final static int OFFSET_KALININGRAD = 2;

    @Test
    public void getZone() {
        val timeZone = new TimeZoneDb();

        val offsetTimeZone = timeZone.getZoneOffsetByLatlng(LAT_KALININGRAD, LNG_KALININGRAD);
        Assert.assertTrue(offsetTimeZone.equals(ZoneOffset.ofHours(OFFSET_KALININGRAD)));
    }
}
