package evacuees.com.mbank.customFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dilwar Singh on 8/6/2017.
 */

public class MyEditText extends EditText {


    public MyEditText(Context context) {
        super(context);

    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        customizeText();

    }


    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customizeText();

    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        customizeText();

    }

    private void customizeText() {


            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
            setTypeface(typeface);
        }
    }
