/**
 * Created by Administrator on 2017/6/29.
 */
/*
 * @Author: anchen
 * @Date:   2016-10-29 09:30:55
 * @Last Modified by:   anchen
 * @Last Modified time: 2016-10-31 17:15:03
 */


//首页banner
if (document.getElementById('index_banner')) {
    (function() {
        var num = 0;

        function play() {
            num++;
            if (num > $('#index_banner ul li').length - 1) num = 0;
            $('#index_banner ul li').eq(num).fadeIn().siblings().fadeOut();
            $('#index_banner ol li').eq(num).addClass('active').siblings().removeClass('active');
        }

        var timer = setInterval(play, 3000);
        $('#index_banner ol li').hover(function() {
            clearInterval(timer);
        }, function() {
            timer = setInterval(play, 3000);
        });
        $('#index_banner ol li').click(function() {
            $(this).addClass('active').siblings().removeClass('active');
            num = $(this).index() - 1;
            play();
        });
    })();
}

//首页无缝滚动
if (document.querySelector('.hzzs_pic')) {
    (function() {
        var box = document.querySelector('.hzzs_pic');
        var aLi = box.children[0].children;
        var w = 0;
        for (var i = 0; i < aLi.length; i++) {
            w += aLi[i].offsetWidth;
        }
        box.children[0].style.width = 2 * w + 'px';
        box.children[0].innerHTML += box.children[0].innerHTML;

        function go() {
            box.scrollLeft++;
            if (box.scrollLeft >= w) box.scrollLeft = 0;
        }
        var timer = setInterval(go, 20);
        box.onmouseover = function() {
            clearInterval(timer);
        }
        box.onmouseout = function() {
            timer = setInterval(go, 20);
        }
    })();
}

if (document.querySelector('.hzzs_pic1')) {
    (function() {
        var box = document.querySelector('.hzzs_pic1');
        var aLi = box.children[0].children;
        var w = 0;
        for (var i = 0; i < aLi.length; i++) {
            w += aLi[i].offsetWidth;
        }
        box.children[0].style.width = 2 * w + 'px';
        box.children[0].innerHTML += box.children[0].innerHTML;

        function go() {
            box.scrollLeft++;
            if (box.scrollLeft >= w) box.scrollLeft = 0;
        }
        var timer = setInterval(go, 20);
        box.onmouseover = function() {
            clearInterval(timer);
        }
        box.onmouseout = function() {
            timer = setInterval(go, 20);
        }
    })();
}
// 3D卡片
if (document.querySelectorAll('.card')) {
    (function() {
        var parents = document.querySelectorAll('.card');
        for (var i = 0; i < parents.length; i++) {
            parents[i].onmousemove = function(e) {
                var child = this.children[0];
                var w = this.offsetWidth;
                var h = this.offsetHeight;
                x = e.offsetX;
                y = e.offsetY;
                rotateX = -(y / (h / 20) - 10);
                rotateY = x / (w / 20) - 10;
                child.style.transform = 'rotateX(' + rotateX + 'deg) rotateY(' + rotateY + 'deg)';
                child.style.transition = '.1s';
                child.style.boxShadow = '0 0 20px black';
            }
            parents[i].onmouseout = function() {
                var child = this.children[0];
                child.style.transform = 'rotateX(0) rotateY(0)';
                child.style.transition = '.5s';
                child.style.boxShadow = 'none';
            }
        }
    })();
}

// 首页视频
if (document.querySelector('.video_list')) {
    (function() {
        var box = document.querySelector('.play_box');
        var boxIn = box.querySelector('.play');
        var embed = box.querySelector('embed');
        var boxP = box.querySelector('p');
        var list = document.querySelector('.video_list');
        var aLi = list.querySelectorAll('li');

        boxIn.innerHTML = '<embed width="890" height="440" flashvars="isAutoPlay=true" allowfullscreen="true" quality="high" src="' + aLi[0].querySelector('span').innerText + '" type="application/x-shockwave-flash">';
        boxP.innerText = aLi[0].querySelector('p').innerText;

        for (var i = 0; i < aLi.length; i++) {
            aLi[i].onclick = function() {
                var liP = this.querySelector('p');
                var span = this.querySelector('span');
                for (var j = 0; j < aLi.length; j++) {
                    aLi[j].className = '';
                }
                this.className = 'active';
                boxP.innerText = liP.innerText;
                boxIn.innerHTML = '<embed width="890" height="440" flashvars="isAutoPlay=true" allowfullscreen="true" quality="high" src="' + span.innerText + '" type="application/x-shockwave-flash">';
            }
        }
    })();
}

//回到页面顶部
if (document.querySelector('.gotop')) {
    (function() {
        $('.gotop').click(function() {
            $('html, body').animate({scrollTop: 0}, 500);
        });
    })();

    function showHide() {
        if ($(document).scrollTop() > 50) {
            $('.gotop').fadeIn();
        } else {
            $('.gotop').fadeOut();
        }
    }
    $(window).scroll(showHide);
    showHide();
}

