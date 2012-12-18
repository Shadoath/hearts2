package game.shad.tempus.hearts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class TrickStats {
	public static final String TAG = "Hearts--Trick";

	public Player winner;
	public int points;
	public int round;
	public CardStats[] cards;
	
	public TrickStats(Deck newTrick, int round, Player winner){
		this.round=round;
		this.winner=winner;
		cards = new CardStats[4];
		if(newTrick.getSize()!=4){
			Log.d(TAG, "Wrong SIZE TRICK!");
			return;
		}
		
		for (int i=0; i<newTrick.getSize(); i++){
			Card c = newTrick.getCard(i);
			cards[i] = new CardStats();
			cards[i].owner=c.getOwner().getRealName();
//			cards[i].suit=c.getSuit();
//			cards[i].value=c.getValue();
			cards[i].name=c.toString();
		}
	}
	
	
	public JSONArray toJson(){
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
	}
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
