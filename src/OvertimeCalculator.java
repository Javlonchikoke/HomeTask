import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class OvertimeCalculator {
    public static void main(String[] args) {

        LocalTime[] startTimes = {
                LocalTime.of(8, 0),   // Monday
                LocalTime.of(8, 0),   // Tuesday
                LocalTime.of(8, 0),   // Wednesday
                LocalTime.of(8, 0),   // Thursday
                LocalTime.of(8, 0)    // Friday
        };

        LocalTime[] endTimes = {
                LocalTime.of(17, 0),  // Monday
                LocalTime.of(17, 0),  // Tuesday
                LocalTime.of(17, 0),  // Wednesday
                LocalTime.of(17, 0),  // Thursday
                LocalTime.of(18, 30)  // Friday
        };

        int weeklyThreshold = 40;


        Duration regularHoursThreshold = Duration.ofHours(weeklyThreshold);
        Duration regularHoursWorked = Duration.ZERO;
        Duration overtimeHoursWorked = Duration.ZERO;


        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));


        for (int i = 0; i < startTimes.length; i++) {
            LocalTime startTime = startTimes[i];
            LocalTime endTime = endTimes[i];


            Duration dailyDuration = Duration.between(startTime, endTime);


            if (regularHoursWorked.plus(dailyDuration).compareTo(regularHoursThreshold) <= 0) {

                regularHoursWorked = regularHoursWorked.plus(dailyDuration);
            } else {

                Duration overtimeDuration = dailyDuration.minus(regularHoursThreshold.minus(regularHoursWorked));
                regularHoursWorked = regularHoursThreshold;
                overtimeHoursWorked = overtimeHoursWorked.plus(overtimeDuration);
            }
        }

        System.out.println("Total Regular Hours: " + regularHoursWorked.toHours());
        System.out.println("Total Overtime Hours: " + overtimeHoursWorked.toHours());
    }
}