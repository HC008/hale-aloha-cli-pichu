
package edu.hawaii.halealohacli.command;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.client.WattDepotClient;
import edu.hawaii.halealohacli.Main;
import edu.hawaii.halealohacli.processor.Processor;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Retrieves the energy consumed by a source from the and. compare it with the baseline to see if we
 * reach the goal
 * 
 * @author Yong Hong Hsu
 */
public class MonitorGoal implements Command {
  private String tower;
  private WattDepotClient client;
  private String output;
  private double energySince;
  private int goal;
  private int interval;
  private double power;
  /**
   * The command energy-since takes 3 arguments.
   */
  public static final int ARGS = 3;

  /**
   * Creates a new instance of the energy-since command.
   */
  public MonitorGoal() {
    this.output = "";
  }

  /**
   * Creates a new instance of the energy-since command.
   * 
   * @param tower the tower or lounge specified
   * @param goal the goal
   * @param interval the time interval
   * @throws InvalidArgumentsException If the arguments supplied by the user are invalid.
   */
  public MonitorGoal(String tower, String goal, String interval) throws InvalidArgumentsException {
    if (this.checkArgs(tower, goal, interval)) {
      this.client = Main.CLIENT;
      this.tower = tower;
      this.goal = Integer.parseInt(goal);
      this.interval = Integer.parseInt(interval);
    }
    else {
      throw new InvalidArgumentsException();
    }
  }

  /**
   * Returns true if the arguments to monitor-goal are valid.
   * 
   * @param args the arguments to be checked for validity
   * @return true if all arguments are valid; false otherwise
   */
  public boolean checkArgs(String... args) {
    boolean valid = false;
    String lounge = "-[A-E]";
    if (((Processor.ILIMA).equals(args[0]) 
        || Pattern.matches(Processor.ILIMA + lounge, args[0])
        || (Processor.LEHUA).equals(args[0]) 
        || Pattern.matches(Processor.LEHUA + lounge, args[0])
        || (Processor.LOKELANI).equals(args[0])
        || Pattern.matches(Processor.LOKELANI + lounge, args[0])
        || (Processor.MOKIHANA).equals(args[0]) 
        || Pattern.matches(Processor.MOKIHANA + lounge, args[0]))
        && (Pattern.matches("[0-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", args[1]))
        && Pattern.matches("[0-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", args[2])) {
      valid = true;
    }

    return valid;
  }

  /**
   * Returns the value of the energy consumed since the date specified.
   * 
   * @return the double value of the energy consumed
   */
  public double getEnergyConsumed() {
    return this.energySince;
  }

  /**
   * Parses an XMLGregorianCalendar object of format "yyyy-MM-dd'T'HH:mm:ss.mls-HH:mm" (where the
   * last "-HH:mm" is simply the time zone) into "yyyy-MM-dd  HH:mm:ss.mls" and returns the parsed
   * result as a string.
   * 
   * @param dateTime the XMLGregorianCalendar object to parse
   * @return a string representation of the date and time
   */
  private String parseDateTime(XMLGregorianCalendar dateTime) {
    String dt = String.valueOf(dateTime);
    int indexT = dt.indexOf('T'); // 'T' separates the date from the time
    String parsedResult = dt.substring(0, indexT) + "  "; // Acquire the date "yyyy-MM-dd  "
    parsedResult += dt.substring(indexT + 1, dt.lastIndexOf('-')); // Add "HH:mm:ss.mls"
    return parsedResult;
  }

  /**
   * Checks if the tower name is actually a word instead of a date (e.g., 2011-10-31).
   * 
   * @return an integer value
   */
  public int checkTowerName() {
    try {
      SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      date.parse(this.tower);
      return 0;
    }
    catch (ParseException e) {
      return 1;
    }

  }

  /**
   * Runs this command. Saves the total energy consumed in kWh since the start date at 00:00:00.000
   * to the date and time of the latest sensor data.
   * 
   * @throws Exception If problems occur in retrieving data from WattDepot or the start date
   * provided by the user is after the date of the latest sensor data.
   */
  @Override
  public void run() throws Exception {
    SetBaseline baseline = new SetBaseline();
    double goalPower = 0;
    if (checkTowerName() == 0) {
      this.output = "Tower name invalid.";
    }
    else {
      double power = this.client.getLatestPowerConsumed(this.tower);
      power /= 1000;
      Calendar date = Calendar.getInstance(Locale.US);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.US);
      int hour = date.get(Calendar.HOUR_OF_DAY);
      goalPower =
          baseline.getEnergies()[hour] - (((double) (goal / 100)) * baseline.getEnergies()[hour]);
      this.output = this.tower + "'s power as of " + df.format(date.getTimeInMillis());
      this.output += " was " + String.format("%.1f", power) + " kW.";
      System.out.println("The goal power is: " + goalPower);
      if (power < goalPower) {
        System.out.println("The goal is reached.");
      }
      else {
        System.out.println("The goal is not reached.");
      }
    }
  }

  /**
   * Returns a string representation of the output of calling this command.
   * 
   * @return the output of calling this command
   */
  @Override
  public String getOutput() {
    return this.output;
  }

  /**
   * Retrieves a description of this command and its functionality.
   * 
   * @return a description of this command and its functionality
   */
  @Override
  public String getHelp() {
    String description = "monitor-goal\n";
    description += "  Usage: monitor-goal [tower | lounge] [goal] [interval]\n";
    description += "    Retrieves the power consumed by the source.\n";
    return description;
  }

  /**
   * Returns a time interval.
   * 
   * @return a time interval
   */
  public int getInterval() {
    return interval;
  }

  

  /**
   * get the power.
   * @return the power
   */
  public double getPower() {
    return power;
  }

  /**
   * set the power.
   * @param power the power
   */
  public void setPower(double power) {
    this.power = power;
  }

}
