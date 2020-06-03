# Brillio-meeting-room-booking-app

The application is Spring Boot based REST solution, provides APIs for managing meeting room bookings.


Installation Steps:
===================
1. Checkout the code into your IDE from the repository - https://github.com/tharakram/Brillio-meeting-room-booking-app
2. Use postman or Swagger for testing APIs


Build & Execution Steps:
========================
2. Build using Gradle
3. Change server port to 8080 in application.yml (optional)
4. Start the BookingServiceMain.main()
5. Test all APIs using this Swagger UI - http://localhost:26780/swagger-ui.html
Note: Remember to change the port number if you have updated it in your application.yml


Assumptions:
============
1. Data is designed as in-memory storage using stubs.
2. Assumed that the date and timeslot is out of scope for the current requirement.
3. Initially two meeting rooms have been added for testing purpose.
4. There are APIs for new rooms addition and deletion of existing rooms.
