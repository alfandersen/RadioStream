package alf.stream.radiostream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import static alf.stream.radiostream.RadioStreamer.RadioStation.*;

/**
 * Created by Alf on 7/4/2017.
 */

public class RadioStreamer {

    public enum RadioStation {
        P1,P2,P3,P4,P5,P6,P7,P8,NEWS
    }

    private Activity parentActivity;
    private MediaPlayer player;
    private Map<RadioStation,String> stations;
    RadioStation currentStation;


    public RadioStreamer(Activity parentActivity){
        this.parentActivity = parentActivity;
//        setContentView(R.layout.activity_main);
        String[] urls = parentActivity.getResources().getStringArray(R.array.streams);
        stations = new HashMap<>();

        stations.put(NEWS, urls[0]);
        stations.put(P1, urls[1]);
        stations.put(P2, urls[2]);
        stations.put(P3, urls[3]);
        stations.put(P4, urls[4]);
        stations.put(P5, urls[5]);
        stations.put(P6, urls[6]);
        stations.put(P7, urls[7]);
        stations.put(P8, urls[8]);

        currentStation = P6;
    }



    private void updatePlayer(){
        player = MediaPlayer.create(parentActivity, Uri.parse(stations.get(currentStation)));

    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updatePlayer();
                player.start();
            }
        }).start();
    }

    public void stop(){
        if(player != null && player.isPlaying()) {
            player.stop();
        }
    }

    public void setStation(RadioStation station){
        if(station != currentStation && stations.containsKey(station)) {
            currentStation = station;
            if (player != null && player.isPlaying()) {
                stop();
                start();
            }
        }
    }

    public void setStation(int stationNo){
        switch (stationNo) {
            case 0: setStation(NEWS); break;
            case 1: setStation(P1); break;
            case 2: setStation(P2); break;
            case 3: setStation(P3); break;
            case 4: setStation(P4); break;
            case 5: setStation(P5); break;
            case 6: setStation(P6); break;
            case 7: setStation(P7); break;
            case 8: setStation(P8); break;
        }
    }
}
