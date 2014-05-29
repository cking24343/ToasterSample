package king.chad.toaster.sample;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import king.chad.toaster.ToastIt;
import king.chad.toaster.sample.PredefinedColorsActivity.MyAdapter;


public class MainActivity extends Activity 
{
	//--Toaster object--
	ToastIt toastIt;
	
	//--Variables--
	String string_spinner_icon;
	String string_spinner_icon_speed;
	private static final int PIC_REQUEST_EXTERNAL = 1001;
	private static final int HEXCODE_BACKGROUND_COLOR = 1002;
	private static final int HEXCODE_TXT_COLOR = 1003;
	private static final int TOASTER_MESSAGE = 1004;
	Bitmap bmp;
	Typeface font;
	String backgroundHexValue = "#000000";
	String textHexValue = "#ffffff";
	
	//--Sample Controls--
	LinearLayout llHexCode;
	LinearLayout llTxtHexCode;
	LinearLayout llToasterMessage;
	Button btnToatstIt;
	Button btnGetImage;
	TextView txtMessage;
	EditText txtDuration;
	Spinner spinnerFontAwesome;
	//Spinner spinnerFontAwesomeAnimation;
	//Spinner spinnerFontAwesomeSpeed;
	Spinner spinnerSelectFont;
	TextView txtBGColor;
	TextView txtMessageColor;
	LinearLayout canMessageColor;
	LinearLayout canBGColor;
	String[] fontIconValues;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(king.chad.toastersample.R.layout.sample_layout);
        
        //--Initialize our toaster object--
        /*
         * Accepts two parameters:
         * 1- The current context
         * 2- Boolean value indicating whether to use the bootstrap icon for toast or not
         */
        toastIt = new ToastIt(this, true);
        
        //--Get font style to be applied from assets folder--
	    //font = Typeface.createFromAsset(getAssets(), "Pacifico.ttf"); 
        
        //--Initialize our controls--
        btnToatstIt = (Button) findViewById(king.chad.toastersample.R.id.btnDisplayToast);
        btnGetImage = (Button) findViewById(king.chad.toastersample.R.id.btnGetImage);
        //txtBackgroundHexCode = (EditText) findViewById(king.chad.toastersample.R.id.txtHexCode);
        txtMessage = (TextView) findViewById(king.chad.toastersample.R.id.txtMessage);
        txtDuration = (EditText) findViewById(king.chad.toastersample.R.id.txtDuration);
        //txtMessageHexCodeColor = (EditText) findViewById(king.chad.toastersample.R.id.txtMessageColor);
        spinnerFontAwesome = (Spinner) findViewById(king.chad.toastersample.R.id.spinnerFontAwesome);
        //spinnerFontAwesomeAnimation = (Spinner) findViewById(king.chad.toastersample.R.id.spinnerFontAwesomeAnimation);
        //spinnerFontAwesomeSpeed = (Spinner) findViewById(king.chad.toastersample.R.id.spinnerFontAwesomeSpeed);
        spinnerSelectFont = (Spinner) findViewById(king.chad.toastersample.R.id.spinnerSelectFont);
        
        txtBGColor = (TextView) findViewById(king.chad.toastersample.R.id.txtBGColor);
        txtMessageColor = (TextView) findViewById(king.chad.toastersample.R.id.txtMessageColor);
        
        llHexCode = (LinearLayout) findViewById(king.chad.toastersample.R.id.llHexCode);
        llTxtHexCode = (LinearLayout) findViewById(king.chad.toastersample.R.id.llTxtHexCode);
        llToasterMessage = (LinearLayout) findViewById(king.chad.toastersample.R.id.llToasterMessage);
        
        canMessageColor = (LinearLayout) findViewById(king.chad.toastersample.R.id.canMessageColor);
        canBGColor = (LinearLayout) findViewById(king.chad.toastersample.R.id.canBGColor);
        
        
        //populateFontIcons();
        populateSpinnerFontIcons();
        //populateFontIconsAnimation();
        //populateFontIconsSpeed();
        populateFontTypeface();
        
        spinnerFontAwesome.setAdapter(new MyAdapter(this, king.chad.toastersample.R.layout.custom_font_spinner, fontIconValues));
        
