function doBooking(book, callback) {
    $.ajax({
        type: "POST",
        url: '/api/book',
        data: book,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateBooking(book, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/book',
        data: book,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function deleteBooking(book, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/book/' + book.id,
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

$(function() {
    var user_id = window.getCookie('rome_mate_user');

    function formatDate(date) {
        if(date)
            return date.substring(0, date.indexOf('T'));
        return '';
    }

    function formatTime(time) {
        if(time)
            return time.substring(0, time.lastIndexOf(':'));
        return '';
    }

    function editBook(book) {
        $('#res_place_id').val(book.id);
        $('#res_place').val(book.place.room.name + " / " + book.place.name);
        $('#res_date').val(formatDate(book.day));
        $('#res_from').val(book.from);
        $('#res_to').val(book.to);

        $('#reservationEditModal').modal('show');
    }

    function deleteBook(book) {
        if(!confirm("Would you like to delete this booking ?"))
            return;

        deleteBooking(book, () => {
            alert('Booking has deleted successfully');

            let targetRow = '#rsrv_' + book.id;
            $(targetRow).remove();
        });
    }

    function book() {
        let placeId = $('#book_place').val() || "";
        let date = $('#book_date').val();
        let from = window.fixApiTime($('#book_from').val());
        let to = window.fixApiTime($('#book_to').val());

        doBooking(
            JSON.stringify({ userId: user_id, placeId, date, from, to }),
            () => {
                alert('Space has booked successfully');

                $('#bookModal').modal('hide');
                $('#book_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/users/' + user_id + '/reservations',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#reservations_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#reservations_table tbody')
                      .append(
                        $('<tr id=\'rsrv_' + r.id + '\' scope=row>')
                            .append(
                                $('<td>')
                                    .append($('<p>').addClass('fw-bold mb-1').text(r.user.name))
                                    .append($('<p>').addClass('text-muted mb-0').text(r.user.githubEmail))
                            )
                            .append(
                                $('<td>')
                                    .append($('<p>').addClass('fw-normal mb-1').text(r.place.room.name))
                                    .append($('<p>').addClass('text-muted mb-0').text(r.place.name))
                            )
                            .append($('<td>').text(formatDate(r.day)))
                            .append($('<td>').text(formatTime(r.from)))
                            .append($('<td>').text(formatTime(r.to)))
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editBook(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteBook(r);
                                            })
                                    )
                            )
                      );
                });
            }
        });
    }

    $('#book_rooms').change(function () {
            let selectedRoom = this.value;
            $.ajax({
                url: '/api/data/rooms/' + selectedRoom + '/places',
                type: "GET",
                dataType: "json",
                success: function (data) {
                    let rooms = data;
                    $('#book_place').empty();
                    $.each(rooms, function(idx, place) {
                        $('#book_place')
                          .append(
                            $('<option>')
                              .text(place.name)
                              .attr('value', place.id)
                          );
                    });
                }
             });
        });

    $('#bookModal').on('show.bs.modal', function (e) {
         $.ajax({
            url: '/api/data/rooms',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let rooms = data;
                $('#book_rooms').empty();
                $.each(rooms, function(idx, room) {
                    $('#book_rooms')
                      .append(
                        $('<option>')
                          .text(room.name)
                          .attr('value', room.id)
                      );
                });
                $("#book_rooms").change();
            }
         });
    });

    $('#book_form').submit(function(event) {
        event.preventDefault();
        book();
    });

    $('#res_form').submit(function(event) {
        event.preventDefault();

        let bookId = $('#res_place_id').val();
        let date = $('#res_date').val();
        let from = window.fixApiTime($('#res_from').val());
        let to = window.fixApiTime($('#res_to').val());

        updateBooking(
            JSON.stringify({ bookId, date, from, to }),
            () => {
                alert('Booking has edited successfully');

                /*let targetRow = '#rsrv_' + book_id;
                $(targetRow).find('td').eq(2).text(date);
                $(targetRow).find('td').eq(3).text(formatTime(time_from));
                $(targetRow).find('td').eq(4).text(formatTime(time_to));*/
                reload();

                $('#reservationEditModal').modal('hide');
                $('#res_form').trigger("reset");
            }
        )
    });

    reload();
});