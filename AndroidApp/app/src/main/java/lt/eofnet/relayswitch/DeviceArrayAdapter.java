package lt.eofnet.relayswitch;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;


/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public class DeviceArrayAdapter extends ArrayAdapter<Device> {

    private final MainActivity listActivity;
    private final List<Device> values;


    public DeviceArrayAdapter(MainActivity listActivity, List<Device> values) {
        super(listActivity, R.layout.list_row, values);
        this.listActivity = listActivity;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) listActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView relay_name = (TextView) rowView.findViewById(R.id.relay_name);
        final ImageButton SwitchButton = (ImageButton) rowView.findViewById(R.id.relay_switch);
        final Device cur = values.get(position);
        relay_name.setText(cur.GetName());
        if (cur.GetState() == 1) {
            SwitchButton.setImageResource(R.mipmap.ic_power_off);
        } else {
            SwitchButton.setImageResource(R.mipmap.ic_power_on);
        }

        SwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cur.GetState() == 1) {
                    // turn off power
                    cur.SetState(0);
                    SwitchButton.setImageResource(R.mipmap.ic_power_on);
                    new SwitchRelay(listActivity,cur.GetId(),0).execute();
                } else {
                    // turn on power
                    cur.SetState(1);
                    SwitchButton.setImageResource(R.mipmap.ic_power_off);
                    new SwitchRelay(listActivity,cur.GetId(),1).execute();
                }

            }
        });


        return rowView;
    }

    public class SwitchRelay extends AsyncTask<Void, Void, Result> {
        private final Context mContext;
        private final int mState;
        private final int mPort;

        SwitchRelay(Context context, int port, int state) {
            mContext = context;
            mState = state;
            mPort = port;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Result doInBackground(Void... params) {
            System.out.println("Switch relay API SENT");
            Result r = null;
            try {
                r = new WebServiceClient().switchRelay(mState,mPort);
            } catch (final Exception e) {
                e.printStackTrace();
                listActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new android.app.AlertDialog.Builder(mContext)
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
            }
            return r;
        }

        @Override
        protected void onPostExecute(final Result r) {
            System.out.println("Result: " + r);
            if (r != null && !r.getAnswer().isEmpty()) {
                System.out.println("Success: " + r);
            } else {
                System.out.println("Error!");
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

}
