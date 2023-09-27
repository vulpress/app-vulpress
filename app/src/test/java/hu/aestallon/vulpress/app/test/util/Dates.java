package hu.aestallon.vulpress.app.test.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;

public final class Dates {

  public static final ZoneId    TEST_ZONE = ZoneId.of("Z");
  public static final Instant   TEST_INST = LocalDateTime
      .of(
          LocalDate.of(2023, Month.SEPTEMBER, 1),
          LocalTime.of(10, 30))
      .atZone(TEST_ZONE)
      .toInstant();
  public static final LocalDate TODAY     = TEST_INST.atZone(TEST_ZONE).toLocalDate();
  public static final LocalDate YESTERDAY = TODAY.minusDays(1L);
  public static final LocalDate TOMORROW  = TODAY.plusDays(1L);

  private Dates() {}

}
