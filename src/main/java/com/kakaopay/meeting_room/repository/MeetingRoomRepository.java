package com.kakaopay.meeting_room.repository;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
