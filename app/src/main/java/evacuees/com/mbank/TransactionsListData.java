package evacuees.com.mbank;

/**
 * Created by Deepak on 10-Aug-17.
 */

public class TransactionsListData {
    String Sendto, date, time, amount;
    String status;

    public TransactionsListData(String sendto, String date, String time, String amount, String status) {
        Sendto = sendto;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
