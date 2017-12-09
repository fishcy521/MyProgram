<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<base href="${ctx}"></base>
<meta charset="utf-8">
<title><c:out value="${title}" default="3D范例"/></title>
<meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<!--
<link href="charisma/css/animate.min.css" rel="stylesheet">
<script src="charisma/bower_components/jquery/jquery.min.js"></script>
-->
<script src="https://cdn.bootcss.com/jquery/1.9.0/jquery.js"></script>

<script src="js/threejs/build/three.js"></script>
<script src="js/threejs/js/Detector.js"></script>
<script src="js/threejs/js/controls/TrackballControls.js"></script>
<script src="js/threejs/js/controls/OrbitControls.js"></script>
<script src="js/threejs/js/loaders/MTLLoader.js"></script>
<script src="js/threejs/js/libs/dat.gui.min.js"></script>
<script src="js/threejs/js/libs/stats.min.js"></script>
<script src="js/threejs/js/loaders/LoaderSupport.js"></script>
<script src="js/threejs/js/loaders/OBJLoader2.js"></script>
<%--<script src="js/threejs/js/loaders/OBJLoader.js"></script>--%>
