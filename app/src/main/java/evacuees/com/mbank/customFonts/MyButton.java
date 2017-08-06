package evacuees.com.mbank.customFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Dilwar Singh on 8/6/2017.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {
    public MyButton(Context context) {
        super(context);
        customizeText();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        customizeText();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customizeText();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        customizeText();
    }

    private void customizeText() {

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
            setTypeface(typeface);


    }

   /* @Override
    public void setTypeface(Typeface tf) {

        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");

        super.setTypeface(tf);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
        super.setTypeface(tf, style);
    }*/

}