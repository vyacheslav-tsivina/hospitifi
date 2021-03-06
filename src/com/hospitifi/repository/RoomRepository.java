package com.hospitifi.repository;

import com.hospitifi.model.Room;

import java.util.Calendar;
import java.util.List;

public interface RoomRepository extends Repository<Room, Long> {

    List<Room> findAvailableRooms(Calendar from, Calendar to);
}
