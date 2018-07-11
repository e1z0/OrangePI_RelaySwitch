package lt.eofnet.relayswitch;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.List;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public class MainActivity extends AppCompatActivity {

    private GetListTask mGetListTask = null;
    private LinearLayout listContainer;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listas);
        mContext = this;
        this.listContainer = (LinearLayout) this.findViewById(R.id.list_container);
        refreshList();
    }


    public void updateList(final List<Device> devices) {
        ListView lv = (ListView) findViewById(R.id.listas);
        DeviceArrayAdapter adapter = new DeviceArrayAdapter(MainActivity.this, devices);
        lv.setAdapter(adapter);
    }


    public void refreshList() {
        mGetListTask = new GetListTask(mContext);
        mGetListTask.execute((Void) null);
    }


    public class GetListTask extends AsyncTask<Void, Void, List<Device>> {

        private final Context mContext;

        GetListTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            listContainer.setVisibility(View.GONE);
        }

        @Override
        protected List<Device> doInBackground(Void... params) {
            System.out.println("Getting the list!");
            List<Device> l = null;
            System.out.println("Fetching data...");
            try {
                l = new WebServiceClient().getDevicesList();
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
            }
            return l;
        }

        @Override
        protected void onPostExecute(final List<Device> devices) {


            if (devices != null) {
                System.out.println("Success!");
                System.out.println(devices.toString());
                updateList(devices);

            } else {
                System.out.println("Error!");
            }
            mGetListTask = null;
            listContainer.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            mGetListTask = null;
            listContainer.setVisibility(View.VISIBLE);
        }
    }



}
