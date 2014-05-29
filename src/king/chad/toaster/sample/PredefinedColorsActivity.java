package king.chad.toaster.sample;

import java.util.ArrayList;

import king.chad.toastersample.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class PredefinedColorsActivity extends Activity 
{
	String hexcode = "#000000";
	Button btnCancel;
	Button btnOk;
	TextView txtColorPickerValue;
	Spinner predifenedHexcodes;
	String[] spinnerValues;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(king.chad.toastersample.R.layout.predefined_colors);
        
        populateColors();

        btnCancel = (Button) findViewById(king.chad.toastersample.R.id.btn_cancel);
        btnOk = (Button) findViewById(king.chad.toastersample.R.id.btn_save);
        txtColorPickerValue = (TextView) findViewById(king.chad.toastersample.R.id.txtColorPickerValue);
        predifenedHexcodes = (Spinner) findViewById(king.chad.toastersample.R.id.predifenedHexcodes);
        
        predifenedHexcodes.setAdapter(new MyAdapter(this, R.layout.custom_spinner, spinnerValues));

        //--set initial color--
        txtColorPickerValue.setText("Hexcode: "+ getHexcode());
        
        predifenedHexcodes.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
            {
				String val = (String)parentView.getItemAtPosition(position);
				hexcode= val;
				
				txtColorPickerValue.setText("Hexcode: "+ hexcode);
            }

            public void onNothingSelected(AdapterView<?> parentView) 
            {
             
            }

       });
        
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
       return hexcode;
    }
    
    public class MyAdapter extends ArrayAdapter<String> 
    { 
    	public MyAdapter(Context ctx, int txtViewResourceId, String[] objects) { 
    		super(ctx, txtViewResourceId, objects); 
    		} 
    	
    	@Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) { 
    		return getCustomView(position, cnvtView, prnt); } 
    	
    	@Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
    		return getCustomView(pos, cnvtView, prnt); 
    	} 
    	
    	public View getCustomView(int position, View convertView, ViewGroup parent) { 
    		LayoutInflater inflater = getLayoutInflater();
    		View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false); 
    		
    		TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen); 
    		main_text.setBackgroundColor(Color.parseColor(spinnerValues[position])); 
    		
    		return mySpinner;
    	} 
    }

    public void populateColors()
    {
    	try{
    			spinnerValues = new String[] { 
    				"#FF0097", 	    	        "#A200FF",    	    	    "#00ABA9",    	    	    "#8CBF26",
    	    	    "#A05000",    	    	    "#E671B8",    	    	    "#F09609",    	    	    "#1BA1E2",
    	    	    "#E51400",    	    	    "#339933",    	    	    "#FFFFFF",    	    	    "#000000",
    	    	    "#FF0000",    	    	    "#008000",    	    	    "#0000FF",    	    	    "#FFFF00",
    	    	    "#008080",    	    	    "#FFA500",    	    	    "#00CED1",    	    	    "#008B8B",
    	    	    "#006400",    	    	    "#E9967A",    	    	    "#00008B",    	    	    "#FF8C00",
    	    	    "#9932CC",    	    	    "#9400D3",    	    	    "#8B008B",    	    	    "#8B0000",
    	    	    "#556B2F",    	    	    "#2F4F4F",    	    	    "#483D8B",    	    	    "#8FBC8F",
    	    	    "#A9A9A9",    	    	    "#B8860B",    	    	    "#BDB76B",    	    	    "#DCDCDC",
    	    	    "#B0C4DE",    	    	    "#ADD8E6",    	    	    "#FFB6C1",    	    	    "#FFA07A",
    	    	    "#E0FFFF",    	    	    "#F08080",    	    	    "#87CEFA",    	    	    "#20B2AA",
    	    	    "#778899",    	    	    "#90EE90",    	    	    "#FFFFE0",    	    	    "#FAFAD2",
    	    	    "#7B68EE",    	    	    "#66CDAA",    	    	    "#C71585",    	    	    "#BA55D3",
    	    	    "#9370DB",    	    	    "#0000CD",    	    	    "#48D1CC",    	    	    "#3CB371",
    	    	    "#00FA9A",    	    	    "#F0FFFF",    	    	    "#F0F8FF",    	    	    "#FAEBD7",
    	    	    "#7FFFD4",    	    	    "#F5F5DC",    	    	    "#DEB887",    	    	    "#A52A2A",
    	    	    "#8A2BE2",    	    	    "#00FFFF",    	    	    "#FFE4C4",    	    	    "#FFEBCD",
    	    	    "#FFF8DC",    	    	    "#FF7F50",   	    	    "#D2691E",    	    	    "#DC143C",
    	    	    "#6495ED",    	    	    "#5F9EA0",    	    	    "#7FFF00",    	    	    "#FF1493",
    	    	    "#00BFFF",    	    	    "#696969",    	    	    "#1E90FF",    	    	    "#FF00FF",
    	    	    "#228B22",    	    	    "#FFFAF0",    	    	    "#B22222",    	    	    "#FF00FF",
    	    	    "#FFD700",    	    	    "#ADFF2F",    	    	    "#808080",    	    	    "#DAA520",
    	    	    "#DCDCDC",    	    	    "#F8F8FF",    	    	    "#F0FFF0",    	    	    "#FF69B4",
    	    	    "#4B0082",    	    	    "#FFFFF0",    	    	    "#CD5C5C",    	    	    "#F0E68C",
    	    	    "#00FF00",    	    	    "#7CFC00",    	    	    "#FFF0F5",    	    	    "#FAF0E6",
    	    	    "#FFFACD",    	    	    "#E6E6FA",    	    	    "#32CD32",    	    	    "#800000",
    	    	    "#FFE4B5",    	    	    "#800000",    	    	    "#F5FFFA",    	    	    "#FF00FF",
    	    	    "#191970",    	    	    "#FFE4E1",    	    	    "#000080",    	    	    "#FFDEAD",
    	    	    "#808000",    	    	    "#6B8E23",    	    	    "#FF4500",    	    	    "#FDF5E6",
    	    	    "#DA70D6",    	    	    "#FFDAB9",    	    	    "#FFC0CB",    	    	    "#CD853F",
    	    	    "#EEE8AA",    	    	    "#DB7093",    	    	    "#DDA0DD",    	    	    "#98FB98",
    	    	    "#B0E0E6",    	    	    "#AFEEEE",    	    	    "#FFEFD5",    	    	    "#4169E1",
    	    	    "#BC8F8F",    	    	    "#C0C0C0",    	    	    "#FFF5EE",    	    	    "#FFFAFA",
    	    	    "#A0522D",    	    	    "#00FF7F",    	    	    "#87CEEB",    	    	    "#2E8B57",
    	    	    "#4682B4",    	    	    "#708090",    	    	    "#6A5ACD",    	    	    "#8B4513",
    	    	    "#FA8072",    	    	    "#F4A460",    	    	    "#D8BFD8",    	    	    "#40E0D0",
    	    	    "#D2B48C",    	    	    "#FF6347",    	    	    "#800000",    	    	    "#800080",
    	    	    "#EE82EE",    	    	    "#F5F5F5",    	    	    "#F5DEB3",    	    	    "#9ACD32"
    		};
    	    
		}
    	catch(Exception ex){}
	}
   
}
