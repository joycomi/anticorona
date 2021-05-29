package anticorona;

import anticorona.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired BookingRepository bookingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverVcCompleted_UpdateStatus(@Payload VcCompleted vcCompleted){

        if(!vcCompleted.validate()) return;

        System.out.println("\n\n##### listener UpdateStatus : " + vcCompleted.toJson() + "\n\n");

        // Sample Logic //
        // Booking booking = new Booking();
        // bookingRepository.save(booking);

        Booking booking = bookingRepository.findByBookingId(vcCompleted.getBookingId());
        booking.setStatus(vcCompleted.getStatus());//Injected
        bookingRepository.save(booking);   
            
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCancelled_UpdateStatus(@Payload BookCancelled bookCancelled){
        //예약 취소가 발생하면, 예약대기가 있는 경우 상태 변경 처리
        if(!bookCancelled.validate()) return;

        System.out.println("\n\n##### listener UpdateStatus : " + bookCancelled.toJson() + "\n\n");

        // Sample Logic //
        // Booking booking = new Booking();
        // bookingRepository.save(booking);

        Iterable<Booking> bookings= bookingRepository.findAll();

        System.out.println("\n\n##### listener UpdateStatus-bookings findAll #### \n\n");

        for (Booking booking : bookings) {
            System.out.println("\n\n##### listener UpdateStatus-bookings-for #### \n\n");
            if(booking.getStatus().matches("Wating"))
            {
                System.out.println("\n\n##### listener UpdateStatus-bookings-for : bookingId="+ booking.getBookingId().toString()+", stock="+ booking.getStatus() +"\n\n");

                booking.setStatus("Reserved");
                System.out.println("\n\n##### listener UpdateStatus-bookings-for : bookingId="+ booking.getBookingId().toString()+", stock="+ booking.getStatus() +"\n\n");

                bookingRepository.save(booking);

                break;
            }
        }
            
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload Booking bookingRecv){
    //public void whatever(@Payload String eventString){
        
        // System.out.println("\n\n##### listener whatever #### \n\n");

        // Booking booking = bookingRepository.findByBookingId(bookingRecv.getBookingId());
        // booking.setStatus(bookingRecv.getStatus());
        // bookingRepository.save(booking);
    }


}
