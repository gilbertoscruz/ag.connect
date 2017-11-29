package agrisolus.com.br.agconnect.componente;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by gilbe on 27/04/2017.
 */

public class DecimalEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    private DecimalFormatSymbols decimalFormatSymbols;
    private Pattern regex;
    private Pattern regexPaste;

    public DecimalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        setRawInputType(Configuration.KEYBOARD_12KEY | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        addTextChangedListener(this);

        decimalFormatSymbols = new DecimalFormatSymbols(getResources().getConfiguration().locale);

        //pattern for simple input
        regex = Pattern.compile("^(\\d{1,7}[" + decimalFormatSymbols.getMonetaryDecimalSeparator() + "]\\d{2}){1}$");

        //pattern for inserted text, like 005 in buffer inserted to 0,05 at position of first zero => 5,05 as a result
        regexPaste = Pattern.compile("^([0]+\\d{1,6}[" + decimalFormatSymbols.getMonetaryDecimalSeparator() + "]\\d{2})$");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /*if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
            String userInput = "" + s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }

            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }

            cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
            cashAmountBuilder.insert(0, '$');

            setText(cashAmountBuilder.toString());

            // keeps the cursor always to the right
            Selection.setSelection(getText(), cashAmountBuilder.toString().length());
        }*/
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().matches(regex.toString()) || editable.toString().matches(regexPaste.toString())) {

            //Unformatted string without any not-decimal symbols
            String coins = editable.toString().replaceAll("[^\\d]", "");
            StringBuilder builder = new StringBuilder(coins);

            //Example: 0006
            while (builder.length() > 3 && builder.charAt(0) == '0')
                //Result: 006
                builder.deleteCharAt(0);

            //Example: 06
            while (builder.length() < 3)
                //Result: 006
                builder.insert(0, '0');

            //Final result: 0,06 or 0.06
            builder.insert(builder.length() - 2, decimalFormatSymbols.getMonetaryDecimalSeparator());
            setText(builder.toString());
        }

        setSelection(super.getText().length());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (selStart == selEnd)
            setSelection(super.getText().length());

        super.onSelectionChanged(selStart, selEnd);
    }

    @Override
    public Editable getText() {
        Editable s = (Editable) super.getText();
        s.toString().replace(",", ".").trim();
        return s;
    }

    /**
     * Obtem o valor do campo no formato double
     * @return
     */
    public double getValue() {
        try {
            String eAm = super.getText().toString();
            DecimalFormat dF = new DecimalFormat("0.00");
            Number num = dF.parse(eAm);
            return num.doubleValue();
        } catch (Exception e) {
            return 0.0d;
        }
    }
}
