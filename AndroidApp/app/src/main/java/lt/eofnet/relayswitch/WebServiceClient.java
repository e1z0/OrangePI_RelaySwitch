package lt.eofnet.relayswitch;

import java.util.List;

import retrofit.RestAdapter;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public class WebServiceClient {

    public static final String RELAY_SERVER_ADDRESS = "http://192.168.254.102:1415";

    public List<Device> getDevicesList() throws Exception {
        try {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(RELAY_SERVER_ADDRESS)
                    .build();
            RelayService service = restAdapter.create(RelayService.class);
            return service.getDevicesList();
        } catch (Exception e) {
            throw e;
        }
    }

    public Result switchRelay(int state, int port) throws Exception {
        try {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(RELAY_SERVER_ADDRESS)
                    .build();
            RelayService service = restAdapter.create(RelayService.class);
            String switchas = "";
            switch(state) {
                case 1:
                    switchas = "off";
                    break;
                case 0:
                    switchas = "on";
                    break;
            }
            if (switchas != "") {
                System.out.println("SWITCH RELAY: " + "PORT: " + port + "; SWITCH: " + switchas);
                return service.relaySwitch(port,switchas); }
            else {
                System.out.println("Switch parameter state not specified!");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }



}

