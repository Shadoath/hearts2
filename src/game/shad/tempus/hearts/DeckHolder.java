package game.shad.tempus.hearts;

public class DeckHolder
{

    private Deck deck;
    
    //Holds players deck, class to call for updates about deck and drawing the deck
    public DeckHolder(Deck deck){
        this.deck = deck;
    }
    
    private boolean touch(float x, float y){
        
        return true;
    }
    
    

}
