package my.test.com.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract;

public class MyRemoteService extends Service {

    public MyRemoteService() {
    }

    IMyAidlInterface.Stub myAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public String SayHello(String world) throws RemoteException {
            return world;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myAidlInterface;
    }
}
