<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: fishc
  Date: 2017/12/2
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/top.jsp" %>
<html>
<head>
    <%@include file="/common/common.jsp" %>

    <style>
        body {
            background:#000;
            color:#000;
            padding:0;
            margin:0;
            overflow:hidden;
            font-family:monospace;
            font-size:13px;
            text-align:center;
        }

        a { color:skyblue }

        #info {
            position: absolute;
            top: 0px; width: 100%;
            color: #fff;
            background:rgba(0,0,0,0.65);
            padding: 5px;
            text-align: center;
            z-index:100;
            display:block;
        }

        #stats { position: absolute; top:5px; left: 0; z-index: 100 }
        #stats #fps { background: transparent !important }
        #stats #fps #fpsText { color: #aaa !important }
        #stats #fps #fpsGraph { display: none }
    </style>

</head>
<body>
<div id="Stats-output">
</div>
<!-- Div which will hold the Output -->
<div id="WebGL-output">
</div>

<script type="text/javascript">

    $(function () {

        var stats = initStats();

        // create a scene, that will hold all our elements such as objects, cameras and lights.
        var scene = new THREE.Scene();


        // create a camera, which defines where we're looking at.
        var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000);

        // SKYBOX
        /*      var  sceneCube;
                var textureCube;
                        sceneCube = new THREE.Scene();
                        //sceneCube.add( camera );

                        var r = "textures/cube/pisa/";
                        var urls = [ r + "px.png", r + "nx.png",
                                     r + "py.png", r + "ny.png",
                                     r + "pz.png", r + "nz.png" ];


                    textureCube = THREE.ImageUtils.loadTextureCube( urls );

                        var shader = THREE.ShaderLib[ "cube" ];
                        shader.uniforms[ "tCube" ].value = textureCube;

                    var material = new THREE.ShaderMaterial( {

                            fragmentShader: shader.fragmentShader,
                            vertexShader: shader.vertexShader,
                            uniforms: shader.uniforms,
                            depthWrite: false,
                            side: THREE.BackSide

                        } ),

                        mesh1 = new THREE.Mesh( new THREE.BoxGeometry( 100, 100, 100 ), material );
                        sceneCube.add( mesh1 );



                */

        // create a render and set the size
        var renderer = new THREE.WebGLRenderer();
        renderer.setClearColorHex(0xffffff, 1.0);
        renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.shadowMapEnabled = true;

        //添加坐标轴
        var axes = new THREE.AxisHelper( 20 );
        scene.add(axes);


        // 创建一个平面
        var planeGeometry = new THREE.PlaneGeometry(100,100, 1, 1);
        var planeMaterial = new THREE.MeshLambertMaterial({color: 0xededed});
        var plane = new THREE.Mesh(planeGeometry, planeMaterial);
        plane.receiveShadow = true;

        // rotate and position the plane
        plane.rotation.x = -0.5 * Math.PI;
        plane.position.x = 15
        plane.position.y = -20
        plane.position.z = 0
        scene.add(plane);

        //添加模型
        var gui = new dat.GUI();
        var mesh;
        var loader = new THREE.OBJMTLLoader();
        loader.addEventListener('load', function (event) {
            var object = event.content;
            for(k in object.children){
                object.children[k].castShadow = true;
                object.children[k].receiveShadow = true;
            }
            object.position.x = 20;
            object.position.y = 0;
            object.position.z = 0;
            object.scale.set(0.5, 0.5, 0.5);
            mesh = object;
            scene.add(mesh);
        });
        loader.load('${ctx}module/tea/tea.obj', '${ctx}module/tea/tea.mtl', {side: THREE.DoubleSide});

        // 相机在场景中的位置和指向
        camera.position.x = -25;
        camera.position.y = 40;
        camera.position.z = 25;
        camera.lookAt(new THREE.Vector3(10, 0, 0));

        //添加控制
        var orbitControls = new THREE.OrbitControls(camera);
        orbitControls.autoRotate = true;//设置平面自动旋转
        var clock = new THREE.Clock();

        // 增加外界环境光照
        var ambiColor = "#8b8a87";
        var ambientLight = new THREE.AmbientLight(ambiColor);
        scene.add(ambientLight);

        // 为了增加阴影添加一个点光源
        var spotLight = new THREE.SpotLight(0xffffff);
        spotLight.position.set(-40, 60, -10);
        spotLight.castShadow = true;
        scene.add(spotLight);

        // add the output of the renderer to the html element
        $("#WebGL-output").append(renderer.domElement);


        // call the render function
        var step = 0;

        var controls = new function () {
            this.rotationSpeed = 0.01;
            this.bouncingSpeed = 0;
            this.ambientColor = ambiColor;
        }

        var gui = new dat.GUI();
        gui.addColor(controls, 'ambientColor').onChange(function (e) {
            ambientLight.color = new THREE.Color(e);
        });


        render();

        //监听窗口时间使其自适应
        window.addEventListener( 'resize', onWindowResize, false );
        window.addEventListener( 'mousemove', onDocumentMouseMove, false );

        function render() {
            stats.update();

            //sphere.rotation.y=step+=0.01;
            var delta = clock.getDelta();
            orbitControls.update(delta);
            if (mesh) {
                //      mesh.rotation.y+=0.006;
                mesh.rotation.y+=0.006;//只是水平旋转
                //mesh.rotation.x += controls.rotationSpeed;
                //mesh.rotation.y += controls.rotationSpeed;
                //mesh.rotation.z += controls.rotationSpeed;
            }
            // render using requestAnimationFrame
            requestAnimationFrame(render);
            renderer.render(scene, camera);
        }

        function initStats() {
            var stats = new Stats();
            stats.setMode(0); // 0: fps, 1: ms
            // Align top-left
            stats.domElement.style.position = 'absolute';
            stats.domElement.style.left = '0px';
            stats.domElement.style.top = '0px';
            $("#Stats-output").append(stats.domElement);
            return stats;
        }


        //窗口监听事件
        function onWindowResize( event ) {
            SCREEN_WIDTH = window.innerWidth;
            SCREEN_HEIGHT = window.innerHeight;
            renderer.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
            camera.aspect = SCREEN_WIDTH / SCREEN_HEIGHT;
            camera.updateProjectionMatrix();
            cameraCube.aspect = SCREEN_WIDTH / SCREEN_HEIGHT;
            cameraCube.updateProjectionMatrix();

        }
        //鼠标监听事件
        function onDocumentMouseMove(event) {
            mouseX = ( event.clientX - windowHalfX );
            mouseY = ( event.clientY - windowHalfY );
        }
    });
</script>
</body>
</html>
