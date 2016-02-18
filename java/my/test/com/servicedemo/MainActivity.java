package my.test.com.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent intent = null;
    private Intent intent_aidl=null;
    private MyService.MyBinder myBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MyService.MyBinder) iBinder;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MyService.class);
        intent_aidl = new Intent(this, MyRemoteService.class);
    }

    public void onClick(View view) throws RemoteException {
        switch (view.getId()) {
            case R.id.id_startService:
                startService(intent);
                break;
            case R.id.id_endService:
                stopService(intent);
                break;
            case R.id.id_bindService:
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.id_unbindService
                    :
                unbindService(serviceConnection);
                break;
            case R.id.id_bindremoteservice:
                bindService(intent_aidl, aidl_ServiceConnection, BIND_AUTO_CREATE);
//                String temp = myAidlInterface.SayHello("Hello AIDL");
//                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_unbindremoteservice:
                unbindService(aidl_ServiceConnection);
                break;
            default:
                break;

        }
    }

    private IMyAidlInterface myAidlInterface = null;
    ServiceConnection aidl_ServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("MainActivity", "ComponentName:"+componentName);
            myAidlInterface = (IMyAidlInterface.Stub.asInterface(iBinder)) ;
            if(myAidlInterface==null)
            Log.i("MainActivity", "myAidlInterface==null");
            else
            {
                try {
                    String temp=myAidlInterface.SayHello("fuck uuuuuuu!!!!!!");
                    Toast.makeText(MainActivity.this,temp,Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
