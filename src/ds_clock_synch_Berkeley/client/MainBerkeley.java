package ds_clock_synch_Berkeley.client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import ds_clock_synch_Berkeley.Config.AppConstants;
import static ds_clock_synch_Berkeley.Config.AppConstants.formatter;
import ds_clock_synch_Berkeley.server.ServerTime;
import ds_clock_synch_Berkeley.server.ServerTimeImpl;

/**
 * Client-side
 */
public class MainBerkeley {

    public static void main(String[] args) {
        try {
            LocalTime localTime = LocalTime.parse(AppConstants.LOCAL_HOUR, formatter);
            System.out.println("Local time: " + formatter.format(localTime));

            // creation of servers (machines)
            ServerTime machine1Server = createMachineServer(1500);
            ServerTime machine2Server = createMachineServer(1501);
            ServerTime machine3Server = createMachineServer(1502);

            // calculate the average of hours
            long avgDiff = generateAverageTime(localTime,
                    machine1Server.getLocalTime(),
                    machine2Server.getLocalTime(),
                    machine3Server.getLocalTime());

            // adjust server time
            machine1Server.adjustTime(localTime, avgDiff);
            machine2Server.adjustTime(localTime, avgDiff);
            machine3Server.adjustTime(localTime, avgDiff);
            localTime = localTime.plusNanos(avgDiff);

            System.out.println("\nupdated hours!");
            System.out.println("Local time: " + formatter.format(localTime));
            System.out.println("server time 1: " + formatter.format(machine1Server.getLocalTime()));
            System.out.println("server time 2: " + formatter.format(machine2Server.getLocalTime()));
            System.out.println("server time 3: " + formatter.format(machine3Server.getLocalTime()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static ServerTime createMachineServer(int machineNumber) throws Exception {
        String serverName = AppConstants.SERVER_NAME;
        int serverPort=machineNumber;
        Registry machineRegistry = LocateRegistry.getRegistry(serverName, serverPort);
        ServerTime machineServerTime = (ServerTime) machineRegistry.lookup(ServerTimeImpl.class.getSimpleName());
        LocalTime machineTime = machineServerTime.getLocalTime();
        System.out.println("Connection with the machine " + machineNumber + "successfully established. Hour: "
                + formatter.format(machineTime));
        return machineServerTime;
    }

    private static long generateAverageTime(LocalTime localTime, LocalTime... times) {
        long nanoLocal = localTime.toNanoOfDay();
        long difServer = 0;
        for (LocalTime t : times) {
            difServer += t.toNanoOfDay() - nanoLocal;
        }
        return difServer / times.length;
    }

}
