package ds_clock_synch_Berkeley.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

/**
 * Interface for the client-side to access the server.
 */
public interface ServerTime extends Remote {

	/**
	 * @return a local time
	 */
	LocalTime getLocalTime() throws RemoteException;

	/**
	 * Adjusts local time based on server time with hourly average.
	 * 
	 * @param localTime server local time
	 * @param avgDiff   average hours
	 */
	void adjustTime(LocalTime localTime, long avgDiff) throws RemoteException;
}