package anticorona;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Vaccine_table")
public class Vaccine {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer vaccineId;
    private String vcName;
    private Integer stock=0;
    private Integer bookQty=0;

    @PostPersist
    public void onPostPersist(){
        // VcRegistered vcRegistered = new VcRegistered();
        // BeanUtils.copyProperties(this, vcRegistered);
        // vcRegistered.publishAfterCommit();

        //System.out.println("\n\n##### listener onPostPersist #### : id="+ this.getId().toString()+", vaccineId="+ this.getVaccineId().toString()+",stock="+ this.getStock().toString() + "\n\n");

        //http POST /vaccines vaccineId=1 vcName="모더나", stock=1
        VcRegistered vcRegistered = new VcRegistered();
        BeanUtils.copyProperties(this, vcRegistered);
        vcRegistered.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
        //http PATCH /vaccines/addVcStock vaccineId=1 stock=1

        //System.out.println("\n\n##### listener onPostUpdate #### : id="+ this.getId().toString()+", vaccinId="+ this.getVaccineId().toString()+", vcName="+ this.getVcName().toString()+",stock="+ this.getStock().toString()+",bookQty="+ this.getBookQty().toString() + "\n\n");
        
        StockModified stockModified = new StockModified();
        BeanUtils.copyProperties(this, stockModified);
        stockModified.publishAfterCommit();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getBookQty() {
        return bookQty;
    }

    public void setBookQty(Integer bookQty) {
        this.bookQty = bookQty;
    }




}
