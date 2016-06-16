using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MyObj {
	public string name;
	public GameObject obj;
	public int number;
}

public class Test : MonoBehaviour 
{
	public GameObject table = null;
	public GameObject chair = null;
	List<MyObj> objs = new List<MyObj>();

	string state = "first";
	string lastCmd = "";

	Color getColor(int order) {
		if (order == 0)
			return Color.red;
		if (order == 1)
			return Color.black;
		return Color.green;
	}

	string getColorName(int order) {
		if (order == 0)
			return "red";
		if (order == 1)
			return  "black";
		return "green";
	}
	
	void onGetCmd(string cmd) {
		string words = "";
		int index = -1;
		cmd = cmd.ToLower();
		GameObject prefab = null;
		MyObj obj = new MyObj();
		MyObj obj2 = new MyObj();
		if (state == "second") {
			cmd += lastCmd;
			state = "first";
		}
		lastCmd = cmd;
		int target = -1;
		if (cmd.Contains ("red"))
			target = 0;
		if (cmd.Contains ("black"))
			target = 1;
		if (cmd.Contains ("green"))
			target = 2;
		if (cmd.Contains ("table")) {

			obj.name = "table";
			obj.obj = table;
			index = cmd.IndexOf("table");
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
		if (cmd.Contains ("forward")) v.z += sl;
		if (cmd.Contains ("back")) v.z -= sl;
		if (cmd.Contains ("up")) v.y += sl;
		if (cmd.Contains ("down")) v.y -= sl;
		
		if (cmd.Contains ("new") || cmd.Contains("more") || cmd.Contains("give")) {
			int order = 0;
			foreach (MyObj ibj in objs) {
				if (ibj.name == obj.name)
					order += 1;
			}
			Debug.Log(order);
			if (order > 2)
				words = "Sorry, we don't have more " + obj.name;
			else {
				obj.obj = (GameObject)GameObject.Instantiate (obj.obj);
				obj.number = order;
				obj.obj.renderer.material.color = getColor(order);
				objs.Add(obj);
				words = "We have found a " + getColorName(order) + " " + obj.name;
			}
		} else {
			int id = 0,rid = 0,order = 0;
			foreach (MyObj ibj in objs) {
				id += 1;
				if (ibj.name == obj.name) {
					obj.obj = ibj.obj;
					rid = id;
					order = ibj.number;
				}
			}
			Debug.Log(order);
			if (order < target) {
				words = "Sorry. I don't have this color of " + obj.name;
				say(words);
				return;
			}
			if (order > 0 && target < 0) {
				words = "You mean which " + obj.name + "? The red one, the black one or the green one?";
				state = "second";
				say (words);
				return;
			}
			if (order > 0 && order != target) {
				foreach (MyObj ibj in objs) {
					id += 1;
					if (ibj.name == obj.name && obj.number == target) {
						obj.obj = ibj.obj;
						rid = id;
					}
				}
			}

			if (obj.obj == null) {
				words = "I can't find the furniture";
				say (words);
				return;
			}
			if (cmd.Contains("delete")) {
				GameObject.Destroy(obj.obj);
				words = "OK, I have destroyed the " + obj.name;
				objs.RemoveAt(rid);
			}
			if (cmd.Contains("move") || cmd.Contains("put")) {
				words = "OK, I have moved the " + obj.name + " to the position you want.";
				obj.obj.transform.localPosition += v;
			}
			if (cmd.Contains('turn')) {
				words = "OK, I have rotated the " + obj.name;
				obj.obj.transform.Rotate(0,0,2);
			}
		}
		say (words);
		return;
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
		Debug.Log (s);
		/*
		using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
		{
			using( AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
			{
				//调用Android插件中UnityTestActivity中StartActivity0方法，stringToEdit表示它的参数
				jo.Call("StartActivity0",s);
			}
			
		}*/
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
		//say("ok");
		onGetCmd (str);
	}
	
}
