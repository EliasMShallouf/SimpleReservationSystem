function doItem(equip, callback) {
    $.ajax({
        type: "POST",
        url: '/api/data/items',
        data: equip,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateItem(equip, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/data/items',
        data: equip,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function _deleteItem(equip, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/data/items/' + equip.id,
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

    function editItem(equip) {
        $('#eequip_id').val(equip.id);
        $('#eequip_name').val(equip.name);
        $('#eequip_des').val(equip.description);

        $('#equipEditModal').modal('show');
    }

    function deleteItem(equip) {
        if(!confirm("Would you like to delete this equip with its spaces ?"))
            return;

        _deleteItem(equip, () => {
            alert('Deleted successfully');

            let targetRow = '#equip_' + equip.id;
            $(targetRow).remove();
        });
    }

    function equip() {
        let name = $('#equip_name').val();
        let description = $('#equip_des').val();

        doItem(
            JSON.stringify({ name, description }),
            () => {
                alert('Item has created successfully');

                $('#equipModal').modal('hide');
                $('#equip_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/data/items',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#equip_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#equip_table tbody')
                      .append(
                        $('<tr id=\'equip_' + r.id + '\' scope=row>')
                            .append($('<td>').text(r.name))
                            .append($('<td>').text(r.description))
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editItem(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteItem(r);
                                            })
                                    )
                            )
                      );
                });
            }
        });
    }

    $('#equip_form').submit(function(event) {
        event.preventDefault();
        equip();
    });

    $('#edit_equip_form').submit(function(event) {
        event.preventDefault();

        let id = $('#eequip_id').val();
        let name = $('#eequip_name').val();
        let description = $('#eequip_des').val();

        updateItem(
            JSON.stringify({ id, name, description }),
            () => {
                alert('Item has edited successfully');

                /*let targetRow = '#equip_' + id;
                $(targetRow).find('td').eq(0).text(name);
                $(targetRow).find('td').eq(1).text(beschreibung);*/
                reload();

                $('#equipEditModal').modal('hide');
                $('#edit_equip_form').trigger("reset");
            }
        )
    });

    reload();
});