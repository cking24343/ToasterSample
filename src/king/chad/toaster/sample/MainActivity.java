package king.chad.toaster.sample;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.View;
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
	//EditText txtBackgroundHexCode;
	TextView txtMessage;
	EditText txtDuration;
	//EditText txtMessageHexCodeColor;
	Spinner spinnerFontAwesome;
	//Spinner spinnerFontAwesomeAnimation;
	//Spinner spinnerFontAwesomeSpeed;
	Spinner spinnerSelectFont;
	TextView txtBGColor;
	TextView txtMessageColor;
	LinearLayout canMessageColor;
	LinearLayout canBGColor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(king.chad.toastersample.R.layout.sample_layout);
        
        //--Initialize our toaster--
        //toastIt = new ToastIt(this);
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
        
        
        populateFontIcons();
        //populateFontIconsAnimation();
        //populateFontIconsSpeed();
        populateFontTypeface();
        
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
				    	
				    	toastIt.show(
					    		string_spinner_icon,
					        		txtMessage.getText().toString(),
					        		font,
					        		textHexCode,
					        		hexCode, 
					        		duration, false, false, false, string_spinner_icon_speed);
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
    	    	        			    
    	    	        				
    	    	        				    	toastIt = new ToastIt(MainActivity.this);
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
}
