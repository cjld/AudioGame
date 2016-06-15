using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MyObj {
	public string name;
	public GameObject obj;
}

public class Test : MonoBehaviour 
{
	public GameObject table = null;
	public GameObject chair = null;
	List<MyObj> objs = new List<MyObj>();
	
	void onGetCmd(string cmd) {
		cmd = cmd.ToLower();
		GameObject prefab = null;
		MyObj obj = new MyObj();
		if (cmd.Contains ("table")) {
			obj.name = "table";
			obj.obj = table;
		}
		if (cmd.Contains ("chair")) {
			obj.name = "chair";
			obj.obj = chair;
		}
		
		if (obj.obj == null) return;
		
		Vector3 v = new Vector3 (0, 0);
		float sl = 1;
		if (cmd.Contains ("left")) v.x += sl;
		if (cmd.Contains ("right")) v.x -= sl;
		if (cmd.Contains ("front")) v.z += sl;
		if (cmd.Contains ("back")) v.z -= sl;
		if (cmd.Contains ("up")) v.y += sl;
		if (cmd.Contains ("down")) v.y -= sl;
		
		if (cmd.Contains ("new")) {
			obj.obj = (GameObject)GameObject.Instantiate (obj.obj);
			objs.Add(obj);
		} else {
			int id = 0,rid = 0;
			foreach (MyObj ibj in objs) {
				id += 1;
				if (ibj.name == obj.name) {
					obj.obj = ibj.obj;
					rid = id;
				}
			}
			if (obj.obj == null) return;
			if (cmd.Contains("delete")) {
				GameObject.Destroy(obj.obj);
				objs.RemoveAt(rid);
			}
			if (cmd.Contains("move")) {
				obj.obj.transform.localPosition += v;
			}
		}
	}

	//请输入一个字符串
	private string stringToEdit = "Please enter a string";

	void Update () 
	{
		//点击手机返回键关闭应用程序
		if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home) )
  		{
   			Application.Quit();
  		}
	}

	void say(string s) {
		using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
		{
			using( AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
			{
				//调用Android插件中UnityTestActivity中StartActivity0方法，stringToEdit表示它的参数
				jo.Call("StartActivity0",s);
			}
			
		}
	}
	
	void OnGUI()
	{
	    //绘制一个输入框接收用户输入	
		stringToEdit = GUILayout.TextField (stringToEdit, GUILayout.Width(300),GUILayout.Height(100));
	
		//一个提交按钮
		if(GUILayout.Button("commit",GUILayout.Height(50)))
		{
			onGetCmd(stringToEdit);

		}

	}
	//注解2
	void mymassage(string str)
	{
		stringToEdit = str;
		Debug.Log(stringToEdit);
		say("ok");
		onGetCmd (str);
	}
	
}
