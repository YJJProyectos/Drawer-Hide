package com.example.jyang.navigationdrawer.activities;

import android.app.ActionBar;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.handlers.NotificactionHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class CuartaActivity extends AppCompatActivity {

    @BindView(R.id.edit_notificacion_titulo)
    EditText editTextTitulo;
    @BindView(R.id.edit_notificacion_mensaje)
    EditText editTextMensaje;
    @BindView(R.id.edit_notificacion_switch)
    Switch switchImportance;
    @BindView(R.id.edit_notificacion_boton)
    Button botonEnviar;

    private NotificactionHandler notificactionHandler;

    private int counter = 0; // para agrupar

    private boolean isHighImportance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cuarta");
        ButterKnife.bind(this);
        /*switchImportance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        }); */
        notificactionHandler = new NotificactionHandler(this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = getWindow();
            //w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    @OnClick(R.id.edit_notificacion_boton)
    public void click() {
        sendNotificacion();
    }
    @OnCheckedChanged(R.id.edit_notificacion_switch)
    public void change(CompoundButton buttonView, boolean isChecked) {
        isHighImportance = isChecked;
        if ( isChecked ) {
            switchImportance.setText(R.string.notificacion_switch_alta);
        } else {
            switchImportance.setText(R.string.notificacion_switch_baja);
        }
        //Toast.makeText(this, "checked " + isHighImportance, Toast.LENGTH_SHORT).show();
    }

    private void sendNotificacion() {
        //Toast.makeText(this, "send", Toast.LENGTH_SHORT).show();
        String titulo = editTextTitulo.getText().toString();
        String mensaje = editTextMensaje.getText().toString();

        if (!TextUtils.isEmpty(titulo) && !TextUtils.isEmpty(mensaje)) {
            Notification.Builder nb = notificactionHandler.createNotification(titulo, mensaje, isHighImportance);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nb.setColor(getColor(R.color.colorPrimary));
            }*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notificactionHandler.getManager().notify(++counter, nb.build());
                notificactionHandler.publishNotificationSummaryGroup(isHighImportance);
            }
        }
    }
}
