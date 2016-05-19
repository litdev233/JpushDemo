package com.litdev.jpush.receive;

import com.litdev.jpush.TestActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class MyReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String extra_val = bundle.getString(JPushInterface.EXTRA_EXTRA);
		System.out.println("onReceive - " + intent.getAction());
		System.out.println("额外数据："+extra_val);
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			System.out.println("全局唯一ID：" + bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			System.out.println("收到了通知");
			// 在这里可以做些统计，或者做些其他工作
			System.out.println("通知的标题是："+bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
			System.out.println("通知的内容是："+bundle.getString(JPushInterface.EXTRA_ALERT));
			System.out.println("通知的额外字段是："+bundle.getString(JPushInterface.EXTRA_EXTRA));
			System.out.println("通知栏的ID："+bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
			System.out.println("通知消息的ID是："+bundle.getString(JPushInterface.EXTRA_MSG_ID));
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			System.out.println("用户点击打开了通知");
			// 在这里可以自己写代码去定义用户点击后的行为
			if(!TextUtils.isEmpty(extra_val) && !"{}".equals(extra_val))
			{
				Intent i = new Intent(context, TestActivity.class); // 自定义打开的界面
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}else{
				System.out.println("通知消息为空，不做跳转");
			}
			
		} else {
			System.out.println("Unhandled intent - " + intent.getAction());
		}
	}

}
