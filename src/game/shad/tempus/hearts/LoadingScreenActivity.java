package game.shad.tempus.hearts;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LoadingScreenActivity extends Activity
{
	

	//creates a ViewSwitcher object, to switch between Views
	private ViewSwitcher viewSwitcher;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		//Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
    }

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends  AsyncTask{
    	//A TextView object and a ProgressBar object
    	private TextView tv_progress;
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(LoadingScreenActivity.this);
	        /* Initialize the loading screen with data from the 'loadingscreen.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher./*/
			viewSwitcher.addView(ViewSwitcher.inflate(LoadingScreenActivity.this, R.layout.startup, null));

			//Initialize the TextView and ProgressBar instances - IMPORTANT: call findViewById() from viewSwitcher.
			//tv_progress = (TextView) viewSwitcher.findViewById(R.id.);
			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar);
			//Sets the maximum value of the progress bar to 100
			pb_progressBar.setMax(100);

			//Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
		}

		//The code to be executed in a background thread.
		@Override
		protected Object doInBackground(Object... params) {
		
			/* This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This
			 * is where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			try
			{
				//Get the current thread's token
				synchronized (this)
				{
					//Initialize an integer (that will act as a counter) to zero
					int counter = 0;
					//While the counter is smaller than four
					while(counter <= 20)
					{
						//Wait 850 milliseconds
						this.wait(200);
						//Increment the counter
						counter++;
						//Set the current progress.
						//This value is going to be passed to the onProgressUpdate() method.
						publishProgress(counter*5);
					}
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		//Update the TextView and the progress at progress bar
		@Override
		 protected void onProgressUpdate(Object... values) {
			//Update the progress at the UI if progress value is smaller than 100
			if((Integer)values[0] <= 100)
			{
				tv_progress.setText("Progress: " + Integer.toString((Integer)values[0]) + "%");
				pb_progressBar.setProgress((Integer)values[0]);
			}
		}

		//After executing the code in the thread
		@Override
		protected void onPostExecute(Object objects)
		{
			/* Initialize the application's main interface from the 'main.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*
	         * */
			viewSwitcher.addView(ViewSwitcher.inflate(LoadingScreenActivity.this, R.layout.main, null));
			//Switch the Views
			viewSwitcher.showNext();
		}

    }

    //Override the default back key behavior
    @Override
    public void onBackPressed()
    {
    	//Emulate the progressDialog.setCancelable(false) behavior
    	//If the first view is being shown
    	if(viewSwitcher.getDisplayedChild() == 0)
    	{
    		//Do nothing
    		return;
    	}
    	else
    	{
    		//Finishes the current Activity
    		super.onBackPressed();
    	}
    }
   
}