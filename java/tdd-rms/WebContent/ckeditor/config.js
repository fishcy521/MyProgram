/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
CKEDITOR.editorConfig = function( config ){
//config.skin = 'v2';
config.height = 260;
//换行方式
config.enterMode = CKEDITOR.ENTER_BR;
//当输入：shift+Enter是插入的标签
config.shiftEnterMode = CKEDITOR.ENTER_BR;//
//图片处理
config.pasteFromWordRemoveStyles = true;
config.filebrowserImageUploadUrl = "/upload!upImage.action";
//去掉ckeditor“保存”按钮
config.removePlugins = 'save';
};
