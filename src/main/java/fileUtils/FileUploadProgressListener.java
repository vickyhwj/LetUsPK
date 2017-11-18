package fileUtils;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;
@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;
    public void setSession(HttpSession session){
        this.session=session;
        Progress status = new Progress();//±£´æÉÏ´«×´Ì¬
        session.setAttribute("uploadstate", status);
    }
    public void update(long bytesRead, long contentLength, int items) {
        Progress status = (Progress) session.getAttribute("uploadstate");
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);
        System.out.println("progress:"+(1.0*bytesRead/contentLength));

    }

}