package evacuees.com.mbank;

import android.widget.ImageView;

/**
 * Created by Deepak on 10-Aug-17.
 */

public class TransactionsListData {
    String Sendto,date,time,amount;
    ImageView img;

    public TransactionsListData(String sendto, String date, String time, String amount, ImageView img) {
        Sendto = sendto;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.img = img;
    }

    public String getSendto() {
        return Sendto;
    }

    public void setSendto(String sendto) {
        Sendto = sendto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
}
