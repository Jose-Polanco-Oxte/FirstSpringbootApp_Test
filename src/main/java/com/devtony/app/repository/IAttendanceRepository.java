package com.devtony.app.repository;

import com.devtony.app.model.Attendance;
import com.devtony.app.model.EventInvitation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.invitation.user.id = :userId AND a.attended = true")
    Optional<List<Attendance>> getAttendanceByUserID(Long userId);

    @Query("SELECT a FROM Attendance a WHERE a.invitation.id = :invitationId")
    Optional<Attendance> getAttendance(@Param("invitationId") Long invitationId);


    @Modifying
    @Transactional
    @Query("UPDATE Attendance a SET a.attended = true, a.checkInTime = CURRENT_TIMESTAMP WHERE a.invitation.user.email = :userEmail AND a.attended = false")
    int checkIn(@Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("UPDATE Attendance a SET a.checkOutTime = CURRENT_TIMESTAMP WHERE a.invitation.user.email = :userEmail AND a.attended = true AND a.checkOutTime IS NULL")
    int checkOut(@Param("userEmail") String userEmail);

    @Query("SELECT i FROM EventInvitation i WHERE i.event.id = :eventId AND i.user.email = :userEmail AND i.status = 'ACCEPTED'")
    Optional<EventInvitation> findAcceptedInvitation(@Param("eventId") Long eventId, @Param("userEmail") String userEmail);
}