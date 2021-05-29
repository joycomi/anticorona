package anticorona;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypageRepository extends CrudRepository<Mypage, Long> {

    List<Mypage> findByBookingId(int bookingId);
    //예약번호(bookingId)로 MyPage 조회
}