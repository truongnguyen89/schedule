package com.football.schedule.service.booking;

import com.football.common.model.stadium.BookingLog;
import com.football.schedule.repository.BookingLogRepository;
import com.football.schedule.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingLogServiceIpml extends BaseService implements BookingLogService {
    @Autowired
    BookingLogRepository bookingLogRepository;

    @Override
    public List<BookingLog> findByStatus(int statusNew) throws Exception {
        return bookingLogRepository.findByStatusNew(statusNew);
    }

    @Override
    public List<BookingLog> findByBookingId(long bookingId) throws Exception {
        return bookingLogRepository.findByBookingId(bookingId);
    }
}
