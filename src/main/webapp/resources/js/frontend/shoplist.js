$(function () {
    var parentId = getQueryString("parentId");
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    // 页码
    var pageIndex = 1;
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';
    var getshopcategoryandarealist = "listshopcategoryandareapageinfo?parentId=" + parentId;
    var shopUrl = "listshop";
    getInitList();
    getShop(pageSize, pageIndex);


    function getInitList() {
        $.getJSON(getshopcategoryandarealist, function (data) {
            if (data.success) {
                var areaHtml = '<option value="">全部街道</option>';
                var shopCategoryHtml = '<a href="#" class="button button-round" data-category-id=""> 全部类别  </a>';

                //加载店铺分类
                data.shopCategoryList.map(function (item, index) {
                    shopCategoryHtml += '<div class="col-30" data-category-id='
                        + item.shopCategoryId + '>'
                        + item.shopCategoryName
                        + '</div>';
                });
                $("#shoplist-search-div").html(shopCategoryHtml);

                //加载区域分类
                data.areaList.map(function (item, index) {
                    areaHtml += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $("#area-search").html(areaHtml);
            }
        });
    }

    function getShop(pageSize, pageIndex) {
        var url = shopUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                var currentItems = data.count;
                var html = "";
                data.shopList.map(function (item, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + item.shopId + '">' + '<div class="card-header">'
                        + item.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.shopImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.shopDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);

                // 获取目前为止已显示的卡片总数，包含之前已经加载的
                var total = $('.list-div .card').length;
                // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                if (total >= currentItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageIndex += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

});