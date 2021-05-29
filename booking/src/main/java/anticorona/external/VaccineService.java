
package anticorona.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

//@FeignClient(name="vaccine", url="http://vaccine:8080")
//@FeignClient(name="vaccine", url="${api.vaccine.url}")
@FeignClient(name="vaccine", url="http://localhost:8081")
public interface VaccineService {

    // @RequestMapping(method= RequestMethod.GET, path="/vaccines")
    // public void chkAndModifyStock(@RequestBody Vaccine vaccine);
    
    @RequestMapping(method= RequestMethod.GET, path="/vaccines/chkAndModifyStock")
    public boolean chkAndModifyStock(@RequestParam("vaccineId") int vaccineId);

}