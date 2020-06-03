<%--
  Created by IntelliJ IDEA.
  User: zzpkusch
  Date: 2020/2/29
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改用户</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.1.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">修改联系人</h3>
    <form action="${pageContext.request.contextPath}/updateUserServlet" method="post" id="updateform">
        <%--隐藏域提交id--%>
        <input type="hidden" name="id" value="${user.id}">
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name" value="${user.name}" readonly="readonly"
                   placeholder="请输入姓名"/>
        </div>

        <c:if test="${user.gender=='男'}">
            <div class="form-group">
                <label>性别：</label>
                <input type="radio" name="gender" value="男" checked/>男
                <input type="radio" name="gender" value="女"/>女
            </div>

        </c:if>
        <c:if test="${user.gender=='女'}">
            <div class="form-group">
                <label>性别：</label>
                <input type="radio" name="gender" value="男"/>男
                <input type="radio" name="gender" value="女" checked/>女
            </div>

        </c:if>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" name="age" value=" ${user.age}" placeholder="请输入年龄"/>
        </div>

        <div class="form-group">
            <label for="address">籍贯：</label>
            <select name="address" class="form-control" id="address">
                <c:if test="${user.address=='江苏'}">
                    <option value="江苏" selected>江苏</option>
                    <option value="河南">河南</option>
                    <option value="北京">北京</option>
                    <option value="湖北">湖北</option>
                </c:if>
                <c:if test="${user.address=='河南'}">
                    <option value="江苏" >江苏</option>
                    <option value="河南" selected>河南</option>
                    <option value="北京">北京</option>
                    <option value="湖北">湖北</option>
                </c:if>
                <c:if test="${user.address=='北京'}">
                    <option value="江苏" >江苏</option>
                    <option value="河南">河南</option>
                    <option value="北京" selected>北京</option>
                    <option value="湖北">湖北</option>
                </c:if>
                <c:if test="${user.address=='湖北'}">
                    <option value="江苏" >江苏</option>
                    <option value="河南">河南</option>
                    <option value="北京">北京</option>
                    <option value="湖北" selected>湖北</option>
                </c:if>
            </select>
        </div>

        <div class="form-group">
            <label for="qq">QQ：</label>
            <input type="text" class="form-control" name="qq" placeholder="请输入QQ号码" id="qq" value="${user.qq}"/>
        </div>

        <div class="form-group">
            <label for="email">Email：</label>
            <input type="text" class="form-control" name="email" placeholder="请输入邮箱地址" id="email"
                   value="${user.email}"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交"/>
            <input class="btn btn-default" type="reset" onclick="$('updateform').reset()"  value="重置"/>
            <input class="btn btn-default" type="button" onclick="window.location.href='${pageContext.request.contextPath}/findUserByPageServlet'" value="返回"/>
        </div>
    </form>
</div>
</body>
</html>
