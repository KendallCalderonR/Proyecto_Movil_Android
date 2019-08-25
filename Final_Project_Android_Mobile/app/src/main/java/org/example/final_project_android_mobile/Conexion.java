package org.example.final_project_android_mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Conexion {

        private static AdminDB BASE = null;
        private static Context context;

        private Conexion(){

        }

        public static void setContext(Context context) {
            Conexion.context = context;
        }

        private static AdminDB getBASE() {
            if (BASE==null) {
                BASE = new AdminDB(context,"datos",null,1);
            }
            return BASE;
        }


        public static SQLiteDatabase getLectura(){
            return getBASE().getReadableDatabase();
        }
        public static SQLiteDatabase getEscritura() {
            return getBASE().getWritableDatabase();
        }


}
