package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Injection_table")
public class Injection {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Integer bookingId;
    private Integer vaccineId;
    private Integer userId;
    private String status;

    // @PostPersist
    // public void onPostPersist(){
    @PreUpdate
    public void onPreUpdate(){
        System.out.println("\n\n##### listener Injeciton-PreUpdate #### : bookingId="+ this.getBookingId().toString()+", Status="+ this.getStatus().toString() + "\n\n");

        VcCompleted vcCompleted = new VcCompleted();
        BeanUtils.copyProperties(this, vcCompleted);
        vcCompleted.setStatus("Injected");//접종완료 처리
        vcCompleted.publishAfterCommit();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
