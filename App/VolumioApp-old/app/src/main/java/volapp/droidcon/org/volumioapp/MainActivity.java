package volapp.droidcon.org.volumioapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //final Socket socket = IO.socket("http://connectivitycheck.gstatic.commm");
            final Socket socket = IO.socket("http://192.168.211.1");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Connected ...");
                    socket.emit("getState", "[]");
                    //socket.emit("next");
                    //socket.disconnect();
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Call ... something ");
                }

            }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Call ... something ");
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Disconnecting ...");
                }

            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Error ...");
                }

            }).on("pushState", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("TOQU", "Device Info ...");
                }
            });
            socket.connect();
        } catch (Exception ex) {
            Log.d("TOQU", "Exception: " + ex.getMessage());
        }
    }
}
