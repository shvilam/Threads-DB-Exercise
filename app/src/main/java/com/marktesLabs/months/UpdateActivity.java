package com.marktesLabs.months;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;



public class UpdateActivity extends MyActionBarActivity implements Validator.ValidationListener {

    private static final String TAG = "Update";

    @Required(order = 2)
    @TextRule(message = "you must chose a month",minLength = 3,order = 1)
	private AutoCompleteTextView actv;

    @Required(order = 2)
    @NumberRule(type = NumberRule.NumberType.INTEGER,gt=27,lt = 32,message = "You should enter a valid number between 28 to 31",order = 1)
    private EditText etNumOofDays;

	private Button btnAddUpdate;
    private Validator validator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.setValidationListener(this);
        etNumOofDays = (EditText)findViewById(R.id.monthNum);
		actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteMonth);
		btnAddUpdate  = (Button)findViewById(R.id.addUpdateDays);
		
		String[] months = getResources().getString(R.string.months_strings).split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,months);
		actv.setAdapter(adapter);
		actv.setThreshold(1);//will start working from first character
		
		
		btnAddUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				validator.validate();
				
			}
		});
		
	}
	
	private void updateSave()
	{
		try {
			final int days = Integer.parseInt(etNumOofDays.getText().toString());
				final String month = actv.getEditableText().toString();
				final SQLiteDatabase mDb = new DBHelper(this.getApplicationContext()).getWritableDatabase();
				AsyncTask<String, Integer, Void> task = new AsyncTask<String, Integer, Void>() {

					@Override
					protected Void doInBackground(String... params) {
                        // If the month is not a valid month or wrong case will not update anything
						mDb.execSQL("UPDATE "+ DBHelper.TABLE_NAME + " SET days="+Integer.toString(days)+" where name='"+month.toString()+"';");
						try {
								Thread.sleep(100);
							} catch (Exception e) {
								// TODO: handle exception
							}
						return null;
					}
			
			
					@Override
					protected void onPostExecute(Void result) {
						Toast.makeText(UpdateActivity.this,R.string.updateSuccess, Toast.LENGTH_LONG).show();
						actv.setText("");
						etNumOofDays.setText("");
					}
				};
				task.execute();


		} catch (NumberFormatException e) {
			Log.e("Update", "the value days could not parse to int ");
			//Toast.makeText(this,R.string.invalid_input, Toast.LENGTH_LONG).show();
		}
		 
		
	}


    @Override
    public void onValidationSucceeded() {
        updateSave();
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        if (failedView instanceof EditText ) {
            failedView.requestFocus();
            ((EditText) failedView).setError(message);
        }
        else if (failedView instanceof AutoCompleteTextView){
            failedView.requestFocus();
            ((AutoCompleteTextView) failedView).setError(message);
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
