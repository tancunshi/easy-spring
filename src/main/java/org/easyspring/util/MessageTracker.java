package org.easyspring.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class MessageTracker {
    private static final Collection<String> tracks = new LinkedList<String>();

    public static void addTrack(String string){
        tracks.add(string);
    }

    public static Collection<String> getTracks(){
        return tracks;
    }

    public static void clear(){
        tracks.clear();
    }
}
