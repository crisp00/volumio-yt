package volapp.droidcon.org.volumioapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class VolumioQueue extends IntentService {
    enum STATE { INITIAL, CONNECTED, DISCONNECTED, ERROR};

    private static final String TAG = VolumioQueue.class.getName();
    static STATE CurrentState = STATE.INITIAL;
    static Socket socket = null;

    public static final String ACTION_OPEN_CONNECTION = "openConnection";
    public static final String ACTION_GET_STATE = "getState";
    public static final String ACTION_GET_QUEUE = "getQueue";

    public VolumioQueue() {
        super("VolumioQueue");
    }

    private void openConnection() {
        Log.d(TAG,"openConnection()");
        try {
            socket = IO.socket("http://192.168.1.162");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    CurrentState = STATE.CONNECTED;
                    SendCurrentStatus();
                    handleGetState();
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    CurrentState = STATE.DISCONNECTED;
                    SendCurrentStatus();
                }
            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (CurrentState == STATE.CONNECTED) {
                        CurrentState = STATE.DISCONNECTED;
                        SendCurrentStatus();
                    } else {
                        CurrentState = STATE.ERROR;
                        SendCurrentStatus();
                    }
                }
            }).on("pushState", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0 && args[0] instanceof JSONObject) {
                        Intent intent = new Intent("CONNECTION-STATUS-UPDATE");
                        intent.putExtra("status", "PUSH_STATE");
                        intent.putExtra("data", ((JSONObject)args[0]).toString());
                        LocalBroadcastManager.getInstance(VolumioQueue.this).sendBroadcast(intent);
                    }
                }
            }).on("pushQueue", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0) {
                        if (args[0] instanceof JSONArray) {
                            Intent intent = new Intent("CONNECTION-STATUS-UPDATE");
                            intent.putExtra("status", "PUSH_QUEUE");
                            intent.putExtra("data", ((JSONArray) args[0]).toString());
                            LocalBroadcastManager.getInstance(VolumioQueue.this).sendBroadcast(intent);
                        }
                    }
                }
            });
            socket.connect();
        } catch (Exception ex) {

        }
    }

    public static void startGetState(Context context) {
        Intent intent = new Intent(context, VolumioQueue.class);
        intent.setAction(ACTION_GET_STATE);
        context.startService(intent);
    }

    public static void openConnection(Context context) {
        Log.d(TAG,"openConnection");
        Intent intent = new Intent(context, VolumioQueue.class);
        intent.setAction(ACTION_OPEN_CONNECTION);
        context.startService(intent);
    }

    public static void startGetQueue(Context context) {
        Intent intent = new Intent(context, VolumioQueue.class);
        intent.setAction(ACTION_GET_QUEUE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"onHandleIntent()");
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
            case ACTION_GET_STATE:
                handleGetState();
                break;
            case ACTION_OPEN_CONNECTION:
                openConnection();
                break;
            case ACTION_GET_QUEUE:
                getQueue ();
                break;
            }
        }
    }

    private void handleGetState() {
        if (CurrentState == STATE.CONNECTED) {
            socket.emit("getState", "[]");
        } else {
            // Do something
        }
    }

    private void getQueue() {
        if (CurrentState == STATE.CONNECTED) {
            socket.emit("getQueue", "[]");
        } else {
            // Do something
        }
    }

    private void SendCurrentStatus() {
        Intent intent = new Intent("CONNECTION-STATUS-UPDATE");
        // add data
        switch (CurrentState) {
            case CONNECTED:
                intent.putExtra("status", "CONNECTED");
                break;
            case DISCONNECTED:
                intent.putExtra("status", "DISCONNECTED");
                break;
            case ERROR:
                intent.putExtra("status", "ERROR");
                break;
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
