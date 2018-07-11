package lt.eofnet.relayswitch;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public interface RelayService {

    @GET("/devices")
    List<Device> getDevicesList();


    @GET("/turn/{port}/{state}")
    Result relaySwitch(@Path(value = "port") int port, @Path(value = "state") String state);

}