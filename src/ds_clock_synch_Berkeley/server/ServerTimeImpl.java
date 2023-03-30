package ds_clock_synch_Berkeley.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import ds_clock_synch_Berkeley.Config.AppConstants;

/**
 * Implementation of {@link ServerTime}.
 */
@SuppressWarnings("serial")
public class ServerTimeImpl extends UnicastRemoteObject implements ServerTime {

    private LocalTime localTime;

    public ServerTimeImpl(LocalTime localTime) throws RemoteException {
        this.localTime = localTime;
    }

    public LocalTime getLocalTime() throws RemoteException {
        return localTime;
    }

    public void adjustTime(LocalTime localTime, long avgDiff) throws RemoteException {
        long localTimeNanos = localTime.toNanoOfDay();
        long thisNanos = getLocalTime().toNanoOfDay();
        long newNanos = thisNanos - localTimeNanos;
        newNanos = newNanos * -1 + avgDiff + thisNanos;
        LocalTime newLocalTime = LocalTime.ofNanoOfDay(newNanos);
        this.localTime = newLocalTime;
        System.out.println("updated schedule: " + AppConstants.formatter.format(newLocalTime));
    }

}
