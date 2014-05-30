package king.chad.toaster.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ToasterTextActivity extends Activity 
{
	Button btnCancel;
	Button btnOk;
	EditText txtToasterText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(king.chad.toastersample.R.layout.toaster_text);
        
        txtToasterText = (EditText) findViewById(king.chad.toastersample.R.id.txtToasterText);
        btnCancel = (Button) findViewById(king.chad.toastersample.R.id.btn_cancel);
        btnOk = (Button) findViewById(king.chad.toastersample.R.id.btn_save);
        
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
				
				
		       Intent data = new Intent();

		        /* *************************************************************
     		     * Set the data to pass back to the Calling Activity --CK
     		    ************************************************************* */
		        //data.setData(Uri.parse(String.valueOf(value)));
		        data.setData(Uri.parse(txtToasterText.getText().toString()));
     			setResult(RESULT_OK, data);

     		    /* *************************************************************
     		     * Calling 'finish()' closes the activity --CK
     		    ************************************************************* */
     			finish();
			}
		});

        
    }
}