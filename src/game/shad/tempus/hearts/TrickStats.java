package game.shad.tempus.hearts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class TrickStats {
	public static final String TAG = "Hearts--Trick";

	public String winnerString;
	public JSONObject jsonWinnerString;
	public JSONArray cardsPileJson;
	
	public TrickStats(int points, Player curPlayer, Trick pile){
		jsonWinnerString = new JSONObject();
		cardsPileJson = new JSONArray();
		winnerString="Points="+points+" Won by="+curPlayer.shortName;
		try {
			jsonWinnerString.put(curPlayer.shortName, points);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Card c: pile.getTrick()){
			cardsPileJson.put(c.getOwner()+":"+c.name);
		}
		
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
	
	
	
	
}
