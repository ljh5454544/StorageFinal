package com.jia.storage.service;

import com.jia.storage.entity.Room;
import com.jia.storage.util.PageBean;

import java.util.List;

public interface RoomService {

    boolean addRoom(Room room);

    boolean removeRoom(Room room);

    boolean updateRoom(Room room);

    boolean removeRoom(List<Long> array);

    List<Room> getByPage(PageBean pageBean);

    Room getRoom(Room room);

    List<Room> getAllRoom();
}
