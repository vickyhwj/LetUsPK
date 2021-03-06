<%@page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Insert title here</title>
    <link href="../css/bootstrap.css" rel="stylesheet">
    <script src="../js/jquery-2.1.3.js"></script>
    <script src="../js/bootstrap.js"></script>
    <style>
        #A {
            color: red;
        }

        #B {
            color: black;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <h2 class="page-header">
                    <em id="A"></em> vs
                    <em id="B"></em>
                </h2>
                <p id="turn"></p>
            </div>
            <div class="col-sm-8">
                <div class="canvas_div">
                    <canvas id="canvas1" style="background-color:burlywood;width: 100%;height: 100%" width="1005" height="1105">不支持Canvas</canvas>
                    <img src="../xiangqi/img/blue-ju.gif" id="ju" hidden="hidden" />
                    <img src="../xiangqi/img/blue-ma.gif" id="ma" hidden="hidden" />
                    <img src="../xiangqi/img/blue-xiang.gif" id="xiang" hidden="hidden" />
                    <img src="../xiangqi/img/blue-shi.gif" id="shi" hidden="hidden" />
                    <img src="../xiangqi/img/blue-jiang.gif" id="jiang" hidden="hidden" />
                    <img src="../xiangqi/img/blue-pao.gif" id="pao" hidden="hidden" />
                    <img src="../xiangqi/img/blue-bing.gif" id="bing" hidden="hidden" />

                    <img src="../xiangqi/img/red-ju.gif" id="r_ju" hidden="hidden" />
                    <img src="../xiangqi/img/red-ma.gif" id="r_ma" hidden="hidden" />
                    <img src="../xiangqi/img/red-xiang.gif" id="r_xiang" hidden="hidden" />
                    <img src="../xiangqi/img/red-shi.gif" id="r_shi" hidden="hidden" />
                    <img src="../xiangqi/img/red-jiang.gif" id="r_jiang" hidden="hidden" />
                    <img src="../xiangqi/img/red-pao.gif" id="r_pao" hidden="hidden" />
                    <img src="../xiangqi/img/red-bing.gif" id="r_bing" hidden="hidden" />
                </div>
            </div>

        </div>
    </div>






</body>

