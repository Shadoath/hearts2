package game.shad.tempus.hearts;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CardView extends ImageView{

	private Card card;
	public CardView(Context context, Card card, LinearLayout.LayoutParams layoutParams) {
		super(context);
		this.card=card;
		setVisibility(View.VISIBLE);
		setImageBitmap(card.getBitmap());
		setLayoutParams(layoutParams);
		setPadding(0, 0, 0, 0);
		setTag(card.toString());
		// TODO Auto-generated constructor stub
	}
	
	public Card getCard(){
		return card;
	}
	
	public void setCard(Card card){
		this.card=card;
	}

	public void setCardSelected(boolean b){
		card.setTouched(b);
	}
}