        OnClickListener ll_click = new OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	Intent i = new Intent("king.chad.toaster.sample.HexcodeSelectorActivity");
				startActivityForResult(i, HEXCODE_BACKGROUND_COLOR);
            }
        };
        
        OnClickListener ll_click2 = new OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	Intent i = new Intent("king.chad.toaster.sample.HexcodeSelectorActivity");
				startActivityForResult(i, HEXCODE_TXT_COLOR);
            }
        };
        
        OnClickListener ll_click3 = new OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	Intent i = new Intent("king.chad.toaster.sample.ToasterTextActivity");
				startActivityForResult(i, TOASTER_MESSAGE);
            }
        };
        
        llHexCode.setOnClickListener(ll_click);
        llTxtHexCode.setOnClickListener(ll_click2);
        llToasterMessage.setOnClickListener(ll_click3);

        btnToatstIt.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {

				String hexCode;
				String textHexCode;
				int duration = 5000;
				
				/****************************************************/
				//--quick check for background hex code value--
				/****************************************************/
				    if(backgroundHexValue.length()<3)
				    {
				    	hexCode = "#000000";
				    }
				    else
				    {
				    	hexCode = backgroundHexValue.toString();
				    }
				    
				    if(!hexCode.contains("#"))
				    {
				    	hexCode = "#" + hexCode;
				    }
			    /****************************************************/
				    
			    /****************************************************/
				//--quick check for text hex code value--
				/****************************************************/
				    if(textHexValue.length()<3)
				    {
				    	textHexCode = "#ffffff";
				    }
				    else
				    {
				    	textHexCode = textHexValue.toString();
				    }
				    
				    if(!textHexCode.contains("#"))
				    {
				    	textHexCode = "#" + textHexCode;
				    }
			    /****************************************************/
			    
			    
			    /****************************************************/
				//--quick conversion for duration value--
			    /****************************************************/
				    try{
				    	duration = Integer.parseInt(
				    			txtDuration.getText().toString());
				    }
				    catch(Exception ex){}
			    /****************************************************/
			    
				    /*
			    toastIt.show(
			    		getResources().getDrawable(king.chad.toastersample.R.drawable.ic_launcher),
			        		txtMessage.getText().toString(), 
			        		textHexCode,
			        		hexCode, 
			        		duration);*/
				    
				    if(string_spinner_icon.equals("Select Icon..."))
				    {
				    	toastIt = new ToastIt(MainActivity.this, false);
				    	toastIt.show(
					    		getResources().getDrawable(king.chad.toastersample.R.drawable.ic_launcher),
					        		txtMessage.getText().toString(), 
					        		font,
					        		textHexCode,
					        		hexCode);
				    }
				    else
				    {
				    	toastIt = new ToastIt(MainActivity.this, true);
				    	/* 
				    	toastIt.show(
						    		string_spinner_icon,
						        		txtMessage.getText().toString(),
						        		textHexCode,
						        		hexCode, 
						        		duration);
						        		*/
				    	
				    	/*toastIt.show(
					    		string_spinner_icon,
					        		txtMessage.getText().toString(),
					        		font,
					        		textHexCode,
					        		hexCode, 
					        		duration, false, false, false, string_spinner_icon_speed);*/
				    	
				    	toastIt.show(
					    		string_spinner_icon,
					        		txtMessage.getText().toString(),
					        		font,
					        		textHexCode,
					        		hexCode, 
					        		duration);
				    }
				   
			}
		});
        
        btnGetImage.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				//--need to add intent to get image--
				Random generator = new Random();
                int n = 100000;
                n = generator.nextInt(n);
            	
	        	Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	        	i.setType("image/*");
	        	startActivityForResult(Intent.createChooser(i, "Select a photo"), PIC_REQUEST_EXTERNAL);
			}
			
        });
  
        spinnerFontAwesome.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
            {
				String val = (String)parentView.getItemAtPosition(position);
				string_spinner_icon = val;
            }

            public void onNothingSelected(AdapterView<?> parentView) 
            {
             
            }

       });
        
        /*spinnerFontAwesomeSpeed.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
            {
				String val = (String)parentView.getItemAtPosition(position);
				string_spinner_icon_speed = val;
            }

            public void onNothingSelected(AdapterView<?> parentView) 
            {
             
            }

       });*/
        
        spinnerSelectFont.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
            {
            	if(position > 0)
            	{
					String val = (String)parentView.getItemAtPosition(position);
					font = null;
					font = Typeface.createFromAsset(getAssets(), val); 
            	}
            }

            public void onNothingSelected(AdapterView<?> parentView) 
            {
             
            }

       });
    }
    
    @SuppressLint("ShowToast")
	public void displayToast(String text)
    {
    	Toast.makeText(this, text, Toast.LENGTH_LONG);
    }
    
    public void populateFontIcons()
    {
    	try
    	{
    		ArrayList<String> initialList = new ArrayList<String>();
    		
    		initialList.add("Select Icon...");
    		initialList.add("fa-glass");
    		initialList.add("fa-music");
    		initialList.add("fa-search");
    		initialList.add("fa-envelope-o");
    		initialList.add("fa-heart");
    		initialList.add("fa-star");
    		initialList.add("fa-star-o");
    		initialList.add("fa-user");
    		initialList.add("fa-film");
    		initialList.add("fa-th-large");
    		initialList.add("fa-th");
    		initialList.add("fa-th-list");
    		initialList.add("fa-check");
    		initialList.add("fa-times");
    		initialList.add("fa-search-plus");
    		initialList.add("fa-search-minus");
    		initialList.add("fa-power-off");
    		initialList.add("fa-signal");
    		initialList.add("fa-cog");
    		initialList.add("fa-trash-o");
    		initialList.add("fa-home");
    		initialList.add("fa-file-o");
    		initialList.add("fa-clock-o");
    		initialList.add("fa-road");
    		initialList.add("fa-download");
    		initialList.add("fa-arrow-circle-o-down");
    		initialList.add("fa-arrow-circle-o-up");
    		initialList.add("fa-inbox");
    		initialList.add("fa-play-circle-o");
    		initialList.add("fa-repeat");
    		initialList.add("fa-refresh");
    		initialList.add("fa-list-alt");
    		initialList.add("fa-lock");
    		initialList.add("fa-flag");
    		initialList.add("fa-headphones");
    		initialList.add("fa-volume-off");
    		initialList.add("fa-volume-down");
    		initialList.add("fa-volume-up");
    		initialList.add("fa-qrcode");
    		initialList.add("fa-barcode");
    		initialList.add("fa-tag");
    		initialList.add("fa-tags");
    		initialList.add("fa-book");
    		initialList.add("fa-bookmark");
    		initialList.add("fa-print");
    		initialList.add("fa-camera");
    		initialList.add("fa-font");
    		initialList.add("fa-bold");
    		initialList.add("fa-italic");
    		initialList.add("fa-text-height");
    		initialList.add("fa-text-width");
    		initialList.add("fa-align-left");
    		initialList.add("fa-align-center");
    		initialList.add("fa-align-right");
    		initialList.add("fa-align-justify");
    		initialList.add("fa-list");
    		initialList.add("fa-outdent");
    		initialList.add("fa-indent");
    		initialList.add("fa-video-camera");
    		initialList.add("fa-picture-o");
    		initialList.add("fa-pencil");
    		initialList.add("fa-map-marker");
    		initialList.add("fa-adjust");
    		initialList.add("fa-tint");
    		initialList.add("fa-pencil-square-o");
    		initialList.add("fa-share-square-o");
    		initialList.add("fa-check-square-o");
    		initialList.add("fa-move");
    		initialList.add("fa-step-backward");
    		initialList.add("fa-fast-backward");
    		initialList.add("fa-backward");
    		initialList.add("fa-play");
    		initialList.add("fa-pause");
    		initialList.add("fa-stop");
    		initialList.add("fa-forward");
    		initialList.add("fa-fast-forward");
    		initialList.add("fa-step-forward");
    		initialList.add("fa-eject");
    		initialList.add("fa-chevron-left");
    		initialList.add("fa-chevron-right");
    		initialList.add("fa-plus-circle");
    		initialList.add("fa-minus-circle");
    		initialList.add("fa-times-circle");
    		initialList.add("fa-check-circle");
    		initialList.add("fa-question-circle");
    		initialList.add("fa-info-circle");
    		initialList.add("fa-crosshairs");
    		initialList.add("fa-times-circle-o");
    		initialList.add("fa-check-circle-o");
    		initialList.add("fa-ban");
    		initialList.add("fa-arrow-left");
    		initialList.add("fa-arrow-right");
    		initialList.add("fa-arrow-up");
    		initialList.add("fa-arrow-down");
    		initialList.add("fa-share");
    		initialList.add("fa-resize-full");
    		initialList.add("fa-resize-small");
    		initialList.add("fa-plus");
    		initialList.add("fa-minus");
    		initialList.add("fa-asterisk");
    		initialList.add("fa-exclamation-circle");
    		initialList.add("fa-gift");
    		initialList.add("fa-leaf");
    		initialList.add("fa-fire");
    		initialList.add("fa-eye");
    		initialList.add("fa-eye-slash");
    		initialList.add("fa-exclamation-triangle");
    		initialList.add("fa-plane");
    		initialList.add("fa-calendar");
    		initialList.add("fa-random");
    		initialList.add("fa-comment");
    		initialList.add("fa-magnet");
    		initialList.add("fa-chevron-up");
    		initialList.add("fa-chevron-down");
    		initialList.add("fa-retweet");
    		initialList.add("fa-shopping-cart");
    		initialList.add("fa-folder");
    		initialList.add("fa-folder-open");
    		initialList.add("fa-resize-vertical");
    		initialList.add("fa-resize-horizontal");
    		initialList.add("fa-bar-chart-o");
    		initialList.add("fa-twitter-square");
    		initialList.add("fa-facebook-square");
    		initialList.add("fa-camera-retro");
    		initialList.add("fa-key");
    		initialList.add("fa-cogs");
    		initialList.add("fa-comments");
    		initialList.add("fa-thumbs-o-up");
    		initialList.add("fa-thumbs-o-down");
    		initialList.add("fa-star-half");
    		initialList.add("fa-heart-o");
    		initialList.add("fa-sign-out");
    		initialList.add("fa-linkedin-square");
    		initialList.add("fa-thumb-tack");
    		initialList.add("fa-external-link");
    		initialList.add("fa-sign-in");
    		initialList.add("fa-trophy");
    		initialList.add("fa-github-square");
    		initialList.add("fa-upload");
    		initialList.add("fa-lemon-o");
    		initialList.add("fa-phone");
    		initialList.add("fa-square-o");
    		initialList.add("fa-bookmark-o");
    		initialList.add("fa-phone-square");
    		initialList.add("fa-twitter");
    		initialList.add("fa-facebook");
    		initialList.add("fa-github");
    		initialList.add("fa-unlock");
    		initialList.add("fa-credit-card");
    		initialList.add("fa-rss");
    		initialList.add("fa-hdd");
    		initialList.add("fa-bullhorn");
    		initialList.add("fa-bell");
    		initialList.add("fa-certificate");
    		initialList.add("fa-hand-o-right");
    		initialList.add("fa-hand-o-left");
    		initialList.add("fa-hand-o-up");
    		initialList.add("fa-hand-o-down");
    		initialList.add("fa-arrow-circle-left");
    		initialList.add("fa-arrow-circle-right");
    		initialList.add("fa-arrow-circle-up");
    		initialList.add("fa-arrow-circle-down");
    		initialList.add("fa-globe");
    		initialList.add("fa-wrench");
    		initialList.add("fa-tasks");
    		initialList.add("fa-filter");
    		initialList.add("fa-briefcase");
    		initialList.add("fa-fullscreen");
    		initialList.add("fa-group");
    		initialList.add("fa-link");
    		initialList.add("fa-cloud");
    		initialList.add("fa-flask");
    		initialList.add("fa-scissors");
    		initialList.add("fa-files-o");
    		initialList.add("fa-paperclip");
    		initialList.add("fa-floppy-o");
    		initialList.add("fa-square");
    		initialList.add("fa-reorder");
    		initialList.add("fa-list-ul");
    		initialList.add("fa-list-ol");
    		initialList.add("fa-strikethrough");
    		initialList.add("fa-underline");
    		initialList.add("fa-table");
    		initialList.add("fa-magic");
    		initialList.add("fa-truck");
    		initialList.add("fa-pinterest");
    		initialList.add("fa-pinterest-square");
    		initialList.add("fa-google-plus-square");
    		initialList.add("fa-google-plus");
    		initialList.add("fa-money");
    		initialList.add("fa-caret-down");
    		initialList.add("fa-caret-up");
    		initialList.add("fa-caret-left");
    		initialList.add("fa-caret-right");
    		initialList.add("fa-columns");
    		initialList.add("fa-sort");
    		initialList.add("fa-sort-asc");
    		initialList.add("fa-sort-desc");
    		initialList.add("fa-envelope");
    		initialList.add("fa-linkedin");
    		initialList.add("fa-undo");
    		initialList.add("fa-gavel");
    		initialList.add("fa-tachometer");
    		initialList.add("fa-comment-o");
    		initialList.add("fa-comments-o");
    		initialList.add("fa-bolt");
    		initialList.add("fa-sitemap");
    		initialList.add("fa-umbrella");
    		initialList.add("fa-clipboard");
    		initialList.add("fa-lightbulb-o");
    		initialList.add("fa-exchange");
    		initialList.add("fa-cloud-download");
    		initialList.add("fa-cloud-upload");
    		initialList.add("fa-user-md");
    		initialList.add("fa-stethoscope");
    		initialList.add("fa-suitcase");
    		initialList.add("fa-bell-o");
    		initialList.add("fa-coffee");
    		initialList.add("fa-cutlery");
    		initialList.add("fa-file-text-o");
    		initialList.add("fa-building");
    		initialList.add("fa-hospital");
    		initialList.add("fa-ambulance");
    		initialList.add("fa-medkit");
    		initialList.add("fa-fighter-jet");
    		initialList.add("fa-beer");
    		initialList.add("fa-h-square");
    		initialList.add("fa-plus-square");
    		initialList.add("fa-angle-double-left");
    		initialList.add("fa-angle-double-right");
    		initialList.add("fa-angle-double-up");
    		initialList.add("fa-angle-double-down");
    		initialList.add("fa-angle-left");
    		initialList.add("fa-angle-right");
    		initialList.add("fa-angle-up");
    		initialList.add("fa-angle-down");
    		initialList.add("fa-desktop");
    		initialList.add("fa-laptop");
    		initialList.add("fa-tablet");
    		initialList.add("fa-mobile");
    		initialList.add("fa-circle-o");
    		initialList.add("fa-quote-left");
    		initialList.add("fa-quote-right");
    		initialList.add("fa-spinner");
    		initialList.add("fa-circle");
    		initialList.add("fa-reply");
    		initialList.add("fa-github-alt");
    		initialList.add("fa-folder-o");
    		initialList.add("fa-folder-open-o");
    		initialList.add("fa-expand-o");
    		initialList.add("fa-collapse-o");
    		initialList.add("fa-smile-o");
    		initialList.add("fa-frown-o");
    		initialList.add("fa-meh-o");
    		initialList.add("fa-gamepad");
    		initialList.add("fa-keyboard-o");
    		initialList.add("fa-flag-o");
    		initialList.add("fa-flag-checkered");
    		initialList.add("fa-terminal");
    		initialList.add("fa-code");
    		initialList.add("fa-reply-all");
    		initialList.add("fa-mail-reply-all");
    		initialList.add("fa-star-half-o");
    		initialList.add("fa-location-arrow");
    		initialList.add("fa-crop");
    		initialList.add("fa-code-fork");
    		initialList.add("fa-chain-broken");
    		initialList.add("fa-question");
    		initialList.add("fa-info");
    		initialList.add("fa-exclamation");
    		initialList.add("fa-superscript");
    		initialList.add("fa-subscript");
    		initialList.add("fa-eraser");
    		initialList.add("fa-puzzle-piece");
    		initialList.add("fa-microphone");
    		initialList.add("fa-microphone-slash");
    		initialList.add("fa-shield");
    		initialList.add("fa-calendar-o");
    		initialList.add("fa-fire-extinguisher");
    		initialList.add("fa-rocket");
    		initialList.add("fa-maxcdn");
    		initialList.add("fa-chevron-circle-left");
    		initialList.add("fa-chevron-circle-right");
    		initialList.add("fa-chevron-circle-up");
    		initialList.add("fa-chevron-circle-down");
    		initialList.add("fa-html5");
    		initialList.add("fa-css3");
    		initialList.add("fa-anchor");
    		initialList.add("fa-unlock-o");
    		initialList.add("fa-bullseye");
    		initialList.add("fa-ellipsis-horizontal");
    		initialList.add("fa-ellipsis-vertical");
    		initialList.add("fa-rss-square");
    		initialList.add("fa-play-circle");
    		initialList.add("fa-ticket");
    		initialList.add("fa-minus-square");
    		initialList.add("fa-minus-square-o");
    		initialList.add("fa-level-up");
    		initialList.add("fa-level-down");
    		initialList.add("fa-check-square");
    		initialList.add("fa-pencil-square");
    		initialList.add("fa-external-link-square");
    		initialList.add("fa-share-square");
    		initialList.add("fa-compass");
    		initialList.add("fa-caret-square-o-down");
    		initialList.add("fa-caret-square-o-up");
    		initialList.add("fa-caret-square-o-right");
    		initialList.add("fa-eur");
    		initialList.add("fa-gbp");
    		initialList.add("fa-usd");
    		initialList.add("fa-inr");
    		initialList.add("fa-jpy");
    		initialList.add("fa-rub");
    		initialList.add("fa-krw");
    		initialList.add("fa-btc");
    		initialList.add("fa-file");
    		initialList.add("fa-file-text");
    		initialList.add("fa-sort-alpha-asc");
    		initialList.add("fa-sort-alpha-desc");
    		initialList.add("fa-sort-amount-asc");
    		initialList.add("fa-sort-amount-desc");
    		initialList.add("fa-sort-numeric-asc");
    		initialList.add("fa-sort-numeric-desc");
    		initialList.add("fa-thumbs-up");
    		initialList.add("fa-thumbs-down");
    		initialList.add("fa-youtube-square");
    		initialList.add("fa-youtube");
    		initialList.add("fa-xing");
    		initialList.add("fa-xing-square");
    		initialList.add("fa-youtube-play");
    		initialList.add("fa-dropbox");
    		initialList.add("fa-stack-overflow");
    		initialList.add("fa-instagram");
    		initialList.add("fa-flickr");
    		initialList.add("fa-adn");
    		initialList.add("fa-bitbucket");
    		initialList.add("fa-bitbucket-square");
    		initialList.add("fa-tumblr");
    		initialList.add("fa-tumblr-square");
    		initialList.add("fa-long-arrow-down");
    		initialList.add("fa-long-arrow-up");
    		initialList.add("fa-long-arrow-left");
    		initialList.add("fa-long-arrow-right");
    		initialList.add("fa-apple");
    		initialList.add("fa-windows");
    		initialList.add("fa-android");
    		initialList.add("fa-linux");
    		initialList.add("fa-dribbble");
    		initialList.add("fa-skype");
    		initialList.add("fa-foursquare");
    		initialList.add("fa-trello");
    		initialList.add("fa-female");
    		initialList.add("fa-male");
    		initialList.add("fa-gittip");
    		initialList.add("fa-sun-o");
    		initialList.add("fa-moon-o");
    		initialList.add("fa-archive");
    		initialList.add("fa-bug");
    		initialList.add("fa-vk");
    		initialList.add("fa-weibo");
    		initialList.add("fa-renren");
    		initialList.add("fa-pagelines");
    		initialList.add("fa-stack-exchange");
    		initialList.add("fa-arrow-circle-o-right");
    		initialList.add("fa-arrow-circle-o-left");
    		initialList.add("fa-caret-square-o-left");
    		initialList.add("fa-dot-circle-o");
    		initialList.add("fa-wheelchair");
    		initialList.add("fa-vimeo-square");

        	ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, initialList);
        	myAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        	
        	Spinner s1 =(Spinner) findViewById(king.chad.toastersample.R.id.spinnerFontAwesome);
            s1.setAdapter(myAdapter);
        	
            spinnerFontAwesome = s1;
        	
    	 } 
    	catch (Exception e)
    	{
    		Log.e("Error", "Error in code: " + e.toString());
    		e.printStackTrace();
    	}
        finally 
        {

        }
    }

    
   /* public void populateFontIconsSpeed()
    {
    	try
    	{
    		ArrayList<String> initialList = new ArrayList<String>();
    		
    		initialList.add("Select Icon Speed...");
    		initialList.add("FAST");
    		initialList.add("MEDIUM");
    		initialList.add("SLOW");
    		
    		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, initialList);
        	myAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        	
        	Spinner s1 =(Spinner) findViewById(king.chad.toastersample.R.id.spinnerFontAwesomeSpeed);
            s1.setAdapter(myAdapter);
        	
            spinnerFontAwesomeSpeed = s1;
        	
    	 } 
    	catch (Exception e)
    	{
    		Log.e("Error", "Error in code: " + e.toString());
    		e.printStackTrace();
    	}
        finally 
        {

        }
    }*/
    
    public void populateFontTypeface()
    {
    	try
    	{
    		ArrayList<String> initialList = new ArrayList<String>();
    		
    		initialList.add("Select Toaster Font...");
    		initialList.add("KaushanScript-Regular.otf");
    		initialList.add("Quicksand-Regular.otf");
    		initialList.add("Barrio-Regular.otf");
    		initialList.add("FoglihtenNo07.otf");
    		initialList.add("FrenteH1-Regular.otf");
    		initialList.add("HennyPenny-Regular.otf");
    		initialList.add("LeagueScript.otf");
    		initialList.add("porter-sans-inline-block.otf");
    		initialList.add("Railway.otf");
    		initialList.add("troika.otf");
    		
    		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, initialList);
        	myAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        	
        	Spinner s1 =(Spinner) findViewById(king.chad.toastersample.R.id.spinnerSelectFont);
            s1.setAdapter(myAdapter);
        	
            spinnerSelectFont = s1;
        	
    	 } 
    	catch (Exception e)
    	{
    		Log.e("Error", "Error in code: " + e.toString());
    		e.printStackTrace();
    	}
        finally 
        {

        }
    }

    public void populateSpinnerFontIcons()
    {
    	
    	try{
    		fontIconValues = new String[] { 
    				"Select Icon...",    	    		"fa-glass",
    	    		"fa-music",    	    		"fa-search",
    	    		"fa-envelope-o",    	    		"fa-heart",
    	    		"fa-star",    	    		"fa-star-o",
    	    		"fa-user",    	    		"fa-film",
    	    		"fa-th-large",    	    		"fa-th",
    	    		"fa-th-list",    	    		"fa-check",
    	    		"fa-times",    	    		"fa-search-plus",
    	    		"fa-search-minus",    	    		"fa-power-off",
    	    		"fa-signal",    	    		"fa-cog",
    	    		"fa-trash-o",    	    		"fa-home",
    	    		"fa-file-o",    	    		"fa-clock-o",
    	    		"fa-road",    	    		"fa-download",
    	    		"fa-arrow-circle-o-down",    	    		"fa-arrow-circle-o-up",
    	    		"fa-inbox",    	    		"fa-play-circle-o",
    	    		"fa-repeat",    	    		"fa-refresh",
    	    		"fa-list-alt",    	    		"fa-lock",
    	    		"fa-flag",    	    		"fa-headphones",
    	    		"fa-volume-off",    	    		"fa-volume-down",
    	    		"fa-volume-up",    	    		"fa-qrcode",
    	    		"fa-barcode",    	    		"fa-tag",
    	    		"fa-tags",    	    		"fa-book",
    	    		"fa-bookmark",    	    		"fa-print",
    	    		"fa-camera",    	    		"fa-font",
    	    		"fa-bold",    	    		"fa-italic",
    	    		"fa-text-height",    	    		"fa-text-width",
    	    		"fa-align-left",    	    		"fa-align-center",
    	    		"fa-align-right",    	    		"fa-align-justify",
    	    		"fa-list",    	    		"fa-outdent",
    	    		"fa-indent",    	    		"fa-video-camera",
    	    		"fa-picture-o",    	    		"fa-pencil",
    	    		"fa-map-marker",    	    		"fa-adjust",
    	    		"fa-tint",    	    		"fa-pencil-square-o",
    	    		"fa-share-square-o",    	    		"fa-check-square-o",
    	    		"fa-move",    	    		"fa-step-backward",
    	    		"fa-fast-backward",    	    		"fa-backward",
    	    		"fa-play",    	    		"fa-pause",
    	    		"fa-stop",    	    		"fa-forward",
    	    		"fa-fast-forward",    	    		"fa-step-forward",
    	    		"fa-eject",    	    		"fa-chevron-left",
    	    		"fa-chevron-right",    	    		"fa-plus-circle",
    	    		"fa-minus-circle",    	    		"fa-times-circle",
    	    		"fa-check-circle",    	    		"fa-question-circle",
    	    		"fa-info-circle",    	    		"fa-crosshairs",
    	    		"fa-times-circle-o",    	    		"fa-check-circle-o",
    	    		"fa-ban",    	    		"fa-arrow-left",
    	    		"fa-arrow-right",    	    		"fa-arrow-up",
    	    		"fa-arrow-down",    	    		"fa-share",
    	    		"fa-resize-full",   	    		"fa-resize-small",
    	    		"fa-plus",    	    		"fa-minus",
    	    		"fa-asterisk",    	    		"fa-exclamation-circle",
    	    		"fa-gift",    	    		"fa-leaf",
    	    		"fa-fire",    	    		"fa-eye",
    	    		"fa-eye-slash",    	    		"fa-exclamation-triangle",
    	    		"fa-plane",    	    		"fa-calendar",
    	    		"fa-random",    	    		"fa-comment",
    	    		"fa-magnet",    	    		"fa-chevron-up",
    	    		"fa-chevron-down",    	    		"fa-retweet",
    	    		"fa-shopping-cart",    	    		"fa-folder",
    	    		"fa-folder-open",    	    		"fa-resize-vertical",
    	    		"fa-resize-horizontal",    	    		"fa-bar-chart-o",
    	    		"fa-twitter-square",    	    		"fa-facebook-square",
    	    		"fa-camera-retro",    	    		"fa-key",
    	    		"fa-cogs",    	    		"fa-comments",
    	    		"fa-thumbs-o-up",    	    		"fa-thumbs-o-down",
    	    		"fa-star-half",    	    		"fa-heart-o",
    	    		"fa-sign-out",    	    		"fa-linkedin-square",
    	    		"fa-thumb-tack",    	    		"fa-external-link",
    	    		"fa-sign-in",    	    		"fa-trophy",
    	    		"fa-github-square",    	    		"fa-upload",
    	    		"fa-lemon-o",    	    		"fa-phone",
    	    		"fa-square-o",    	    		"fa-bookmark-o",
    	    		"fa-phone-square",    	    		"fa-twitter",
    	    		"fa-facebook",    	    		"fa-github",
    	    		"fa-unlock",    	    		"fa-credit-card",
    	    		"fa-rss",    	    		"fa-hdd",
    	    		"fa-bullhorn",    	    		"fa-bell",
    	    		"fa-certificate",    	    		"fa-hand-o-right",
    	    		"fa-hand-o-left",    	    		"fa-hand-o-up",
    	    		"fa-hand-o-down",    	    		"fa-arrow-circle-left",
    	    		"fa-arrow-circle-right",    	    		"fa-arrow-circle-up",
    	    		"fa-arrow-circle-down",    	    		"fa-globe",
    	    		"fa-wrench",    	    		"fa-tasks",
    	    		"fa-filter",    	    		"fa-briefcase",
    	    		"fa-fullscreen",    	    		"fa-group",
    	    		"fa-link",    	    		"fa-cloud",
    	    		"fa-flask",    	    		"fa-scissors",
    	    		"fa-files-o",    	    		"fa-paperclip",
    	    		"fa-floppy-o",    	    		"fa-square",
    	    		"fa-reorder",    	    		"fa-list-ul",
    	    		"fa-list-ol",    	    		"fa-strikethrough",
    	    		"fa-underline",    	    		"fa-table",
    	    		"fa-magic",    	    		"fa-truck",
    	    		"fa-pinterest",    	    		"fa-pinterest-square",
    	    		"fa-google-plus-square",    	    		"fa-google-plus",
    	    		"fa-money",    	    		"fa-caret-down",
    	    		"fa-caret-up",    	    		"fa-caret-left",
    	    		"fa-caret-right",    	    		"fa-columns",
    	    		"fa-sort",    	    		"fa-sort-asc",
    	    		"fa-sort-desc",    	    		"fa-envelope",
    	    		"fa-linkedin",    	    		"fa-undo",
    	    		"fa-gavel",    	    		"fa-tachometer",
    	    		"fa-comment-o",    	    		"fa-comments-o",
    	    		"fa-bolt",    	    		"fa-sitemap",
    	    		"fa-umbrella",    	    		"fa-clipboard",
    	    		"fa-lightbulb-o",    	    		"fa-exchange",
    	    		"fa-cloud-download",    	    		"fa-cloud-upload",
    	    		"fa-user-md",    	    		"fa-stethoscope",
    	    		"fa-suitcase",    	    		"fa-bell-o",
    	    		"fa-coffee",    	    		"fa-cutlery",
    	    		"fa-file-text-o",    	    		"fa-building",
    	    		"fa-hospital",    	    		"fa-ambulance",
    	    		"fa-medkit",    	    		"fa-fighter-jet",
    	    		"fa-beer",    	    		"fa-h-square",
    	    		"fa-plus-square",    	    		"fa-angle-double-left",
    	    		"fa-angle-double-right",    	    		"fa-angle-double-up",
    	    		"fa-angle-double-down",    	    		"fa-angle-left",
    	    		"fa-angle-right",    	    		"fa-angle-up",
    	    		"fa-angle-down",    	    		"fa-desktop",
    	    		"fa-laptop",    	    		"fa-tablet",
    	    		"fa-mobile",    	    		"fa-circle-o",
    	    		"fa-quote-left",    	    		"fa-quote-right",
    	    		"fa-spinner",    	    		"fa-circle",
    	    		"fa-reply",    	    		"fa-github-alt",
    	    		"fa-folder-o",    	    		"fa-folder-open-o",
    	    		"fa-expand-o",    	    		"fa-collapse-o",
    	    		"fa-smile-o",    	    		"fa-frown-o",
    	    		"fa-meh-o",    	    		"fa-gamepad",
    	    		"fa-keyboard-o",    	    		"fa-flag-o",
    	    		"fa-flag-checkered",    	    		"fa-terminal",
    	    		"fa-code",    	    		"fa-reply-all",
    	    		"fa-mail-reply-all",    	    		"fa-star-half-o",
    	    		"fa-location-arrow",    	    		"fa-crop",
    	    		"fa-code-fork",    	    		"fa-chain-broken",
    	    		"fa-question",    	    		"fa-info",
    	    		"fa-exclamation",    	    		"fa-superscript",
    	    		"fa-subscript",    	    		"fa-eraser",
    	    		"fa-puzzle-piece",    	    		"fa-microphone",
    	    		"fa-microphone-slash",    	    		"fa-shield",
    	    		"fa-calendar-o",    	    		"fa-fire-extinguisher",
    	    		"fa-rocket",    	    		"fa-maxcdn",
    	    		"fa-chevron-circle-left",    	    		"fa-chevron-circle-right",
    	    		"fa-chevron-circle-up",    	    		"fa-chevron-circle-down",
    	    		"fa-html5",    	    		"fa-css3",
    	    		"fa-anchor",    	    		"fa-unlock-o",
    	    		"fa-bullseye",    	    		"fa-ellipsis-horizontal",
    	    		"fa-ellipsis-vertical",    	    		"fa-rss-square",
    	    		"fa-play-circle",    	    		"fa-ticket",
    	    		"fa-minus-square",    	    		"fa-minus-square-o",
    	    		"fa-level-up",    	    		"fa-level-down",
    	    		"fa-check-square",    	    		"fa-pencil-square",
    	    		"fa-external-link-square",    	    		"fa-share-square",
    	    		"fa-compass",    	    		"fa-caret-square-o-down",
    	    		"fa-caret-square-o-up",    	    		"fa-caret-square-o-right",
    	    		"fa-eur",    	    		"fa-gbp",
    	    		"fa-usd",    	    		"fa-inr",
    	    		"fa-jpy",    	    		"fa-rub",
    	    		"fa-krw",    	    		"fa-btc",
    	    		"fa-file",    	    		"fa-file-text",
    	    		"fa-sort-alpha-asc",    	    		"fa-sort-alpha-desc",
    	    		"fa-sort-amount-asc",    	    		"fa-sort-amount-desc",
    	    		"fa-sort-numeric-asc",    	    		"fa-sort-numeric-desc",
    	    		"fa-thumbs-up",    	    		"fa-thumbs-down",
    	    		"fa-youtube-square",    	    		"fa-youtube",
    	    		"fa-xing",    	    		"fa-xing-square",
    	    		"fa-youtube-play",    	    		"fa-dropbox",
    	    		"fa-stack-overflow",    	    		"fa-instagram",
    	    		"fa-flickr",    	    		"fa-adn",
    	    		"fa-bitbucket",    	    		"fa-bitbucket-square",
    	    		"fa-tumblr",    	    		"fa-tumblr-square",
    	    		"fa-long-arrow-down",    	    		"fa-long-arrow-up",
    	    		"fa-long-arrow-left",    	    		"fa-long-arrow-right",
    	    		"fa-apple",    	    		"fa-windows",
    	    		"fa-android",    	    		"fa-linux",
    	    		"fa-dribbble",    	    		"fa-skype",
    	    		"fa-foursquare",    	    		"fa-trello",
    	    		"fa-female",    	    		"fa-male",
    	    		"fa-gittip",    	    		"fa-sun-o",
    	    		"fa-moon-o",    	    		"fa-archive",
    	    		"fa-bug",    	    		"fa-vk",
    	    		"fa-weibo",    	    		"fa-renren",
    	    		"fa-pagelines",    	    		"fa-stack-exchange",
    	    		"fa-arrow-circle-o-right",    	    		"fa-arrow-circle-o-left",
    	    		"fa-caret-square-o-left",    	    		"fa-dot-circle-o",
    	    		"fa-wheelchair",    	    		"fa-vimeo-square"
    		};
    	}catch(Exception ex){}
    }
    
    @SuppressWarnings("deprecation")
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (resultCode == Activity.RESULT_CANCELED)
      	{
      		//---Display message when cancel detected---
      		Toast.makeText(this, "Action was cancelled by user...", Toast.LENGTH_LONG).show();	   
      	}
    	else if (requestCode == HEXCODE_BACKGROUND_COLOR)
    	{
    		if (resultCode == RESULT_OK)
    		{
    			backgroundHexValue = data.getDataString().toString();
    			txtBGColor.setText("Selected Hexcode: " + backgroundHexValue);
    			//llHexCode.setBackgroundColor(Color.parseColor(backgroundHexValue));
    			canBGColor.setBackgroundColor(Color.parseColor(backgroundHexValue));
    		}
    	}
    	else if (requestCode == HEXCODE_TXT_COLOR)
    	{
    		if (resultCode == RESULT_OK)
    		{
    			textHexValue = data.getDataString().toString();
    			txtMessageColor.setText("Selected Hexcode: " + textHexValue);
    			//llTxtHexCode.setBackgroundColor(Color.parseColor(textHexValue));
    			canMessageColor.setBackgroundColor(Color.parseColor(textHexValue));
    		}
    	}
    	else if (requestCode == TOASTER_MESSAGE)
    	{
    		if (resultCode == RESULT_OK)
    		{
    			txtMessage.setText(data.getDataString().toString());
    		}
    	}
    	else if (requestCode == PIC_REQUEST_EXTERNAL)
    	{
    		if (resultCode == RESULT_OK)
    		{
    	    	    try 
    	    	    {
    	    			
    	        			Uri selectedImage = data.getData();
    	                    String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
    	                    
    	                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
    	                    startManagingCursor(cursor);
    	                    cursor.moveToFirst();

    	                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
    	                    int columnName = cursor.getColumnIndex(filePathColumn[1]);
    	                    String filePath = cursor.getString(columnIndex);
    	                    File path = new File(filePath);
    	                   
    	                   
    	    	            		try 
    	    	            		{
    	    	            			bmp = (loadBitmap(path.toString(), 32, 32));
    	    	            			
    	    	            			String hexCode;
    	    	        				String textHexCode;
    	    	        				int duration = 5000;
    	    	        				
    	    	        				/****************************************************/
    	    	        				//--quick check for background hex code value--
    	    	        				/****************************************************/
    	    	        				if(backgroundHexValue.length()<3)
    	    	    				    {
    	    	    				    	hexCode = "#000000";
    	    	    				    }
    	    	    				    else
    	    	    				    {
    	    	    				    	hexCode = backgroundHexValue.toString();
    	    	    				    }
    	    	    				    
    	    	    				    if(!hexCode.contains("#"))
    	    	    				    {
    	    	    				    	hexCode = "#" + hexCode;
    	    	    				    }
    	    	        			    /****************************************************/
    	    	        				    
    	    	        			    /****************************************************/
    	    	        				//--quick check for text hex code value--
    	    	        				/****************************************************/
    	    	        				    if(textHexValue.length()<3)
    	    	        				    {
    	    	        				    	textHexCode = "#ffffff";
    	    	        				    }
    	    	        				    else
    	    	        				    {
    	    	        				    	textHexCode = textHexValue.toString();
    	    	        				    }
    	    	        				    
    	    	        				    if(!textHexCode.contains("#"))
    	    	        				    {
    	    	        				    	textHexCode = "#" + textHexCode;
    	    	        				    }
    	    	        			    /****************************************************/
    	    	        			    
    	    	        			    
    	    	        			    /****************************************************/
    	    	        				//--quick conversion for duration value--
    	    	        			    /****************************************************/
    	    	        				    try{
    	    	        				    	duration = Integer.parseInt(
    	    	        				    			txtDuration.getText().toString());
    	    	        				    }
    	    	        				    catch(Exception ex){}
    	    	        			    /****************************************************/
    	    	        			    
    	    	        				
    	    	        				    	toastIt = new ToastIt(MainActivity.this, false);
    	    	        				    	 toastIt.show(
    	    	        						    		bmp,
    	    	        						        		txtMessage.getText().toString(),
    	    	        						        		textHexCode,
    	    	        						        		hexCode, 
    	    	        						        		duration);
    	    	        				    
    	    	            		} 
    	    	            		catch (Exception e) 
    	    	            		{
    	    	            		       e.printStackTrace();
    	    	            		}
    	        	
    		 	    }
    		    	catch (Exception e)
    		       	{
    		       		Log.e("Error", "Error in code: " + e.toString());
    		       		e.printStackTrace();
    		       	}
			}
    	}
    		
    }

	static class BitmapLoader
	{
		  public static int getScale(int originalWidth,int originalHeight, final int requiredWidth,final int requiredHeight)
		  {
			 //a scale of 1 means the original dimensions
			 //of the image are maintained
			 int scale=1;
			        
			 //calculate scale only if the height or width of
			 //the image exceeds the required value.
		   if((originalWidth>requiredWidth) || (originalHeight>requiredHeight))
		   {
		       //calculate scale with respect to
			    //the smaller dimension
			    if(originalWidth<originalHeight)
			    	scale=Math.round((float)originalWidth/requiredWidth);
			    else
			    	scale=Math.round((float)originalHeight/requiredHeight);
		 
		    }
			       
			  return scale;
		  }
	}
	
	public static BitmapFactory.Options getOptions(String filePath, int requiredWidth,int requiredHeight)
	{
			   
			 BitmapFactory.Options options=new BitmapFactory.Options();
			 //setting inJustDecodeBounds to true
			 //ensures that we are able to measure
			 //the dimensions of the image,without
			 //actually allocating it memory
			 options.inJustDecodeBounds=true;
			   
			 //decode the file for measurement
			 BitmapFactory.decodeFile(filePath,options);
			   
			 //obtain the inSampleSize for loading a
			 //scaled down version of the image.
			 //options.outWidth and options.outHeight
			 //are the measured dimensions of the
			 //original image
			 options.inSampleSize = BitmapLoader.getScale(options.outWidth, options.outHeight, requiredWidth, requiredHeight);
			   
			 //set inJustDecodeBounds to false again
			 //so that we can now actually allocate the
			 //bitmap some memory
			 options.inJustDecodeBounds=false;
			   
			 return options;
			   
	}
	
	public static Bitmap loadBitmap(String filePath, int requiredWidth,int requiredHeight)
	{	 
		BitmapFactory.Options options= getOptions(filePath, requiredWidth, requiredHeight);
	   
		return BitmapFactory.decodeFile(filePath,options);
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
	    		View mySpinner = inflater.inflate(king.chad.toastersample.R.layout.custom_font_spinner, parent, false); 
	    		
	    		com.beardedhen.bbutton.FontAwesomeText main_icon = (com.beardedhen.bbutton.FontAwesomeText) mySpinner.findViewById(king.chad.toastersample.R.id.imgFontAwesome); 
	    		main_icon.setIcon(fontIconValues[position]);
	    		
	    		TextView main_text = (TextView) mySpinner.findViewById(king.chad.toastersample.R.id.txtFontIconName); 
	    		main_text.setText(fontIconValues[position]);
	    		
	    		return mySpinner;
	    	} 
	    }

}