</html>
<script src="../js/common.js"></script>
<script type="text/javascript">
    var key = "${param.key}";
    window.onload = function () {
        var ip = commonIp;
        var websocket = new WebSocket("ws://" + ip + "/ssmws/WatchXiangqi?key=" + key);
        websocket.onopen = function () {
            console.log("ok")
        }

        var A, B, turn;
        var a = [];

        var ju = document.getElementById("ju");
        var ma = document.getElementById("ma");
        var xiang = document.getElementById("xiang");
        var shi = document.getElementById("shi");
        var jiang = document.getElementById("jiang");
        var bing = document.getElementById("bing");
        var pao = document.getElementById("pao");

        var r_ju = document.getElementById("r_ju");
        var r_ma = document.getElementById("r_ma");
        var r_xiang = document.getElementById("r_xiang");
        var r_shi = document.getElementById("r_shi");
        var r_jiang = document.getElementById("r_jiang");
        var r_bing = document.getElementById("r_bing");
        var r_pao = document.getElementById("r_pao");

        for (var i = 0; i < 10; ++i) {
            a[i] = [];
            for (var j = 0; j < 9; ++j)
                a[i][j] = '0';
        }
        a[0][0] = 'ju';
        a[0][1] = 'ma';
        a[0][2] = 'xiang';
        a[0][3] = 'shi';
        a[0][4] = 'jiang';
        a[0][8] = 'ju';
        a[0][7] = 'ma';
        a[0][6] = 'xiang';
        a[0][5] = 'shi';
        a[2][1] = 'pao';
        a[2][7] = 'pao';
        a[3][0] = 'bing';
        a[3][2] = 'bing';
        a[3][4] = 'bing';
        a[3][6] = 'bing';
        a[3][8] = 'bing';

        a[9][0] = 'r_ju';
        a[9][1] = 'r_ma';
        a[9][2] = 'r_xiang';
        a[9][3] = 'r_shi';
        a[9][4] = 'r_jiang';
        a[9][8] = 'r_ju';
        a[9][7] = 'r_ma';
        a[9][6] = 'r_xiang';
        a[9][5] = 'r_shi';
        a[7][1] = 'r_pao';
        a[7][7] = 'r_pao';
        a[6][0] = 'r_bing';
        a[6][2] = 'r_bing';
        a[6][4] = 'r_bing';
        a[6][6] = 'r_bing';
        a[6][8] = 'r_bing';



        var canvas1;
        var object = {
            //初始化
            init: function () {
                //棋盘外框
                canvas1 = document.getElementById("canvas1");
                this.ctx = canvas1.getContext("2d");
                this.ctx.lineWidth = 5;
                this.ctx.strokeStyle = "brown";
                this.ctx.strokeRect(100, 100, 800, 900);

                this.row();
                this.cols();
                this.drawFont();
                //注意：现在的原点中心为（100,100），所以下面的所有坐标在原来的基础上加（x+100,y+100）；
                //中心点一（1000,200）
                this.center(200, 300);
                //中心点二（700,200）
                this.center(800, 300);
                //中心点三（5,300）
                this.center(105, 400);
                //中心点四（200,300）
                this.center(300, 400);
                //中心点五（400,300）
                this.center(500, 400);
                //中心点六（600,300）
                this.center(700, 400);
                //中心点七（800,300）
                this.center(900, 400);
                //中心点八（800,600）
                this.center(900, 700);
                //中心点九（600,600）
                this.center(700, 700);
                //中心点十（400,600）
                this.center(500, 700);
                //中心点十一（200,600）
                this.center(300, 700);
                //中心点十二（5,600）
                this.center(105, 700);
                //中心点十三（700,700）
                this.center(800, 800);
                //中心点十四（100,700）
                this.center(200, 800);

                //必须当页面中的图片资源加载成功,再画棋子


                //将棋子图像绘制到画布上
                this.ctx.drawImage(ju, 50, 150, 100, 100);
                this.ctx.drawImage(ma, 150, 50, 100, 100);
                this.ctx.drawImage(xiang, 250, 50, 100, 100);
                this.ctx.drawImage(shi, 350, 50, 100, 100);
                this.ctx.drawImage(jiang, 450, 50, 100, 100);
                this.ctx.drawImage(shi, 550, 50, 100, 100);
                this.ctx.drawImage(xiang, 650, 50, 100, 100);
                this.ctx.drawImage(ma, 750, 50, 100, 100);
                this.ctx.drawImage(ju, 850, 50, 100, 100);
                this.ctx.drawImage(pao, 150, 250, 100, 100);
                this.ctx.drawImage(pao, 750, 250, 100, 100);
                this.ctx.drawImage(bing, 50, 350, 100, 100);
                this.ctx.drawImage(bing, 250, 350, 100, 100);
                this.ctx.drawImage(bing, 450, 350, 100, 100);
                this.ctx.drawImage(bing, 650, 350, 100, 100);
                this.ctx.drawImage(bing, 850, 350, 100, 100);

                this.ctx.drawImage(r_ju, 50, 950, 100, 100);
                this.ctx.drawImage(r_ma, 150, 950, 100, 100);
                this.ctx.drawImage(r_xiang, 250, 950, 100, 100);
                this.ctx.drawImage(r_shi, 350, 950, 100, 100);
                this.ctx.drawImage(r_jiang, 450, 950, 100, 100);
                this.ctx.drawImage(r_shi, 550, 950, 100, 100);
                this.ctx.drawImage(r_xiang, 650, 950, 100, 100);
                this.ctx.drawImage(r_ma, 750, 950, 100, 100);
                this.ctx.drawImage(r_ju, 850, 950, 100, 100);
                this.ctx.drawImage(r_pao, 150, 750, 100, 100);
                this.ctx.drawImage(r_pao, 750, 750, 100, 100);
                this.ctx.drawImage(r_bing, 50, 650, 100, 100);
                this.ctx.drawImage(r_bing, 250, 650, 100, 100);
                this.ctx.drawImage(r_bing, 450, 650, 100, 100);
                this.ctx.drawImage(r_bing, 650, 650, 100, 100);
                this.ctx.drawImage(r_bing, 850, 650, 100, 100);


            },
            //此方法用来画棋盘线
            LineDrawing: function (mx, my, lx, ly) {
                this.ctx.beginPath();
                this.ctx.moveTo(mx, my);
                this.ctx.lineTo(lx, ly);
                this.ctx.stroke();
            },
            //棋盘行
            row: function () {
                for (var i = 200; i <= 900; i += 100) {
                    this.ctx.beginPath();
                    this.ctx.moveTo(105, i);
                    this.ctx.lineTo(900, i);
                    this.ctx.stroke();
                }
            },
            //棋盘列
            cols: function () {
                for (var i = 200; i <= 800; i += 100) {
                    this.ctx.beginPath();
                    this.ctx.moveTo(i, 105);
                    this.ctx.lineTo(i, 1000);
                    this.ctx.stroke();
                }
                //清除指定的矩形区域
                //this.ctx.clearRect(5, 402,795, 95);
                this.ctx.clearRect(102.5, 502, 795, 95);
                //斜线
                this.LineDrawing(400, 105, 600, 300);
                this.LineDrawing(400, 805, 600, 1000);
                //反斜线
                this.LineDrawing(600, 105, 400, 300);
                this.LineDrawing(600, 805, 400, 1000);
            },
            //坐标的中心点
            center: function (x, y) {
                this.ctx.lineWidth = 5;
                //中心点一（100,200）
                //左上
                this.LineDrawing(x - 10, y - 30, x - 10, y - 10);
                this.LineDrawing(x - 10, y - 10, x - 30, y - 10);
                //右上
                this.LineDrawing(x + 10, y - 30, x + 10, y - 10);
                this.LineDrawing(x + 10, y - 10, x + 30, y - 10);
                //左下
                this.LineDrawing(x - 10, y + 30, x - 10, y + 10);
                this.LineDrawing(x - 10, y + 10, x - 30, y + 10);
                //右下
                this.LineDrawing(x + 10, y + 30, x + 10, y + 10);
                this.LineDrawing(x + 10, y + 10, x + 30, y + 10);
            },
            drawFont: function () {
                this.ctx.lineWidth = 1;
                //绘制文字
                this.ctx.font = "60px microsoft yahei";
                this.ctx.save(); //保存点
                //将坐标中心作为起启点
                this.ctx.translate(canvas1.width / 2, canvas1.height / 2);
                var radian = Math.PI / 2; // 弧度制 Math.PI=π
                this.ctx.rotate(radian); // 旋转画布绘制刻度
                //填充
                this.ctx.fillText("楚", -30, -270);
                this.ctx.fillText("河", -30, -150);
                this.ctx.restore(); //恢复到保存点
                this.ctx.save();
                //将坐标中心作为起点
                this.ctx.translate(canvas1.width / 2, canvas1.height / 2);
                var radian = Math.PI / -2;
                this.ctx.rotate(radian);
                this.ctx.fillText("汉", -30, -270);
                this.ctx.fillText("界", -30, -150);
                this.ctx.restore();
            },
            jiaocha: function (x, y) {
                x = x + 1; y = y + 1;
                this.LineDrawing(100 * y - 50, 100 * x - 50, 100 * y + 50, 100 * x - 50);
                this.LineDrawing(100 * y + 50, 100 * x - 50, 100 * y + 50, 100 * x + 50);
                this.LineDrawing(100 * y + 50, 100 * x + 50, 100 * y - 50, 100 * x + 50);
                this.LineDrawing(100 * y - 50, 100 * x + 50, 100 * y - 50, 100 * x - 50);
            },
            //clean
            clean: function () {
                //	canvas1 = document.getElementById("canvas1");
                //	this.ctx = canvas1.getContext("2d");
                this.ctx.clearRect(0, 0, canvas1.width, canvas1.height);
                this.ctx.lineWidth = 5;
                this.ctx.strokeStyle = "brown";
                this.ctx.strokeRect(100, 100, 800, 900);

                this.row();
                this.cols();
                this.drawFont();
                //注意：现在的原点中心为（100,100），所以下面的所有坐标在原来的基础上加（x+100,y+100）；
                //中心点一（1000,200）
                this.center(200, 300);
                //中心点二（700,200）
                this.center(800, 300);
                //中心点三（5,300）
                this.center(105, 400);
                //中心点四（200,300）
                this.center(300, 400);
                //中心点五（400,300）
                this.center(500, 400);
                //中心点六（600,300）
                this.center(700, 400);
                //中心点七（800,300）
                this.center(900, 400);
                //中心点八（800,600）
                this.center(900, 700);
                //中心点九（600,600）
                this.center(700, 700);
                //中心点十（400,600）
                this.center(500, 700);
                //中心点十一（200,600）
                this.center(300, 700);
                //中心点十二（5,600）
                this.center(105, 700);
                //中心点十三（700,700）
                this.center(800, 800);
                //中心点十四（100,700）
                this.center(200, 800);

                for (var i = 0; i < 10; ++i)
                    for (var j = 0; j < 9; ++j) {
                        if (a[i][j] != null && a[i][j] != '0') {
                            var xxx = document.getElementById(a[i][j]);
                            this.ctx.drawImage(xxx, 50 + 100 * j, 50 + 100 * i, 100, 100);
                        }
                    }
            }
        };
        object.init();
        object.clean();
        websocket.onmessage = function (data) {
            data = data.data;
            data = JSON.parse(data);
            console.log(data);
            a = data.state;
            A = data.A;
            $("#A").text(A);
            B = data.B;
            $("#B").text(B);
            turn = data.turn;
            $("#turn").text("轮到" + turn)
            object.clean();

        }

        setInterval(function () {
            $.ajax({
                type: "get",
                url: "/ssmws/WatchXiangqi",
                data: "key=" + key,
                success: function (response) {
                    console.log("对方在线：" + response)
                },
                error: function () {
                    console.log("fail")
                }
            });
            if (websocket.readyState == 3) {
                console.log("disconnect")
                if (confirm("连接异常，是否刷新")) {
                    location.reload(true);
                }
            }
        }, 10000);
      //  window.open("http://localhost:8080/ssmws/xiangqi/watch.html")
    }

</script>