package id.bnv.jupiter.pojo;

import java.io.Serializable;
import java.util.Date;

public class ForDecode implements Serializable {
    private final static long serialVersionUID=1L;

    public int userId;
    public Date dateIssue;
    public Date dateExpire;

    public ForDecode(int userId, Date dateIssue, Date dateExpire) {
        this.userId = userId;
        this.dateIssue = dateIssue;
        this.dateExpire = dateExpire;
    }
}
