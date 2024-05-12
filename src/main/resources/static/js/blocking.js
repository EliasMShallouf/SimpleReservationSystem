function doBlocking(block, callback) {
    $.ajax({
        type: "POST",
        url: '/api/block',
        data: block,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateBlocking(block, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/block',
        data: block,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function deleteBlocking(block, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/block/' + block.id,
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

    function editBlock(block) {
        $('#block_place_id').val(block.id);
        $('#block_place').val(block.place.room.name + " / " + block.place.name);
        $('#block_date').val(formatDate(block.day));
        $('#block_from').val(block.from);
        $('#block_to').val(block.to);

        $('#blockEditModal').modal('show');
    }

    function deleteBlock(block) {
        if(!confirm("Would you like to unblock this space ?"))
            return;

        deleteBlocking(block, () => {
            alert('Unblocking successfully');

            let targetRow = '#blk_' + block.id;
            $(targetRow).remove();
        });
    }

    function block() {
        let placeId = $('#book_place').val() || "";
        let date = $('#book_date').val();
        let from = window.fixApiTime($('#book_from').val());
        let to = window.fixApiTime($('#book_to').val());

        doBlocking(
            JSON.stringify({ userId: user_id, placeId, date, from, to }),
            () => {
                alert('Space has blocked successfully');

                $('#blockModal').modal('hide');
                $('#block_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/users/' + user_id + '/blocks',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#block_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#block_table tbody')
                      .append(
                        $('<tr id=\'blk_' + r.id + '\' scope=row>')
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
                                                editBlock(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteBlock(r);
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

    $('#blockModal').on('show.bs.modal', function (e) {
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

    $('#block_form').submit(function(event) {
        event.preventDefault();
        block();
    });

    $('#edit_block_form').submit(function(event) {
        event.preventDefault();

        let bookId = $('#block_place_id').val();
        let date = $('#block_date').val();
        let from = window.fixApiTime($('#block_from').val());
        let to = window.fixApiTime($('#block_to').val());

        updateBlocking(
            JSON.stringify({ bookId, date, from, to }),
            () => {
                alert('Block has edited successfully');

                /*let targetRow = '#blk_' + book_id;
                $(targetRow).find('td').eq(2).text(date);
                $(targetRow).find('td').eq(3).text(formatTime(time_from));
                $(targetRow).find('td').eq(4).text(formatTime(time_to));*/
                reload();

                $('#blockEditModal').modal('hide');
                $('#edit_block_form').trigger("reset");
            }
        )
    });

    reload();
});