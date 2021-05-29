package anticorona;

import anticorona.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired VaccineRepository vaccineRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCancelled_ModifyStock(@Payload BookCancelled bookCancelled){

        if(!bookCancelled.validate()) return;

        System.out.println("\n\n##### listener ModifyStock-bookCancelled : " + bookCancelled.toJson() + "\n\n");

        // Sample Logic //
        // Vaccine vaccine = new Vaccine();
        // vaccineRepository.save(vaccine);

        Vaccine vaccine = vaccineRepository.findByVaccineId(bookCancelled.getVaccineId());
        vaccine.setBookQty(vaccine.getBookQty()-1); //예약취소 수량 1건 차감
        vaccineRepository.save(vaccine);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookUpdated_ModifyStock(@Payload BookUpdated bookUpdated){

        if(!bookUpdated.validate()) return;

        System.out.println("\n\n##### listener ModifyStock-bookUpdated : " + bookUpdated.toJson() + "\n\n");

        // Sample Logic //
        // Vaccine vaccine = new Vaccine();
        // vaccineRepository.save(vaccine);
        Vaccine vaccine = vaccineRepository.findByVaccineId(bookUpdated.getVaccineId());
        vaccine.setBookQty(vaccine.getBookQty()+1); //예약완료 수량 1건 증가
        vaccineRepository.save(vaccine);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverVcCompleted_ModifyStock(@Payload VcCompleted vcCompleted){

        if(!vcCompleted.validate()) return;

        System.out.println("\n\n##### listener ModifyStock-vcCompleted : " + vcCompleted.toJson() + "\n\n");

        // Sample Logic //
        // Vaccine vaccine = new Vaccine();
        // vaccineRepository.save(vaccine);
        Vaccine vaccine = vaccineRepository.findByVaccineId(vcCompleted.getVaccineId());
        vaccine.setStock(vaccine.getStock()-1); //재고 수량 1건 차감 
        vaccine.setBookQty(vaccine.getBookQty()-1); //예약완료 수량 1건 차감 
        vaccineRepository.save(vaccine);
            
    }
    // @StreamListener(KafkaProcessor.INPUT)
    // public void wheneverStockModified_ModifyStock(@Payload StockModified stockModified){

    //     //System.out.println("\n\n##### 0-listener ModifyStock (stockModified): vaccineId=" + stockModified.getVaccineId().toString() +",stock="+ stockModified.getStock().toString() + "\n\n");
    //     System.out.println("\n##### 1-listener ModifyStock (stockModified) : " + stockModified.toJson() + "\n");

    //     //if(!stockModified.validate()) return;

    // }


    @StreamListener(KafkaProcessor.INPUT)
    //public void whatever(@Payload String eventString){
    public void whatever(@Payload StockModified stockModified){
        
        // System.out.println("\n\n##### listener whatever : " + stockModified.toJson() + "\n\n");
        // System.out.println("\n\n##### listener whatever-getEventType() : " + stockModified.getEventType() + "\n\n");
        // //System.out.println("\n\n##### listener whatever : " + eventString + "\n\n");
        // // int vaccineId=0;
        // // int stock=0;

        // if(stockModified.getEventType().matches("StockModified")){

        //     System.out.println("\n\n##### listener whatever-StockModified #####\n\n");
        //     Vaccine vaccine = vaccineRepository.findByVaccineId(stockModified.getVaccineId());
        //     System.out.println("\n##### listener whatever (vaccine)1: vaccineId=" + vaccine.getVaccineId().toString() +",stock="+ vaccine.getStock().toString() + "\n");
        //     System.out.println("\n##### 2-listener ModifyStock (stockModified): vaccineId=" + stockModified.getVaccineId().toString() +",stock="+ stockModified.getStock().toString() + "\n");

        //     vaccine.setStock(vaccine.getStock() + stockModified.getStock()); //현수량+ 추가 수량
        //     System.out.println("\n##### listener whatever (vaccine)2: vaccineId=" + vaccine.getVaccineId().toString() +",stock="+ vaccine.getStock().toString() + "\n");
        //     vaccineRepository.save(vaccine);
        //  }

    }


}
