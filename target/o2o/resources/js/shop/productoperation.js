$(function () {
    var productId = getQueryString("productId");
    var isEdit = productId?true:false;
    var productCategoryInfo = "getproductcategory";
    var productInfo = "getproductbyid?productId="+productId;
    var productAddUrl = "addproduct";
    var productModifyUrl = "modifyproduct";


    if(isEdit){
        setProductInfo(productId);
    }else{
        getProductInfo();
    }

    /*注册商品界面，显示后台分类*/
    function getProductInfo(){
        $.getJSON(productCategoryInfo,function(data) {
            if(data.success){
                var html = "";
                data.productCategoryList.map(function(item,index){
                    html += '<option data-id="'+item.productCategoryId+'">'+item.productCategoryName+'</option>'
                });
                $("#category").html(html);
            }

        });
    }

    /*编辑商品界面，显示商品原来参数*/
    function setProductInfo(productId){
        $.getJSON(productInfo,function (data) {
            if(data.success){
                var product = data.product;
                var optionSelected = data.product.productCategory.productCategoryId;
                var optionHtml='';
                $("#product-name").val(product.productName);
                $("#priority").val(product.priority);
                $("#normal-price").val(product.normalPrice);
                $("#promotion-price").val(product.promotionPrice);
                $("#product-desc").val(product.productDesc);
                data.productCategoryList.map(function (item,index) {
                    var isSelect = (optionSelected === item.productCategoryId ? 'selected' : '');
                    optionHtml += '<option data-value="'
                        + item.productCategoryId
                        + '"'
                        + isSelect
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                });
                $("#category").html(optionHtml);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $("#submit").click(function () {
        var product = {};
        if(isEdit){
            product.productId = productId;
        }
        product.productName = $("#product-name").val();
        product.priority = $("#priority").val();
        product.normalPrice = $("#normal-price").val();
        product.promotionPrice = $("#promotion-price").val();
        product.productDesc = $("#product-desc").val();
        product.productCategory = {
            productCategoryId : $('#category').find('option').not(function(){
                return !this.selected;
            }).data("id")};

        var thumbnail = $('#small-img')[0].files[0];
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        $('.detail-img').map(function(index, item) {
            if ($('.detail-img')[index].files.length > 0) {
                formData.append('productImg' + index, $('.detail-img')[index].files[0]);
            }
        });
        formData.append('productStr', JSON.stringify(product));
        console.log(formData.get("thumbnail"));
        console.log(formData.get("productImg"));
        var verifyCode = $("#j_captcha").val();
        if(!verifyCode){
            $.toast("请输入验证码");
            return;
        }
        formData.append("verifyCode",verifyCode);
        // 将数据提交至后台处理相关操作
        $.ajax({
            url : (isEdit?productModifyUrl:productAddUrl),
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });
    });
});