/**

 @Name：layuiAdmin iframe版主入口
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.extend({
  setter: 'config' //配置模块
  ,admin: 'lib/admin' //核心模块
  ,view: 'lib/view' //视图渲染模块
}).define(['setter', 'admin'], function(exports){
  var setter = layui.setter
  ,element = layui.element
  ,admin = layui.admin
  ,tabsPage = admin.tabsPage
  ,view = layui.view

  ,refreshAndCloseTabs = function (refreshUrl, closeUrl) {
          //遍历页签选项卡
          var matchTo,
              mathCloseIndex,
              mathCloseTo,
              tabs = $('#LAY_app_tabsheader>li'),
              path = refreshUrl.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');
          tabs.each(function (index) {
              var li = $(this), layid = li.attr('lay-id');
              var layids =layid.split("?");
              layid= layids[0];
              if (layid === 'bid/'+refreshUrl) {
                  matchTo = true;
                  tabsPage.index = index;
              }
              if (layid == 'bid/'+closeUrl) {
                  mathCloseTo = true;
                  mathCloseIndex = index;
              }
          });






          if (mathCloseTo) {
              $('#LAY_app_tabsheader>li').eq(mathCloseIndex).find('.layui-tab-close').trigger('click');
          }

          if (matchTo) {
              //定位当前tabs
              element.tabChange(FILTER_TAB_TBAS, refreshUrl);
              admin.tabsBodyChange(tabsPage.index,
                  {
                      url: refreshUrl,
                      //text: text
                  });
              var iframe = admin.tabsBody(admin.tabsPage.index).find('.layadmin-iframe');
              iframe[0].contentWindow.location.href = refreshUrl;
          } else {
              console.log("调用openTabsPageRefresh没有匹配到url:", refreshUrl, "路径！");
          }
   }
   , setCookie = function (name, value, times) {
          var strsec = times * 24 * 60 * 60 * 1000;
          var exp = new Date();
          exp.setTime(exp.getTime() + strsec * 1);
          document.cookie = name + "=" + escape(value) + ((times == null) ? "" : ";expires=" + exp.toUTCString() + ";path=/");

          // alert(name + '|' + value + '|' + times);
   }
      , getCookie = function (name) {
          var c_start;
          var c_end;
          if (document.cookie.length > 0) {
              c_start = document.cookie.indexOf(name + "=");
              if (c_start != -1) {
                  c_start = c_start + name.length + 1;
                  c_end = document.cookie.indexOf(";", c_start);
                  if (c_end == -1) c_end = document.cookie.length;
                  return unescape(document.cookie.substring(c_start, c_end))
              }
          }
          return ""
          // var username = document.cookie.split(";")[0].split("=")[1];
      }
  ,delCookie = function(name){
          var domain = '.'+location.host;
          var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
          if(keys) {
              for(var i = keys.length; i--;) {
                  var date=new Date();
                  date.setTime(date.getTime()-10000);
                  document.cookie=keys[i]+"=; expire="+date.toUTCString()+"; path=/";
              }
          }
    }
    //打开标签页
  ,openTabsPage = function(url, text){
    //遍历页签选项卡
    var matchTo
    ,tabs = $('#LAY_app_tabsheader>li')
    ,path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');

    tabs.each(function(index){
      var li = $(this)
      ,layid = li.attr('lay-id');

      if(layid === url){
        matchTo = true;
        tabsPage.index = index;
      }
    });

    text = text || '新标签页';

    if(setter.pageTabs){
      //如果未在选项卡中匹配到，则追加选项卡
      if(!matchTo){
        $(APP_BODY).append([
          '<div class="layadmin-tabsbody-item layui-show">'
            ,'<iframe src="'+ url +'" frameborder="0" class="layadmin-iframe"></iframe>'
          ,'</div>'
        ].join(''));
        tabsPage.index = tabs.length;
        element.tabAdd(FILTER_TAB_TBAS, {
          title: '<span>'+ text +'</span>'
          ,id: url
          ,attr: path
        });
      }
    } else {
      var iframe = admin.tabsBody(admin.tabsPage.index).find('.layadmin-iframe');
      iframe[0].contentWindow.location.href = url;
    }

    //定位当前tabs
    element.tabChange(FILTER_TAB_TBAS, url);
    admin.tabsBodyChange(tabsPage.index, {
      url: url
      ,text: text
    });
  }


  ,APP_BODY = '#LAY_app_body', FILTER_TAB_TBAS = 'layadmin-layout-tabs'
  ,$ = layui.$, $win = $(window);

  //初始
  if(admin.screen() < 2) admin.sideFlexible();

  //将模块根路径设置为 controller 目录
  layui.config({
    base: setter.base + 'modules/'
  });

  //扩展 lib 目录下的其它模块
  layui.each(setter.extend, function(index, item){
    var mods = {};
    mods[item] = '{/}' + setter.base + 'lib/extend/' + item;
    layui.extend(mods);
  });

  view().autoRender();

  //加载公共模块
  layui.use('common');

  //对外输出
  exports('index', {
    openTabsPage: openTabsPage
      , refreshAndCloseTabs: refreshAndCloseTabs
      ,setCookie:setCookie
      ,getCookie:getCookie
      ,delCookie:delCookie
  });

});
