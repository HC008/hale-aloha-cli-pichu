![http://www2.hawaii.edu/~dwilkie/machu_picchu_pichu.jpg](http://www2.hawaii.edu/~dwilkie/machu_picchu_pichu.jpg)
<br>
<font size='1'>Macchu-Picchu-Pichu, our beloved mascot</font>

Our objective is to develop and improve upon a command line interface (CLI) program to understand various aspects of energy use in the Hale Aloha residence halls.  This project will also be an assessment of the project contributors' abilities regarding Issue Driven Project Management, Continuous Integration, and testing.  More information can be found at this <a href='https://sites.google.com/site/ics314fall2011/modules/issue-driven-project-management'>website</a>.<br>
<br>
Users should follow the directions on the <a href='http://code.google.com/p/hale-aloha-cli-pichu/wiki/UserGuide'>UserGuide</a>.<br>
Developers should refer to the <a href='http://code.google.com/p/hale-aloha-cli-pichu/wiki/DeveloperGuide'>DeveloperGuide</a>.<br>
<br>
The following invokes the program and shows the output of the <code>help</code> and <code>quit</code> commands:<br>
<br>
<br>
<br>
<pre><code>bash-3.2$ java -jar hale-aloha-cli-pichu.jar
Connected successfully to the Hale Aloha WattDepot server.
&gt; help
ARGUMENTS:
  [tower | lounge] =&gt;
    [tower] = Mokihana | Ilima | Lehua | Lokelani
    [lounge] = Mokihana | Ilima | Lehua | Lokelani
      followed by "-" followed by A | B | C | D | E.
      For example: Mokihana-A
  [date] =&gt;
    The date in yyyy-mm-dd format.
    Must be BEFORE today's date.
  [start] =&gt;
    The starting date in yyyy-mm-dd format.
    Must be BEFORE [end], which is BEFORE today's date.
  [end] =&gt;
    The ending date in yyyy-mm-dd format.
    Must be AFTER [start] and BEFORE today's date.

COMMANDS:
Here are the available commands for this system.

current-power
  Usage: current-power [tower | lounge]
    Retrieves the current power of the particular source.

daily-energy
  Usage: daily-energy [tower | lounge] [date]
    Retrieves the energy consumed by the source from
    the date specified by the user.

energy-since
  Usage: energy-since [tower | lounge] [date]
    Retrieves the energy consumed by the source from the date
    specified by the user to the time of the latest sensor data.

rank-towers
  Usage: rank-towers [start] [end]
    Retrieves a list in sorted order from least to most energy consumed
    between the [start] and [end] dates specified by the user.

set-baseline
  Usage: set-baseline [tower | lounge] [date]
    This command defines [date] as the "baseline" day for tower or lounge.
    When this command is executed, the system should obtain and save the]
    amount of energy used during each of the 24 hours of that day for the
    given tower or lounge.  

monitor-goal
  Usage: monitor-goal [tower | lounge] [goal] [interval]
    Retrieves the power consumed by the source.


monitor-power
  Usage: monitor-power [tower | lounge] [interval]
    This command prints out a timestamp and the current power for
    [tower | lounge] every [interval] seconds.  [interval] is an optional
    integer greater than 0 and defaults to 10 seconds. Entering any character
    (such as a carriage return) stops this monitoring process and returns the
    user to the command loop.

help
  Usage: help
    Provides a brief description of each command and its usage.

quit
  Usage: quit
    Terminates execution.
&gt; quit
Concerned about energy and power?  You're awesome!  See ya!
</code></pre>