//百度地图
if (document.getElementById('dituContent1')) {

    //1111111111111111111111111111111111111111111111
    (function() {
        //创建和初始化地图函数：
        function initMap(){
            createMap();//创建地图
            setMapEvent();//设置地图事件
            addMapControl();//向地图添加控件
        }

        //创建地图函数：
        function createMap(){
            var map = new BMap.Map("dituContent1");//在百度地图容器中创建一个地图
            var point = new BMap.Point(113.256766,23.185868);//定义一个中心点坐标
            map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
            window.map = map;//将map变量存储在全局
        }

        //地图事件设置函数：
        function setMapEvent(){
            map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
            map.enableScrollWheelZoom();//启用地图滚轮放大缩小
            map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
            map.enableKeyboard();//启用键盘上下左右键移动地图
        }

        //地图控件添加函数：
        function addMapControl(){
            //向地图中添加缩放控件
            var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
            map.addControl(ctrl_nav);
            //向地图中添加缩略图控件
            var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
            map.addControl(ctrl_ove);
            //向地图中添加比例尺控件
            var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
            map.addControl(ctrl_sca);
        }


        initMap();//创建和初始化地图
    })();

    //222222222222222222222222222222222222222222222222
    (function() {
        //创建和初始化地图函数：
        function initMap(){
            createMap();//创建地图
            setMapEvent();//设置地图事件
            addMapControl();//向地图添加控件
        }

        //创建地图函数：
        function createMap(){
            var map = new BMap.Map("dituContent2");//在百度地图容器中创建一个地图
            var point = new BMap.Point(104.095135,30.698903);//定义一个中心点坐标
            map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
            window.map = map;//将map变量存储在全局
        }

        //地图事件设置函数：
        function setMapEvent(){
            map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
            map.enableScrollWheelZoom();//启用地图滚轮放大缩小
            map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
            map.enableKeyboard();//启用键盘上下左右键移动地图
        }

        //地图控件添加函数：
        function addMapControl(){
            //向地图中添加缩放控件
            var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
            map.addControl(ctrl_nav);
            //向地图中添加缩略图控件
            var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
            map.addControl(ctrl_ove);
            //向地图中添加比例尺控件
            var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
            map.addControl(ctrl_sca);
        }


        initMap();//创建和初始化地图
    })();

    //3333333333333333333333333333333333333333333333333
    (function() {
        //创建和初始化地图函数：
        function initMap(){
            createMap();//创建地图
            setMapEvent();//设置地图事件
            addMapControl();//向地图添加控件
        }

        //创建地图函数：
        function createMap(){
            var map = new BMap.Map("dituContent3");//在百度地图容器中创建一个地图
            var point = new BMap.Point(121.313554,31.066316);//定义一个中心点坐标
            map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
            window.map = map;//将map变量存储在全局
        }

        //地图事件设置函数：
        function setMapEvent(){
            map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
            map.enableScrollWheelZoom();//启用地图滚轮放大缩小
            map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
            map.enableKeyboard();//启用键盘上下左右键移动地图
        }

        //地图控件添加函数：
        function addMapControl(){
            //向地图中添加缩放控件
            var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
            map.addControl(ctrl_nav);
            //向地图中添加缩略图控件
            var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
            map.addControl(ctrl_ove);
            //向地图中添加比例尺控件
            var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
            map.addControl(ctrl_sca);
        }


        initMap();//创建和初始化地图
    })();

    //444444444444444444444444444444444444444444444444444444444
    (function() {
        //创建和初始化地图函数：
        function initMap(){
            createMap();//创建地图
            setMapEvent();//设置地图事件
            addMapControl();//向地图添加控件
        }

        //创建地图函数：
        function createMap(){
            var map = new BMap.Map("dituContent4");//在百度地图容器中创建一个地图
            var point = new BMap.Point(116.374923,39.839019);//定义一个中心点坐标
            map.centerAndZoom(point,17);//设定地图的中心点和坐标并将地图显示在地图容器中
            window.map = map;//将map变量存储在全局
        }

        //地图事件设置函数：
        function setMapEvent(){
            map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
            map.enableScrollWheelZoom();//启用地图滚轮放大缩小
            map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
            map.enableKeyboard();//启用键盘上下左右键移动地图
        }

        //地图控件添加函数：
        function addMapControl(){
            //向地图中添加缩放控件
            var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
            map.addControl(ctrl_nav);
            //向地图中添加缩略图控件
            var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
            map.addControl(ctrl_ove);
            //向地图中添加比例尺控件
            var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
            map.addControl(ctrl_sca);
        }


        initMap();//创建和初始化地图
    })();
}

//整站效果
(function() {
    function go() {
        var h = document.body.scrollTop + document.documentElement.scrollTop + window.innerHeight;
        $('.slideUp').each(function() {
            $(this).t = this.t;
            if ($(this).offset().top <= h + 85) {
                $(this).addClass('show');
            } else {
                $(this).removeClass('show');
            }
        });
    }
    go();
    window.addEventListener('scroll', go, false);

})();

//LOGO翻页
// (function() {
//   if (document.querySelector('.logo_box')) {
//     var box = document.querySelector('.logo_box');
//     var ul = box.children[0];
//     var page = document.querySelector('.logo_box_page');
//     var aLi = box.querySelectorAll('li');
//     var num = 0;

//     var divNum = Math.ceil(aLi.length / 8);

//     ul.style.width = divNum * 1203 + 'px';

//     for (var i = 1; i <= divNum; i++) {
//       var div = document.createElement('div');
//       for (var j = 8 * (i - 1); j < Math.min(aLi.length, 8 * i ); j++ ) {
//         div.appendChild(aLi[j]);
//       }
//       ul.appendChild(div);
//     }
//     for (var i = 0; i < divNum; i++) {
//       var span = document.createElement('span');
//       page.appendChild(span);
//     }
//     var span = page.children;
//     span[0].className = 'active';
//     for (var i = 0; i < divNum; i++) {
//       span[i].index = i;
//       span[i].onclick = function() {
//         for (var j = 0; j < divNum; j++) {
//           span[j].className = '';
//         }
//         this.className = 'active';
//         num = this.index;
//         box.scrollLeft = 1200 * num;
//       }
//     }
//   }
// })();

