<!DOCTYPE html>
<html>
  <head>
    <title>Calendar</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css"
    />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js"></script>
    <script>
      var userRole = '<%= request.getSession().getAttribute("userRole") %>';
      function clearLocalStorage() {
        localStorage.clear();
      }

      function clearSessionStorage() {
        sessionStorage.clear();
      }
      function clearCookies() {
  document.cookie.split(';').forEach(function(c) {
    document.cookie = c.replace(/^ +/, '').replace(/=.*/, '=;expires=' + new Date().toUTCString() + ';path=/');
  });
}

      function logout() {
        // Perform the cleanup
        clearCookies();
        clearLocalStorage();
        clearSessionStorage();

        // Redirect the user to the logout servlet
        window.location.href = "/TrabajoFinal/LogoutServlet";
      }
    </script>
  </head>
  <body>
    <nav>
      <!-- Add other navigation items here -->
      <button onclick="logout()" class="btn btn-outline-danger">Logout</button>
    </nav>
    <div id="calendar"></div>
    <script type="text/javascript">
      $(document).ready(function () {
        $("#calendar").fullCalendar({
          header: {
            left: "prev,next today",
            center: "title",
            right: "month,agendaWeek,agendaDay",
          },
          events: {
            url: "/TrabajoFinal/FetchEventsServlet",
          },
          eventRender: function (event, element) {
            element
              .find(".fc-title")
              .html(
                event.title +
                  " (Event ID: " +
                  event.eventId +
                  ", User ID: " +
                  event.userId +
                  ")"
              );
          },
          select:
            userRole === "manager"
              ? function (start, end) {
                  var title = prompt("Enter event title:");

                  if (title) {
                    var userId = prompt("Enter user ID:");

                    var eventData = {
                      title: title,
                      start: start,
                      end: end,
                      userId: userId,
                    };

                    $.ajax({
                      url: "/TrabajoFinal/CreateEventServlet",
                      method: "POST",
                      data: {
                        title: title,
                        start: start.toISOString(),
                        end: end.toISOString(),
                        userId: userId,
                      },
                      success: function (response) {
                        console.log("Event creation success:", response);
                        $("#calendar").fullCalendar("refetchEvents");
                      },
                      error: function (response) {
                        console.log("Event creation error:", response);
                      },
                    });
                  }

                  $("#calendar").fullCalendar("unselect");
                }
              : null,
          selectable: userRole === "manager",
          selectHelper: true,
          eventClick: function (event, jsEvent, view) {
            if (userRole === "manager") {
              var modal = $("<div>")
                .css({
                  position: "fixed",
                  top: "50%",
                  left: "50%",
                  transform: "translate(-50%, -50%)",
                  backgroundColor: "white",
                  padding: "20px",
                  zIndex: 1000,
                  boxShadow: "0 0 10px rgba(0, 0, 0, 0.2)",
                })
                .appendTo($("body"));

              $("<button>")
                .text("Edit")
                .css({
                  marginRight: "10px",
                })
                .click(function () {
                  var newTitle = prompt("Enter new event title:", event.title);
                  var newStart = prompt(
                    "Enter new start date and time (YYYY-MM-DD HH:MM):",
                    event.start.format("YYYY-MM-DD HH:mm")
                  );
                  var newEnd = prompt(
                    "Enter new end date and time (YYYY-MM-DD HH:MM):",
                    event.end ? event.end.format("YYYY-MM-DD HH:mm") : ""
                  );

                  $.ajax({
                    url: "/TrabajoFinal/UpdateEventServlet",
                    method: "POST",
                    data: {
                      eventID: event.eventID,
                      title: newTitle,
                      start: newStart,
                      end: newEnd,
                    },
                    success: function (response) {
                      console.log("Event update success:", response);
                      $("#calendar").fullCalendar("refetchEvents");
                    },
                    error: function (response) {
                      console.log("Event update error:", response);
                    },
                  });
                  modal.remove();
                })
                .appendTo(modal);

              $("<button>")
                .text("Delete")
                .click(function () {
                  $.ajax({
                    url: "/TrabajoFinal/DeleteEventServlet",
                    method: "POST",
                    data: {
                      eventId: event.eventId,
                    },
                    success: function (response) {
                      console.log("Event deletion success:", response);
                      $("#calendar").fullCalendar("refetchEvents");
                    },
                    error: function (response) {
                      console.log("Event deletion error:", response);
                    },
                  });
                  modal.remove();
                })
                .appendTo(modal);

              $("<button>")
                .text("Cancel")
                .css({
                  marginLeft: "10px",
                })
                .click(function () {
                  modal.remove();
                })
                .appendTo(modal);
            }
          },
        });
      });
    </script>
  </body>
</html>
