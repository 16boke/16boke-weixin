package com.menu;

import org.apache.log4j.Logger;

import com.pojo.AccessToken;
import com.pojo.Button;
import com.pojo.CommonButton;
import com.pojo.ComplexButton;
import com.pojo.Menu;
import com.pojo.ViewButton;
import com.utils.WeixinUtil;

/**
 * 菜单管理器类
 * 
 * @author avic
 * @date 2013-11-19 自定义菜单的创建步骤 1、找到AppId和AppSecret。自定义菜单申请成功后，在“高级功能”-“开发模式”-“接口配置信息”的最后两项就是； 2、根据AppId和AppSecret，以https
 *       get方式获取访问特殊接口所必须的凭证access_token； 3、根据access_token，将json格式的菜单数据通过https post方式提交。
 */
public class MenuManager {

	private static final Logger log = Logger.getLogger(MenuManager.class);
	// 第三方用户唯一凭证
	private static String appId = "wx417f3cbb953e74ec";
	// 第三方用户唯一凭证密钥
	private static String appSecret = "55a9711f02cad0fa3134cb3080f28a37";

	public static void main(String[] args) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("酒店预订");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn21 = new CommonButton();
		btn21.setName("我的订单");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("我要注册");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("我的积分");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("我的特权");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn31 = new CommonButton();
		btn31.setName("优惠活动");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("积分换购");
		btn32.setType("click");
		btn32.setKey("32");

		// ComplexButton mainBtn1 = new ComplexButton();
		// mainBtn1.setName("酒店预订");
		// mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("我的金岁");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("最新活动");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		// menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });

		ViewButton btn1 = new ViewButton();
		btn1.setName("我要上网");
		btn1.setType("view");
		btn1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ appId
				+ "&redirect_uri=http://192.168.0.32:8080/portal/weixin_login.jsp&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

		ViewButton btn2 = new ViewButton();
		btn2.setName("资源下载");
		btn2.setType("view");
		btn2.setUrl("http://weixin.todo.cn/weiweb/27/index.html");

		menu.setButton(new Button[] { btn1, btn2 });

		return menu;
	}
}
