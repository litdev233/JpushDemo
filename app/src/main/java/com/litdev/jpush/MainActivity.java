package com.litdev.jpush;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.data.JPushLocalNotification;

public class MainActivity extends Activity {

	private EditText ed_alias;
	private EditText ed_Tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		JPushInterface.init(this);
		ed_alias = (EditText) findViewById(R.id.ed_alias);
		ed_Tag = (EditText) findViewById(R.id.ed_Tag);
	}
	
	/**
	 * 本地通知
	 * @param v
	 */
	public void SetNotify(View v){
		JPushLocalNotification ln = new JPushLocalNotification();
		ln.setBuilderId(0); //设置本地通知样式
		ln.setContent("这里是本地通知");//设置本地通知的content
		ln.setTitle("收到了吗骚年？"); //本地通知的Title
		ln.setNotificationId(11111111) ;//设置本地通知的ID
		ln.setBroadcastTime(System.currentTimeMillis() + 1000);//设置本地通知触发时间(1s后通知)
		//setBroadcastTime(Date date)
		//setBroadcastTime(int year, int month, int day, int hour, int minute, int second)
		
		//准备额外的json数据
		Map<String , Object> map = new HashMap<String, Object>() ;
		map.put("name", "jpush") ;
		map.put("test", "111") ;
		JSONObject json = new JSONObject(map) ;
		ln.setExtras(json.toString()) ;//设置额外的数据
		JPushInterface.addLocalNotification(getApplicationContext(), ln);
	}
	
	public void SetTag(View v){
		String tags = ed_Tag.getText().toString().trim();
		Set set =new HashSet();
		for (String item : tags.split(",")) {
			set.add(item);
		}
		
		if(set.size() > 0){
			JPushInterface.setTags(this, set, new TagAliasCallback() {
				
				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {
					System.out.println("别名："+arg0);
					if(arg0 == 0){
						Toast.makeText(MainActivity.this, "标签设置成功", 0).show();
					}else{
						Toast.makeText(MainActivity.this, "标签设置失败", 0).show();
					}
					
					if(arg2 != null){
						System.out.println("set长度："+arg2.size());
						Iterator i = arg2.iterator();//先迭代出来  
						while(i.hasNext()){//遍历  
				            System.out.println("Tag:"+i.next().toString());  
				        } 
					}else{
						System.out.println("arg2是null");
					}
					
				}
			});
		}
		
		
	}
	
	public void SetAlias(View v){
		String alias = ed_alias.getText().toString().trim();
		if(!TextUtils.isEmpty(alias)){
			JPushInterface.setAlias(this, alias, new TagAliasCallback() {				
				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {
					System.out.println("别名："+arg0);
					if(arg2 != null){
						Iterator i = arg2.iterator();//先迭代出来  
						while(i.hasNext()){//遍历  
				            System.out.println("Tag:"+i.next().toString());  
				        }  
					}else{
						System.out.println("arg2是null");
					}
					if(arg0 == 0){
						Toast.makeText(MainActivity.this, "别名设置成功", 0).show();
					}else{
						Toast.makeText(MainActivity.this, "别名设置失败", 0).show();
					}
				}
			});
			
		}else{
			Toast.makeText(this, "别名不能为空", 0).show();
		}
	}
	
	@Override
	protected void onResume() {
		JPushInterface.onResume(this);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		JPushInterface.onPause(this);
		super.onPause();
	}
	
}
