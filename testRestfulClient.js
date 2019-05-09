function sendRestfulGet(){
    var doubanURL = 'https://api.douban.com//v2/movie/top250';
    $.ajax({
        url: doubanURL,
        type: 'Get',
        data: '',
        dataType: 'JSONP',
        crossDomain: true,
        success: function(data){
            console.log(data);
        }
    });
}