<!DOCTYPE html>
<html>
<#include  "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDTOPage.getContent() as orderDTO >
                <tr>
                    <td>${orderDTO.getOrderId()}</td>
                    <td>${orderDTO.getBuyerName()}</td>
                    <td>${orderDTO.getBuyerPhone()}</td>
                    <td>${orderDTO.buyerAddress}</td>
                    <td>${orderDTO.orderAmount}</td>
                    <td>${orderDTO.getOrderStatusEnum().getMsg()}</td>
                    <td>${orderDTO.getPayStatusEnum().getMessage()}</td>
                    <td>${orderDTO.getCreateTime()}</td>
                    <td>
                        <a href="/sell/seller/order/detail?orderId=${orderDTO.getOrderId()}">详情</a>
                    </td>

                    <td>
                        <#if orderDTO.getOrderStatusEnum().getMsg()=="新下单">
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.getOrderId()}">取消</a>
                        </#if>
                    </td>


                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if currentPage lte 1>
                <li class="disabled"><a href="javascript:">上一页</a></li>
                <#else >
                <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=5">上一页</a></li>
                </#if>

                <#list 1..orderDTOPage.getTotalPages() as index>
                    <#if currentPage==index>
                <li class="disabled"><a href="javascript:">${index}</a></li>
                    <#else >
                <li><a href="/sell/seller/order/list?page=${index}&size=5">${index}</a></li>
                    </#if>
                </#list>

                <#if currentPage gte orderDTOPage.getTotalPages()>
                <li class="disabled"><a href="javascript:">下一页</a></li>
                <#else >
                <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=5">下一页</a></li>
                </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>