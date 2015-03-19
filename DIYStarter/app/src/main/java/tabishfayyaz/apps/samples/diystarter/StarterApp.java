package tabishfayyaz.apps.samples.diystarter;

import android.app.Application;

import de.greenrobot.event.EventBus;
import tabishfayyaz.apps.samples.diystarter.manager.DataService;

/**
 *
 * This Android application is an example of how to implement a modern durable android REST client
 * utilizing some of the best open-source libraries available:
 *
 * http://square.github.io/retrofit/
 * https://github.com/greenrobot/EventBus
 * http://square.github.io/picasso/
 * http://square.github.io/okhttp/
 *
 * @see tabishfayyaz.apps.samples.diystarter.manager.DataService
 *
 * Created by tabish on 3/18/15.
 */

public class StarterApp extends Application
{
    private DataService dataService;

    @Override
    public void onCreate()
    {
        super.onCreate();
        dataService = new DataService();

        EventBus.getDefault().register(dataService);
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();

    }
}
