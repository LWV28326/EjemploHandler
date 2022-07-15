package fp.dam.ejemplohandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;


//Enviar mensajes y tareas ejecutables

public class MiHandlerThread  extends HandlerThread {

    private Handler hand;

    public MiHandlerThread(String name) {
        super(name);
    }


    //Se usa para instanciar el Handler
    @Override
    protected void onLooperPrepared() {
        //Enviar tareas
        hand = new Handler(getLooper()){
            //para enviar mensajes
            @Override
            public void handleMessage(@NonNull Message msg) {

            }
        };

    }

    //Necesito el get que me devuelve el atributo handler
    public Handler getHand() {
        return hand;
    }
}
