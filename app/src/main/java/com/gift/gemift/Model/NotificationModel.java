package com.gift.gemift.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationModel implements Serializable {


        private ArrayList<Datum> data;

        public ArrayList<Datum> getData() {
                return data;
        }

        public void setData(ArrayList<Datum> data) {
                this.data = data;
        }
}
