package com.brillio.booking.service.stub;

import com.brillio.booking.service.model.*;
import com.brillio.booking.service.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingStub {

    private List<Room> roomList = new ArrayList<>();
    private List<Room> bookingList = new ArrayList<>();

    public BookingStub() {

        Room room1 = new Room();
        room1.setRoomId(20121);
        room1.setBuildingName("Trump Tower");
        room1.setFloorNo(20);
        room1.setRoomSize(8);
        room1.setRoomName("20-Octagon");
        room1.setBookingStart("2020-06-02 10:00:00");
        room1.setBookingEnd("2020-06-02 11:00:00");
        room1.setBookingRef("Res_0602_1011");
        room1.setBooked(false);

        Room room2 = new Room();
        room2.setRoomId(14121);
        room2.setBuildingName("Trump Tower");
        room2.setFloorNo(20);
        room2.setRoomSize(4);
        room2.setRoomName("14-Square");
        room2.setBookingStart("2020-06-02 15:00:00");
        room2.setBookingEnd("2020-06-02 17:00:00");
        room2.setBookingRef("Res_0602_1517");
        room2.setBooked(true);

        roomList.add(room1);
        roomList.add(room2);
    }

    /**
     *
     * @return
     */
    public List<Room> getRoomList() {
        return roomList;
    }

    /**
     * @return
     */
    public List<Room> getAvailableRoomList() {
        return (List<Room>) roomList.stream().filter(room -> {
            if(!room.isBooked()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Room> getAvailableRoomsByFilter(SearchCriteria criteria) {
        List<Room> rooms = roomList.stream().filter(room -> {
            if(room.getBuildingName().equalsIgnoreCase(criteria.getBuildingName())) {
                if (room.getRoomSize() == criteria.getRoomSize()) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());

        if (criteria.getFloorNo() > 0) {
            return rooms.stream().filter(r -> {
                if (criteria.getFloorNo() == r.getFloorNo()) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        }
        return rooms;
    }

    /**
     * @param roomId
     * @return
     */
    public BookingResponse bookRoom(long roomId) {
        Room roomToBook = roomList.stream().filter(room -> (room.getRoomId() == roomId)).findAny().orElse(null);
        BookingResponse br = new BookingResponse();
        if (roomToBook != null) {
            if (!roomToBook.isBooked()) {
                String bookingRef = "Res_" + roomId + "_" + CommonUtils.getDateForRef();
                br.setBookingStatus("Success");
                br.setBookingRef(bookingRef);
                br.setMessage("Congratulations! Your room " + roomId + " has been booked.");

                roomList.remove(roomToBook);
                roomToBook.setBooked(true);
                roomToBook.setBookingRef(bookingRef);

                roomList.add(roomToBook);
                bookingList.add(roomToBook);
                return br;
            } else {
                br.setBookingStatus("Booking failed");
                br.setMessage("The meeting room " + roomId + " has been already booked. Please reserve another room.");
                return br;
            }
        } else {
            br.setBookingStatus("Booking failed");
            br.setMessage("The meeting room with ID " + roomId + " does not exist.");
            return br;
        }
    }

    /**
     *
     * @param roomId
     * @return
     */
    public BookingResponse cancelBooking(long roomId) {

        Room roomToBook = roomList.stream().filter(room -> (room.getRoomId() == roomId)).findAny().orElse(null);
        BookingResponse br = new BookingResponse();
        if (roomToBook.isBooked()) {
            br.setBookingStatus("Success");
            br.setBookingRef("null");
            br.setMessage("Your booking " + roomToBook.getBookingRef() + " has been cancelled.");

            roomList.remove(roomToBook);
            roomToBook.setBooked(false);
            roomToBook.setBookingRef("null");

            roomList.add(roomToBook);
            bookingList.add(roomToBook);
            return br;
        } else {
            br.setBookingStatus("No action taken");
            br.setMessage("Your cancellation request cannot be completed. No reservations found for the room " + roomId + ".");
            return br;
        }
    }

    /**
     *
     * @param bookingRef
     * @return
     */
    public BookingResponse cancelBooking(String bookingRef) {

        Room room = roomList.stream().filter(bookedRoom -> (bookedRoom.getBookingRef().equalsIgnoreCase(bookingRef))).findAny().orElse(null);
        BookingResponse br = new BookingResponse();
        if (room != null && room.isBooked()) {
            br.setBookingStatus("Success");
            br.setBookingRef("null");
            br.setMessage("Your booking " + room.getBookingRef() + " has been cancelled.");

            bookingList.remove(room);
            roomList.remove(room);
            room.setBooked(false);
            room.setBookingRef("null");

            roomList.add(room);
            return br;
        } else {
            br.setBookingStatus("No action taken");
            br.setMessage("Your cancellation request cannot be completed. No reservations found for the booking reference " + bookingRef + ".");
            return br;
        }
    }

    /**
     *
     * @param room
     * @return
     */
    public Response addRoomToDatabase(Room room) {
        Response response = new Response();
        if (room.getRoomId() <= 0) {
            response.setStatus("Failed");
            response.setMessage("Room addition failed. Please provide valid room ID (number > 0).");
            return response;
        }
        if (StringUtils.isEmpty(room.getBuildingName())) {
            response.setStatus("Failed");
            response.setMessage("Room addition failed. Please provide valid Building Name.");
            return response;
        }
        if (StringUtils.isEmpty(room.getRoomName())) {
            response.setStatus("Failed");
            response.setMessage("Room addition failed. Please provide valid Room Name.");
            return response;
        }
        if (room.getRoomSize() != 4 && room.getRoomSize() != 8) {
            response.setStatus("Failed");
            response.setMessage("Room addition failed. Please provide valid Room Type (4 or 8 seats).");
            return response;
        }
        roomList.add(room);
        response.setStatus("Success");
        response.setMessage("Room " + room.getRoomId() + " has been added to the database.");
        return response;
    }

    /**
     *
     * @return
     */
    public List<Room> getAvailableRooms() {
        return (List<Room>) roomList.stream().filter(room -> {
            if(!room.isBooked()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param roomId
     * @return
     */
    public Response deleteRoomFromDatabase(long roomId) {
        Room room = roomList.stream().filter(r -> (r.getRoomId() == roomId)).findAny().orElse(null);
        Response res = new Response();
        if (room != null) {
            res.setStatus("Success");
            res.setMessage("Room " + roomId + " has been deleted successfully.");
            roomList.remove(room);
        } else {
            res.setStatus("Failed");
            res.setMessage("Room " + roomId + " not found in the database.");
        }
        return res;
    }
}
