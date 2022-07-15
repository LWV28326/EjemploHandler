package fp.dam.ejemplohandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

// ------- VIDEO - 6 ---------------- HILO QUE MANEJA UNA COLA DE EVENTOS

// CREACION HILOS

//Handler  -->> se comunica con la cola de eventos gestinada por el lopper
// Looper -->> gestiona la cola de eventos, se crea al instanciar el hilo
// HanlderThread -->> hilo

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private Handler handler;
    private int contador;
    private Queue<Integer> queue= new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.textView);

        //PRIMERA FORMA DE HACERLO
        HandlerThread hilo= new HandlerThread("hiloUno");
        hilo.start(); //Entra en un bucle y el looper (ahora está esperando) es el encargado d despachar las tareas de la cola de eventos
        handler = new Handler(hilo.getLooper()); // Se comunica con la cola, inserta las tareas

            /*
        //OTRA FORMA -- RECIVIR MENSAJES A PARTE DE RUNNABLES
        handler = new Handler(hilo.getLooper()){
            @Override
            public void handleMessage(@NonNull Message message){

            }
        };*/

        //SEGUNDA FORMA COM LA CLASE CREADA
        MiHandlerThread hand = new MiHandlerThread("hiloNuevo");
        hand.start();
        handler = hand.getHand(); //Me devuelve el handler
    }


    public void hacerAlgo()  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

            //No hacerlo así
            //txt.setText("Tarea "+contador+" finalizada"); //No se puede hacer aquí porque no es el hilo principal
            //Se tiene que procurar que se ejecute en la UIThread, en la i terfaz de usuario

            //hacerlo Asi
            runOnUiThread(() -> txt.setText("Tarea "+queue.poll()+" finalizada"));
        }

    }


    public void onClickEjecutar (View v){
            queue.offer(contador);
            //METO LA TAREA QUE QUIERO REALIZAR
            /*handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });*/
            handler.post(this::hacerAlgo);
    }
}