/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class StatusReport {
    private String status;
    private Exception exception;

    public StatusReport() {
        this.status = null;
        this.exception = null;
    }

    public StatusReport(String status, Exception exception) {
        this.status = status;
        this.exception = exception;
    }

    public StatusReport(String status) {
        this.status = status;
        this.exception = null;
    }


    public void addException(Exception e){
        this.status="Exception";
        this.exception = e;
    }

    //region getter and setter


    public void setStatus(String status) {
        this.status = status;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getStatus() {
        return status;
    }

    public Exception getException() {
        return exception;
    }


    //endregion getter and setter
}
