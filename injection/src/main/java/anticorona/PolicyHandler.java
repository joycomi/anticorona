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
    @Autowired InjectionRepository injectionRepository;
    @Autowired CancellationRepository cancellationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBooked_AcceptBooking(@Payload Booked booked){

        if(!booked.validate()) return;

        System.out.println("\n\n##### listener AcceptBooking-booked : " + booked.toJson() + "\n\n");

        if(booked.getStatus().matches("Reserved")){
            // Sample Logic //
            Injection injection = new Injection();
            // injectionRepository.save(injection);
        
            injection.setBookingId(booked.getBookingId()); //예약번호
            injection.setVaccineId(booked.getVaccineId()); //백신번호
            injection.setUserId(booked.getUserId()); //예약자번호
            injection.setStatus(booked.getStatus()); //상태정보
            injectionRepository.save(injection);
        }

        // Cancellation cancellation = new Cancellation();
        // cancellationRepository.save(cancellation);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCancelled_CancelAcceptBooking(@Payload BookCancelled bookCancelled){

        if(!bookCancelled.validate()) return;

        System.out.println("\n\n##### listener CancelAcceptBooking : " + bookCancelled.toJson() + "\n\n");

        // Sample Logic //
        // Injection injection = new Injection();
        // injectionRepository.save(injection);

        //BookingId 검색 후 삭제
        Injection injection = injectionRepository.findByBookingId(bookCancelled.getBookingId());
        injectionRepository.delete(injection);
        
        // Cancellation cancellation = new Cancellation();
        // cancellationRepository.save(cancellation);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCancelled_RegCancelBooking(@Payload BookCancelled bookCancelled){

        if(!bookCancelled.validate()) return;

        System.out.println("\n\n##### listener RegCancelBooking : " + bookCancelled.toJson() + "\n\n");

        Cancellation cancellation = new Cancellation();
        cancellation.setBookingId(bookCancelled.getBookingId()); //예약번호
        cancellation.setVaccineId(bookCancelled.getVaccineId()); //백신번호
        cancellation.setUserId(bookCancelled.getUserId()); //사용자번호
        cancellationRepository.save(cancellation);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookUpdated_AcceptBooking(@Payload BookUpdated bookUpdated){

        if(!bookUpdated.validate()) return;

        System.out.println("\n\n##### listener AcceptBooking-bookUpdated : " + bookUpdated.toJson() + "\n\n");

        if(bookUpdated.getStatus().matches("Reserved")){
            // Sample Logic //
            Injection injection = new Injection();
            // injectionRepository.save(injection);
        
            injection.setBookingId(bookUpdated.getBookingId()); //예약번호
            injection.setVaccineId(bookUpdated.getVaccineId()); //백신번호
            injection.setUserId(bookUpdated.getUserId()); //예약자번호
            injection.setStatus(bookUpdated.getStatus()); //상태정보
            injectionRepository.save(injection);
        }

        // Cancellation cancellation = new Cancellation();
        // cancellationRepository.save(cancellation);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
