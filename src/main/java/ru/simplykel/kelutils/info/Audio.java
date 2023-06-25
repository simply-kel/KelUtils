package ru.simplykel.kelutils.info;

import net.bjoernpetersen.volctl.VolumeControl;
import ru.simplykel.kelutils.config.UserConfig;

import java.io.IOException;

public class Audio {

    public static VolumeControl volumeControl;
    public Audio() throws IOException {
        volumeControl = new VolumeControl();
    }
    public static void upValue() {
        assert volumeControl != null;
        int value = volumeControl.getVolume() + UserConfig.SELECT_SYSTEM_VOLUME;
        if(value > 100) value = 100;
        volumeControl.setVolume(value);
    }
    public static void downValue(){
        assert volumeControl != null;
        int value = volumeControl.getVolume() - UserConfig.SELECT_SYSTEM_VOLUME;
        if(value < 0) value = 0;
        volumeControl.setVolume(value);
    }
    public static int getValue(){
        if(volumeControl == null) return -1;
        else return volumeControl.getVolume();
    }
}