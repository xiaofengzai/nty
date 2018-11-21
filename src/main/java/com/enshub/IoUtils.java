package com.enshub;

import java.io.Closeable;
import java.io.IOException;

public class IoUtils {
    public static void closeIoResource(Closeable resource){
        try {
            if(resource!=null){
                resource.close();
                resource=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
