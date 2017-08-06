package evacuees.com.mbank.customFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Dilwar Singh on 8/6/2017.
 */

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
        customizeText();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        customizeText();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customizeText();
    }

    private void customizeText() {


            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
            this.setTypeface(typeface);
        }
    }
/*

    @Override
    public void setTypeface(Typeface tf) {

        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");

        super.setTypeface(tf);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
        super.setTypeface(tf, style);
    }
}
*/
