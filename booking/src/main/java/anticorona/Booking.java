package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Date;

@Entity
@Table(name="Booking_table")
public class Booking {

    // @Autowired
    // BookingRepository bookingRepository;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer bookingId;
    private Integer vaccineId;
    private String vcName;
    private Integer userId;
    private String status;

    // @PostPersist
    // public void onPostPersist(){
    @PrePersist
    public void onPrePersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        // anticorona.external.Vaccine vaccine = new anticorona.external.Vaccine();
        // vaccine.setVaccineId(this.getVaccineId());
        // // mappings goes here
        // BookingApplication.applicationContext.getBean(anticorona.external.VaccineService.class)
        //     .chkAndModifyStock(vaccine);

        // (등록이벤트) http POST /bookings bookingId=1 vaccineId=1, vcName=모더나, userId=1
        // (bookingId=Auto, status=null)

        //System.out.println("\n\n##### listener Booking-onPostPersist1 ####\n\n");

        boolean rslt = BookingApplication.applicationContext.getBean(anticorona.external.VaccineService.class)
                .chkAndModifyStock(this.getVaccineId());
        
        //System.out.println("\n\n##### listener Booking-onPostPersist2 ####\n\n");
        
        if (rslt) { // 예약가능 상태 : 예약완료 처리
            System.out.println("\n\n##### listener Booking-onPostPersist3 ####\n\n");
            Booked booked = new Booked();
            this.setStatus("Reserved");
            BeanUtils.copyProperties(this, booked);
            booked.publishAfterCommit();

        } else { // 예약불가 상태 : 예약대기 처리
            System.out.println("\n\n##### listener Booking-onPostPersist4 ####\n\n");
            Booked booked = new Booked();
            this.setStatus("Wating");
            BeanUtils.copyProperties(this, booked);
            booked.publishAfterCommit();
        }

    }

    @PostUpdate
    public void onPostUpdate(){
    // @PreUpdate
    // public void onPreUpdate(){
        System.out.println("\n\n##### listener Booking-onPostUpdate1 ####\n\n");

        if(!this.getStatus().matches("Injected")) //Injected외 처리
        {
            //예약대기 건 예약완료 처리
            BookUpdated bookUpdated = new BookUpdated();
            this.setStatus("Reserved");
            BeanUtils.copyProperties(this, bookUpdated);
            bookUpdated.publishAfterCommit();
        }
    }

    //@PreUpdate
    @PreRemove
    public void onPreRemove(){
        System.out.println("\n\n##### listener Booking-PreRemove1 ####\n\n");
        BookCancelled bookCancelled = new BookCancelled();
        this.setStatus("Cancelled");
        BeanUtils.copyProperties(this, bookCancelled);
        bookCancelled.publishAfterCommit();

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
    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
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
