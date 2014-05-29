package king.chad.toaster.sample;

import king.chad.toastersample.R;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class HexcodeSelectorActivity extends Activity 
{
	Button btnCancel;
	Button btnOk;
	TextView txtColorPickerValue;
	ColorPicker picker;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(king.chad.toastersample.R.layout.hex_code_selector);
        
        
        
        btnCancel = (Button) findViewById(king.chad.toastersample.R.id.btn_cancel);
        btnOk = (Button) findViewById(king.chad.toastersample.R.id.btn_save);
        txtColorPickerValue = (TextView) findViewById(king.chad.toastersample.R.id.txtColorPickerValue);
       
        picker = (ColorPicker) findViewById(R.id.picker);
        
        //--set initial color--
        txtColorPickerValue.setText("Hexcode: "+ getHexcode());
        
        picker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
				
				txtColorPickerValue.setText("Hexcode: "+ getHexcode());
			}
        	
        });
        
        /*SVBar svBar = (SVBar) findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);*/

        btnCancel.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        btnOk.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				
				//To get the color
		        //int value = picker.getColor();
		        
 		       String hexColor = getHexcode();
		       Intent data = new Intent();

		        /* *************************************************************
     		     * Set the data to pass back to the Calling Activity --CK
     		    ************************************************************* */
		        //data.setData(Uri.parse(String.valueOf(value)));
		        data.setData(Uri.parse(hexColor));
     			setResult(RESULT_OK, data);

     		    /* *************************************************************
     		     * Calling 'finish()' closes the activity --CK
     		    ************************************************************* */
     			finish();
			}
		});

    }
    
    public String getHexcode()
    {
        String vals = picker.getARGBVals();
        String[] argbParts = vals.split(",");
		int a = Integer.parseInt(argbParts[0]);
	    int r = Integer.parseInt(argbParts[1]);
	    int g = Integer.parseInt(argbParts[2]);
	    int b = Integer.parseInt(argbParts[3]);
	    
    	return String.format( "#%02x%02x%02x", r, g, b );
    }

}
