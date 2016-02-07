package weblayer.vendas.view;


import weblayer.vendas.d1.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public abstract class Activity_PainelList extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	
    	weblayer.toolbox.Common.DefinirTheme(this, "");
    	
    	super.onCreate(savedInstanceState);
        
    }
    
    public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible)
    {
    		//ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
    		//View inflated = stub.inflate();
          
    		//TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
    		//txtTitle.setText(title);
    		
    		//Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
    		//f(!btnHomeVisible)
    		//	btnHome.setVisibility(View.INVISIBLE);
    		
    		/*
    		Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
    		if(!btnFeedbackVisible)
    			btnFeedback.setVisibility(View.INVISIBLE);
    		*/
    		
    }
    
    public void btnHomeClick(View v)
    {
    	Intent intent = new Intent(getApplicationContext(), Activity_Home.class);
    	intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    }
    
}