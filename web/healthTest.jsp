<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>首页</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
    </script>

    <script type="text/javascript" language="javascript">
        $("#btn1").click(function () {
            alert("点击了");
            $.ajax({
                url:"https://health.xiamin.tech/user/profile/",
                type:"GET",
                data:{},
                beforeSend:function (request) {
                    request.setRequestHeader("user-agent", "Mozilla/5.0 (Linux; Android 9; vivo NEX Build/PKQ1.181030.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36VivoBrowser/7.6.16.1");
                    request.setRequestHeader("cookie","sessionid=pht5d57pov2yb3omuhmlzmfd2lfxl6bj");
                },
                success:function(data){
                    alert(data);
                },
                dataType:"text"
            });
        });

    </script>
</head>

<body>
    <button id="btn1" class="btn btn-primary">点我</button>
</body>
</html>
