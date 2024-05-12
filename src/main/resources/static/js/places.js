function doSpace(space, callback) {
    $.ajax({
        type: "POST",
        url: '/api/data/places',
        data: space,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateSpace(space, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/data/places',
        data: space,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function _deleteSpace(space, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/data/places/' + space.id,
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
    let selectedRoom = null

    function refreshChipGroup(parent) {
        let pid = $(parent).attr('id');
        let childs = $('#' + pid + ' div').length;

        if(childs > 0)
            $(parent).addClass("d-flex flex-row").show();
        else
            $(parent).removeClass("d-flex flex-row").hide();
    }

    function addChipToGroup(c, parent) {
        if(c.id == null)
            return;

        let pid = $(parent).attr('id');
        let find = $('#' + pid + ' div[data-value="' + c.id + '"').length;

        if(find == 1) {
            return;
        }

        parent.append(
            $('<div>')
                .attr('data-value', c.id)
                .addClass('listItem chips')
                .append(
                    $('<button>')
                        .addClass('btn btn-secondary')
                        .text(c.name)
                        .click(function(e) {
                            e.preventDefault();
                            $(this).closest('div').remove();
                            refreshChipGroup(parent);
                        })
                )
        )
        refreshChipGroup(parent);
    }

    function renderChips(chips, parent) {
        parent.empty();

        $.each(chips, function(idx, c) {
            addChipToGroup(c, parent);
        });
    }

    function editSpace(space) {
        $('#espace_id').val(space.id);
        $('#espace_name').val(space.name);

        renderChips(space.items, $('#edit_items_list'));

        $('#edit_items_btn').click(function(e) {
            e.preventDefault();

            let chip = {
                id: $('#edit_items').val(),
                name: $('#edit_items option:selected').text()
            };
            addChipToGroup(chip, $('#edit_items_list'))
        });

        $('#spaceEditModal').modal('show');
    }

    function deleteSpace(space) {
        if(!confirm("Would you like to delete this place?"))
            return;

        _deleteSpace(space, () => {
            alert('Deleted successfully');

            let targetRow = '#spc_' + space.id;
            $(targetRow).remove();
        });
    }

    function space() {
        let name = $('#space_name').val();
        let room = selectedRoom;
        let items = [];

        $.each($('#new_items_list div'), function(idx, e) {
            let id = $(e).attr('data-value');
            items.push(id);
        });

        doSpace(
            JSON.stringify({ name, room, items }),
            () => {
                alert('Place has created successfully');

                $('#spaceModal').modal('hide');
                $('#space_form').trigger("reset");

                reload(selectedRoom);
            }
        )
    }

    function reload(space_id) {
        $.ajax({
            url: '/api/data/rooms/' + space_id + '/places',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#space_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#space_table tbody')
                      .append(
                        $('<tr id=\'spc_' + r.id + '\' scope=row>')
                            .append($('<td>').text(r.name))
                            .append(
                                $('<td>').html(
                                    r.items ?
                                        r.items.map((as) => "<p>" + as.name + "</p>").join("")
                                        : ""
                                )
                            )
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editSpace(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteSpace(r);
                                            })
                                    )
                            )
                      );
                });
            }
        });
    }

    $('#space_form').submit(function(event) {
        event.preventDefault();
        space();
    });

    $('#edit_space_form').submit(function(event) {
        event.preventDefault();

        let id = $('#espace_id').val();
        let name = $('#espace_name').val();
        let items = [];

        $.each($('#edit_items_list div'), function(idx, e) {
            let id = $(e).attr('data-value');
            items.push(id);
        });

        let itemListTmp = [];
        $.each($('#edit_items_list div button'), function(idx, e) {
            let name = $(e).text();
            itemListTmp.push(name);
        });

        updateSpace(
            JSON.stringify({ id, name, items }),
            () => {
                alert('Place has edited successfully');

                /*let targetRow = '#spc_' + id;
                $(targetRow).find('td').eq(0).text(name);
                $(targetRow).find('td').eq(1).html(ausstatungListTmp.map((as) => "<p>" + as + "</p>").join(""));*/
                reload(selectedRoom);

                $('#spaceEditModal').modal('hide');
                $('#edit_space_form').trigger("reset");
            }
        )
    });

    $('#space_room').change(function () {
        selectedRoom = $(this).val();
    });

    $('#spaceEditModal').on('show.bs.modal', function (e) {
         $.ajax({
            url: '/api/data/items',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let equips = data;
                $('#edit_items').empty();

                $.each(equips, function(idx, equip) {
                    $('#edit_items')
                      .append(
                        $('<option>')
                          .text(equip.name)
                          .attr('value', equip.id)
                      );
                });
            }
         });
    });

    $('#spaceModal').on('show.bs.modal', function (e) {
         $.ajax({
            url: '/api/data/items',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let equips = data;
                $('#new_items').empty();

                $.each(equips, function(idx, equip) {
                    $('#new_items')
                      .append(
                        $('<option>')
                          .text(equip.name)
                          .attr('value', equip.id)
                      );
                });
            }
         });
    });

    $.ajax({
        url: '/api/data/rooms',
        type: "GET",
        dataType: "json",
        success: function (data) {
            let rooms = data;
            $('#space_room').empty();
            $.each(rooms, function(idx, room) {
                $('#space_room')
                  .append(
                    $('<option>')
                      .text(room.name)
                      .attr('value', room.id)
                  );
            });
            $("#space_room").change();

            if(rooms.length > 0)
              $('#spacesFilterBtn').click();
        }
    });

    $('#spacesFilterBtn').click(function() {
        reload($('#space_room').val());
    });

    $('#new_items_btn').click(function(e) {
        e.preventDefault();

        let chip = {
            id: $('#new_items').val(),
            name: $('#new_items option:selected').text()
        };
        addChipToGroup(chip, $('#new_items_list'))
    });
});