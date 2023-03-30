package ds_clock_synch_Berkeley.server.machine;

import ds_clock_synch_Berkeley.Config.AppConstants;
import static ds_clock_synch_Berkeley.Config.AppConstants.formatter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import ds_clock_synch_Berkeley.server.ServerTime;
import ds_clock_synch_Berkeley.server.ServerTimeImpl;

/**
 * Representation of machine 1 to have its time adjusted.
 */
public class Machine1 {

	public static void main(String[] args) {
		try {
			LocalTime hour = LocalTime.parse(AppConstants.MACHINE_1_HOUR, formatter);
			ServerTime machineServer = new ServerTimeImpl(hour);
			Registry registry = LocateRegistry.createRegistry(AppConstants.SERVER_PORT_1);
			registry.rebind(ServerTimeImpl.class.getSimpleName(), machineServer);
			System.out.println(String.format("Machine 1 started on port %s [local time: %s].",
					AppConstants.SERVER_PORT_1,
					AppConstants.formatter.format(hour)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
