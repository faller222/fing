
$('.parent').click(function() {
    var subMenu = $(this).siblings('ul');
    if ($(subMenu).hasClass('open')) {
        $(subMenu).fadeOut();
        $(subMenu).removeClass('open').addClass('closed');
    }
    else {
        $(subMenu).fadeIn();
        $(subMenu).removeClass('closed').addClass('open');
    }
});


//llamada abajo
function uiaspodj(A) {
    $.ajax({
        type: "POST",
        url: "Categoria",
        data: {
            Cate: A}
    }).done(function(msg) {
        $("#main").html(msg);
    });
}

$('.categuria').click(function() {
    var categoria = $(this).text();
    uiaspodj(categoria);
});
