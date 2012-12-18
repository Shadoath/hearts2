package game.shad.tempus.hearts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class TrickStats {
	public static final String TAG = "Hearts--Trick";


	public String pointsWinnerString;
	
	public TrickStats(int points, Player winner){
		pointsWinnerString="Points="+points+" Won by="+winner.realName;
	}
	
	
	/*public JSONArray toJson(){
		JSONArray jsonTrickArray = new JSONArray();
		int i=1;
		for(CardStats cs : cards){
			if(cs==null){
				Log.d(TAG, "bad CARD in TRICK!");
				return jsonTrickArray;
			}
			JSONObject jsonCard = new JSONObject();
			try {
				String cardValueString = cs.owner+" "+cs.name;
				jsonCard.put("card"+i, cardValueString);
//				jsonCard.put("Value", cs.value);
//				jsonCard.put("Owner", cs.owner);
				i++;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonTrickArray.put(jsonCard);
			
		}
		return jsonTrickArray;
		}*/
	
	public class CardStats{
		
		public String owner;
		public int suit;
		public int value;
		public String name;
		
		public CardStats(){
			owner=null;
			suit=0;
			value=0;
			name="";
		}
	}
	
}
