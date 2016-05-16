package Fly.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Json莽禄鈥溍ε九撁Ｃε撅拷莽卤禄
 */
public class JsonParser {

	public static String parseIatResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				// 猫陆卢氓鈥犫劉莽禄鈥溍ε九撁拷茂录艗茅禄藴猫庐陇盲陆驴莽鈥澛ぢ糕偓盲赂陋莽禄鈥溍ε九�
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				JSONObject obj = items.getJSONObject(0);
				ret.append(obj.getString("w"));
//				氓娄鈥毭ε九撁┡撯偓猫娄锟矫ヂづ∶モ偓鈩⒚┾偓鈥懊烩�溍ε九撁寂捗Ｃε撅拷忙鈥⒙懊烩�灻モ�β睹ぢ烩�撁ヂ�斆β�
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret.toString();
	}
	
	public static String parseGrammarResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				for(int j = 0; j < items.length(); j++)
				{
					JSONObject obj = items.getJSONObject(j);
					if(obj.getString("w").contains("nomatch"))
					{
						ret.append("忙虏隆忙艙鈥懊ヅ捖姑┾�︼拷莽禄鈥溍ε九�.");
						return ret.toString();
					}
					ret.append("茫鈧拷莽禄鈥溍ε九撁ｂ偓鈥�" + obj.getString("w"));
					ret.append("茫鈧拷莽陆庐盲驴隆氓潞娄茫鈧��" + obj.getInt("sc"));
					ret.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.append("忙虏隆忙艙鈥懊ヅ捖姑┾�︼拷莽禄鈥溍ε九�.");
		} 
		return ret.toString();
	}
}
