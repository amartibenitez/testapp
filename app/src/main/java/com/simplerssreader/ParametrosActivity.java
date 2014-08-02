    package com.simplerssreader;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.Intent;
    import android.content.pm.ActivityInfo;
    import android.os.Bundle;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.view.ViewGroup;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.TextView;
    import android.support.v4.app.Fragment;


    import com.theplatform.adk.Player;
    import com.theplatform.adk.PlayerError;
    import com.theplatform.util.log.debug.Debug;
import android.view.WindowManager;

    import java.net.URI;
    import java.net.URISyntaxException;

    public class ParametrosActivity extends Activity {
        private static final int OK_RESULT_CODE = 1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.full_player);
            try {
            //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Capturamos los objetos gráficos que vamos a usar
        //TextView text = (TextView) findViewById(R.id.fullscreen_content);
        //Button button = (Button) findViewById(R.id.dummy_button);
        //TextView params = (TextView) findViewById(R.id.params);


            final Player player = new Player((ViewGroup) this.findViewById(R.id.tpPlayer));


            final Activity activity = this;

            player.addErrorListener(
                    new Player.ErrorListener()
                    {
                        @Override
                        public void onError(PlayerError playerError)
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                            alertDialog.setTitle("Got an error");
                            alertDialog.setMessage("Error code:" + playerError.getPlayerErrorType());
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }
                    });









        //text.setText(R.string.cadena2);
    /*
        button.setText(R.string.salir);

        //Al pulsar el botón cerramos la ventana y volveremos a la anterior
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Cierra la actividad y la saca de la pila
                returnParams();
            }
        });*/

        // Mostramos los parámetros recibidos de la actividad mainActivity
        Bundle reicieveParams = getIntent().getExtras();
       // params.setText(reicieveParams.getString("videoTitle"));



            try
            {
                URI uri = new URI(reicieveParams.getString("param1"));
                player.loadReleaseUrl(uri);
                //player.playReleaseUrl(uri);
                player.asMediaPlayerControl().start();

            }
            catch (URISyntaxException e)
            {

                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Invalid URL");
                alertDialog.setMessage("You've entered an invalid URL");
                alertDialog.setCancelable(true);
                alertDialog.show();
            }



















    }
    catch (Exception Ex)
    {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

    // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(Ex.getMessage())
                .setTitle(R.string.dialog_title);

    // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
    }
        }

        protected void returnParams() {
            Intent intent = new Intent();
            intent.putExtra("result", "'Valor devuelto por ParametrosActivity'");
            setResult(OK_RESULT_CODE, intent);
            finish();
        }
}