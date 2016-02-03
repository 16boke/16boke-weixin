package com.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.pojo.TextMessage;
import com.utils.MessageUtil;

public class CoreService {
	private static final Logger log = Logger.getLogger(CoreService.class);

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "谢谢,收到！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			log.info("发送方帐号 = " + fromUserName);
			log.info("公众帐号 = " + toUserName);
			log.info("消息类型 = " + msgType);
			System.out.println("发送方帐号 = " + fromUserName);
			System.out.println("公众帐号 = " + toUserName);
			System.out.println("消息类型 = " + msgType);
			StringBuffer buffer = new StringBuffer();// 首次关注和帮助显示的内容
			buffer.append("您好，欢迎您关注本帐号!").append("\n\n");
			buffer.append("1、点击'我要上网'可以进行无线上网").append("\n\n");
			buffer.append("回复“?”显示此帮助菜单");
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				System.out.println("之前content = " + content);
				try {
					if (content != null) {
						content = content.replaceAll("<![CDATA[", "");
						content = content.replaceAll("]]>", "");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("之后content = " + content);
				if (content.trim().equalsIgnoreCase("wifi")) {// 获取wifi密码
					if (fromUserName != null && fromUserName.trim().length() > 0) {

					}
				} else if (content.trim().equalsIgnoreCase("?") || content.trim().equalsIgnoreCase("？")) {
					respContent = buffer.toString();
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = buffer.toString();
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					log.info("事件KEY值 = " + eventKey);
					if (eventKey.equals("11")) {
						respContent = "酒店预订正在创建中...";
					} else if (eventKey.equals("21")) {
						respContent = "我的订单正在创建中...";
					} else if (eventKey.equals("22")) {
						respContent = "我要注册正在创建中...";
					} else if (eventKey.equals("23")) {
						respContent = "我的积分正在创建中...";
					} else if (eventKey.equals("24")) {
						respContent = "我的特权正在创建中...";
					} else if (eventKey.equals("31")) {
						respContent = "优惠活动正在创建中...";
					} else if (eventKey.equals("32")) {
						respContent = "积分换购正在创建中...";
					}
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			System.out.println("respMessage = " + respMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	public static String getRes(String pass) {
		return "验证码：" + pass + "，当天有效。\n\n<a href='http://www.baidu.com'>点击上网</a>,选择微信登录，输入验证码完成登录，祝您上网愉快!【中航网信物联】";
	}
}
