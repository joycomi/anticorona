package anticorona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

 @RestController
 public class VaccineController {

        @Autowired
        VaccineRepository vaccineRepository;

        // @RequestMapping(value = "/",
        //         method = RequestMethod.GET,
        //         produces = "application/json;charset=UTF-8")

        @RequestMapping(value = "/vaccines/chkAndModifyStock",
                method = RequestMethod.GET,
                produces = "application/json;charset=UTF-8")

        // public void chkAndModifyStock(HttpServletRequest request, HttpServletResponse response)
        //         throws Exception {
        //         System.out.println("##### /vaccine/chkAndModifyStock  called #####");
        //         }

        public boolean chkAndModifyStock(HttpServletRequest request, HttpServletResponse response)
                throws Exception {
                
                //System.out.println("##### /vaccine/chkAndModifyStock  called #####");

                boolean chkStock=false;

                int vaccineId = Integer.parseInt(request.getParameter("vaccineId"));

               // System.out.println("##### /vaccine/chkAndModifyStock :vaccineId="+ Integer.toString(vaccineId) +" #####");

                Vaccine vaccine = vaccineRepository.findByVaccineId(vaccineId);

                //System.out.println("##### /vaccine/chkAndModifyStock : vaccineId="+Integer.toString(vaccineId)+", stock="+vaccine.getStock().toString()+", bookQty="+vaccine.getBookQty().toString()+" #####");

                //재고수량 - 예약수량 0보다 크면 예약가능 상태
                if( vaccine.getStock() - vaccine.getBookQty() > 0){
                        //예약완료 가능 상태이면, 예약수량 증가 처리
                        vaccine.setBookQty(vaccine.getBookQty()+1);
                        vaccineRepository.save(vaccine);
                        
                        chkStock = true;
                }

                return chkStock;
        }


        @RequestMapping(value = "/vaccines/addVcStock",
        method = RequestMethod.PATCH,
        produces = "application/json;charset=UTF-8")

        @Transactional
        public void addVcStock(@RequestBody Vaccine vaccine)
        throws Exception {
                //int vaccineId = vaccine.getVaccineId();
                //int stock = vaccine.getStock();
                
                Vaccine vaccineOld = vaccineRepository.findByVaccineId(vaccine.getVaccineId());
                
                System.out.println("##### /vaccines/addVcStock : vaccineId="+vaccine.getVaccineId().toString()+", stock="+vaccine.getStock().toString()+", stcokOld="+vaccineOld.getStock().toString()+" #####");

                if(!vaccineOld.equals(null)){
                        vaccineOld.setStock(vaccine.getStock()+vaccineOld.getStock());
                        vaccineRepository.save(vaccineOld);
                }
        }


        
 }
