# DIYStarter

A durable Android REST based client built on the principle of Less Code, Better Quality using:

 - [Retrofit] (http://square.github.io/retrofit/)
 - [EventBus] (https://github.com/greenrobot/EventBus)
 - [Picasso] (http://square.github.io/picasso/)
 - [GSon] (https://code.google.com/p/google-gson/) - comes packaged within Retrofit
 - [OkHttp] (http://square.github.io/okhttp/)
 - [DIY.org REST API] (http://docs.diy.org/)


This is a starter application so the UI is primitive with basic error handling. The intent is not to show UI/UX excellency but code design and best practices

### Architecture

The project was built with Android Studio and studying the architecture and code of the project you'll be able to learn:

- Consuming a web API and displaying images fetched over the network in a List View
- Moving network request outside of the Activity lifecyle.
- Clear and safe communication between multiple independent component i.e. configuration changes (rotation, phone-call etc.) will not crash the app. We don't take a defensive programming approach or null checks spread out everywhere in the code. If you have done anything with Fragments you know what I am talking about.
- All Activities (or any similar component for that matter) registers on the Event Bus and use it to communicate.
- When you want to load data you post an event on the Event Bus which will cause the Data Service to start the network request.
- When network request finishes, Data Service will post the result back on the Event Bus for any listening Activites to handle.
- The UI is completely agnostic to who is the data provider meaning you can plug-play any type of UI. The source of data can be an in-memory cache or local database that syncs with a web API. 

### Study Guide

Make sure to have basic familiarity on how to use the open-source libraries linked above. Given you done that studying the project in following order will probably be more helpful:

1. [DIYApi.java] (https://github.com/tabishfayyaz/diy-starter/blob/master/DIYStarter/app/src/main/java/tabishfayyaz/apps/samples/diystarter/rest/DIYApi.java) communicates with the REST API utilizing RetroFit
2. [StarterApp.java] (https://github.com/tabishfayyaz/diy-starter/blob/master/DIYStarter/app/src/main/java/tabishfayyaz/apps/samples/diystarter/StarterApp.java) is where Data Service is initialized and registered on the Event Bus
3. [DataService.java] (https://github.com/tabishfayyaz/diy-starter/blob/master/DIYStarter/app/src/main/java/tabishfayyaz/apps/samples/diystarter/manager/DataService.java) is the pipe through which data travels back & forth between the UI layer and REST layer. The UI is completely agnostic to who is the data provider meaning you can plug-play any type of UI without changing anything in other packages
4. [SelectionActivity.java] (https://github.com/tabishfayyaz/diy-starter/blob/master/DIYStarter/app/src/main/java/tabishfayyaz/apps/samples/diystarter/ui/SelectionActivity.java) is the first view of the app and lets the user choose between seeing his projects or his followers projects
5. Continue studying the other Activities

#### Questions? 

Tweet me: [@tabishfayyaz](https://twitter.com/tabishfayyaz)

## License

DIYStarter is available under the MIT license. See the [LICENSE](https://github.com/tabishfayyaz/diy-starter/blob/master/LICENSE).
 
