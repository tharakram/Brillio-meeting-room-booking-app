package com.brillio.booking.service.controller;

import com.brillio.booking.service.model.*;
import com.brillio.booking.service.stub.BookingStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/")
public class BookingController {

    @Autowired
    private BookingStub bookingStub;

    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms() {
        return bookingStub.getRoomList();
    }

    //@PostMapping("/searchRooms")
    //public List<Room> searchRooms(@RequestBody Room room) {
    //    return bookingStub.getRoomList();
    //}

    @GetMapping("/book/{roomId}")
    public BookingResponse bookRoom(@PathVariable long roomId) {
        return bookingStub.bookRoom(roomId);
    }

    @GetMapping("/cancelBooking/{bookingRef}")
    public BookingResponse cancelBooking(@PathVariable String bookingRef) {
        return bookingStub.cancelBooking(bookingRef);
    }

    @PostMapping("/addRoomDetails")
    public Response addRoomToDatabase(@RequestBody Room room) {
        return bookingStub.addRoomToDatabase(room);
    }

    @PostMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms() {
        return bookingStub.getAvailableRooms();
    }

    //Lists all available meeting rooms (with id, name etc), given the meeting room type, building name, and floor (optional)
    @PostMapping("/getRoomsByCriteria")
    public List<Room> getAllRoomsByCriteria(@RequestBody SearchCriteria criteria) {
        if (criteria.getRoomSize() != 4 && criteria.getRoomSize() != 8) {
            return null;
        }
        if (StringUtils.isEmpty(criteria.getBuildingName())) {
            return null;
        }
        return bookingStub.getAvailableRoomsByFilter(criteria);
    }

    @PostMapping("/deleteRoom/{roomId}")
    public Response addRoomToDatabase(@PathVariable long roomId) {
        return bookingStub.deleteRoomFromDatabase(roomId);
    }

}
