package ds_clock_synch_Berkeley.server.machine;

import static ds_clock_synch_Berkeley.Config.AppConstants.formatter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import ds_clock_synch_Berkeley.Config.AppConstants;
import ds_clock_synch_Berkeley.server.ServerTime;
import ds_clock_synch_Berkeley.server.ServerTimeImpl;

/**
 * Representation of machine 2 to have its time adjusted.
 */
public class Machine2 {

	public static void main(String[] args) {
		try {
			LocalTime hour = LocalTime.parse(AppConstants.MACHINE_2_HOUR, formatter);
			ServerTime machineServer = new ServerTimeImpl(hour);
			Registry registry = LocateRegistry.createRegistry(AppConstants.SERVER_PORT_2);
			registry.rebind(ServerTimeImpl.class.getSimpleName(), machineServer);
			System.out.println(String.format("Machine 2 started on port %s [local time: %s].",
					AppConstants.SERVER_PORT_2,
					AppConstants.formatter.format(hour)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
