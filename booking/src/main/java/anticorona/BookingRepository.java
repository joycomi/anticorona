package anticorona;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="bookings", path="bookings")
public interface BookingRepository extends PagingAndSortingRepository<Booking, Integer>{

    Booking findByBookingId(int bookingId);
}
