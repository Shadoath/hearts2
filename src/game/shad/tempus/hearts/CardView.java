package game.shad.tempus.hearts;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CardView extends ImageView{	
	public static final String TAG = "Hearts--CardView";


	private Card card;
	public CardView(Context context, Card card, LinearLayout.LayoutParams layoutParams) {
		super(context);
		this.card=card;
		setVisibility(View.VISIBLE);
		setImageBitmap(card.getBitmap());
		setLayoutParams(layoutParams);
		setPadding(0, 0, 0, 0);
		setTag(card.name);
		// TODO Auto-generated constructor stub
	}
	
	public Card getCard(){
		return card;
	}
	
	public void setCard(Card card){
		this.card=card;
	}
	

	public void setTouched(boolean touched){
		Log.d(TAG, "Setting Touched to:"+touched);
		card.setTouched(touched);
		setImageBitmap(card.getBitmap());
		this.refreshDrawableState();
	}
	
}
