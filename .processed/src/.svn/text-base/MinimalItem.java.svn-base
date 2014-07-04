import java.io.IOException;

import javax.microedition.lcdui.*;

// A minimal implementation of a MIDP 2.0
// custom item. Fills its content area with
// a white rectangle.

public class MinimalItem extends CustomItem {
    public MinimalItem( String label ){
        super( label );
    }

    // Returns the minimal height of the content
    // area.

    protected int getMinContentHeight(){
        return 40;
    }

    // Returns the minimal width of the content
    // area.

    protected int getMinContentWidth(){
        return 40;
    }

    // Returns the preferred height of the content
    // area. A tentative value for the opposite
    // dimension -- the width -- is passed to aid
    // in the height calculation. The tentative value
    // should be ignored if it is -1.

    protected int getPrefContentHeight( int width ){
        return getMinContentHeight();
    }

    // Returns the preferred width of the content
    // area. A tentative value for the opposite
    // dimension -- the height -- is passed to aid
    // in the width calculation. The tentative value
    // should be ignored if it is -1.

    protected int getPrefContentWidth( int height ){
        return getMinContentWidth();
    }

    // Draws the item's content area, whose dimensions
    // are given by the width and height parameters.

    protected void paint( Graphics g, int width, int height ){
        try {
			g.drawImage(Image.createImage("/borda.png"), width, height,20);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
