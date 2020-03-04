$(function () {
    var getheadlineandshopcategorylist = "listmainpageinfo";
    getList();

    function getList(){
        $.getJSON(getheadlineandshopcategorylist,function (data) {
            var swiperHtml="";
            if(data.success){

                data.headLineList.map(function (item,index) {
                    swiperHtml += '<div class="swiper-slide img-wrap">'
                        + '<img class="banner-img" src="' + item.lineImg
                        + '" alt="' + item.lineName + '">' + '</div>';
                });

                $(".swiper-wrapper").html(swiperHtml);
                //设置轮播时间
                $(".swiper-container").swiper({
                    autoplay : 3000,
                    autoplayDisableOnInteraction : false
                });

                var categoryHtml = "";
                data.shopCategoryList.map(function (item,index) {
                    categoryHtml += ''
                        + '<div class="col-50 shop-classify" data-category='
                        + item.shopCategoryId + '>' + '<div class="word">'
                        + '<p class="shop-title">' + item.shopCategoryName
                        + '</p>' + '<p class="shop-desc">'
                        + item.shopCategoryDesc + '</p>' + '</div>'
                        + '<div class="shop-classify-img-warp">'
                        + '<img class="shop-img" src="' + item.shopCategoryImg
                        + '">' + '</div>' + '</div>';
                });
                $(".row").html(categoryHtml);
            }

        });
    }

    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    $('.row').on('click', '.shop-classify', function(e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = 'shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });
});