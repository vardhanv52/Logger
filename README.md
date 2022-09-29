# MyLogger

This library can be used to log all the APIs executed within the app along with the user interactions with the components. Please go through the below documentation to know about the library in detail.

### Integration

Integration can be done either through AAR file or through Jitpack URL.

##### Jitpack URL

Add the following code within your gradle files.
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
    implementation 'com.github.vardhanv52:Logger:$latest_version'
}
```
Check the releases section for the latest version details

##### AAR File

- Include the aar file within the libs folder of the project.
- Make sure that the dependencies used by the library are declared with the app level gradle file. _(The library dependencies are listed below.)_
- Include the following lines within the project dependencies list.

```
implementation fileTree(dir: 'libs', include: ['*.jar, *.aar'])
implementation files('libs/logger-release.aar')
```

### Library Dependencies

```
def roomVersion = "2.4.3"
def workVersion = "2.7.1"
implementation "androidx.room:room-runtime:$roomVersion"
kapt "androidx.room:room-compiler:$roomVersion"
implementation "androidx.work:work-runtime-ktx:$workVersion"
implementation 'androidx.core:core-ktx:1.7.0'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
implementation 'com.google.code.gson:gson:2.8.9'
implementation 'com.squareup.retrofit2:retrofit:2.8.1'
implementation platform('com.google.firebase:firebase-bom:30.4.1')
implementation 'com.google.firebase:firebase-firestore'
```

### Implementation

- Within the Application class of the app, initialise the library in the following way. The second argument LogOptions object is an optional property. But can be used to customise based on the requirements. The complete list of properties available for customisation are listed below.

```
MyLogger.launch(this, LogOptions().apply {
    apiCalls.terminalLogging = true
    messages.terminalLogging = true
    firebase.logsCollection = "library-logs"
    setLogsHistory(2)
    setSyncingInterval(20)
})
```

### Library Customisation

The entire customisation happens through the object of the LogOptions class. Please check the following table for the available properties.

| Property                    | DataType | Default Value  | Description                                                                                                                                                     |
| :-------------------------- | :------: | :------------: | :-------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| firebase.logsCollection     |  String  |      logs      | If provided, the logs will be stored within this collection.                                                                                                    |
| autoSyncing | Boolean | true | Auto syncing of logs to firestore will happen based upon this boolean value. |
| syncingInterval | Int | 15 | The values is in minutes. The logs will be synced with firestore periodically based on this interval. Have to use setSyncingInterval(Int) function to update the property |
| autoDeletion | Boolean | true | Auto deletion of logs from local db will happen based upon this boolean value. |
| logsHistory | Int | 1 | The values is in days. This number specifies how many days of logs does the library have to maintain in the local db at any time. Have to use setLogsHistory(Int) function to update the property |
| apiCalls.enabled            | Boolean  |      true      | If true, API calls will be logged as per the configuration otherwise no.                                                                                    |
| apiCalls.terminalLogging    | Boolean  |     false      | If true, API calls data will be printed in the terminal along with logging                                                                                  |
| apiCalls.dbLogging          | Boolean  |      true      | If true, the triggered API calls will be logged in the local database.                                                                                          |
| userActions.enabled         | Boolean  |      true      | If true, User interactions will be logged as per the configuration otherwise no.                                                                                |
| userActions.terminalLogging | Boolean  |     false      | If true, User interactions data will be printed in the terminal along with logging                                                                              |
| userActions.dbLogging       | Boolean  |      true      | If true, the User interactions will be logged in the local database.                                                                                            |
| messages.enabled            | Boolean  |      true      | If true, the developer will be able to log custom messages otherwise no.                                                                                        |
| messages.terminalLogging    | Boolean  |     false      | If true, the custom messages will be printed in the terminal along with logging                                                                                 |
| messages.dbLogging          | Boolean  |      true      | If true, the custom messages will be logged in the local database.                                                                                              |

### Appending Tags

Tags can be configured in the following way.
```
MyLogger.setTags(arrayListOf("OrderId", "User Email"))
```
By default, tags will be an empty array. If provided, all the logs will be tagged with these tags. Anything that helps in filtering the logs can be provided through tags.
The following functions are available to deal with the tags.

```
    fun setTags(tags: List<String>) - To set tags
    fun addTags(tags: List<String>) - To add tags to existing list
    fun clearTags() - To clear all the tags
    fun clearTags(list: List<String>) -  To clear specific tags
    fun getTags(): List<String> - To fetch existing tags
```

### Tracking Properties

|      Action      | Properties                                                   |
| :--------------: | :----------------------------------------------------------- |
|     API Call     | URL, Headers, Body, Response code, Response, Tags, Timestamp |
| User Interaction | Screen/Activity, View Id, Tags, Timestamp                    |
| Custom Messages  | Screen/Activity, Message, Tags, Timestamp                    |
In addition to the above properties, Device Manufacturer, Device Model and Device SDK details will also be logged for all the logs.

### Logging API Calls

This works only if the retrofit library is being used within the project. The library provides an interceptor which in return has to be added to the HttpClient object while configuring the Retrofit.

```
httpClient?.addInterceptor(MyLogger.getAPIInterceptor())
```

### Logging User Interaction

This can be done either by using the custom views or custom OnClick Listener provided by the library.
Currently, the library provides custom views for only TextView and Button.
Replace the native Button and Textview with _com.android.my_logger.views.MyButton_ and _com.android.my_logger.views.MyTextView_ respectively.
One more way to track the user clicks is through Custom OnClickListener.

```
val listener = object : MyOnClickListener() {
        override fun onUserClick(p0: View?) {
            Helper.showToast("Hello!")
        }
    }
binding.clickMeBtn.setOnClickListener(listener)
```

### Logging Custom Messages

```
MyLogger.log("MainActivity OnCreate")
```

The argument for the log function can be anything. If a string is not provided, the given data will be converted to a string through Gson and then will be logged.

### Export Database

```
Helper.showProgressDialog(context)
val status = MyLogger.exportDatabase()
Helper.showToast(
    if (status) "Exported successfully"
    else "Failed to Export!"
)
Helper.dismissProgressDialog()
```

The exportDatabase function returns a boolean value. if True, the local db is successfully exported to a CSV file otherwise the operation is failed. The exported csv file will be in the documents folder.
if the android SDK is less than 29, then the app has to declare the WRITE_EXTERNAL_STORAGE permission and has to fetch the runtime permission before exporting the DB. If it is >= 29, then no permissions are necessary. Within the CSV file, both the user interactions along with the API calls will be logged.

### Clear Database

```
MyLogger.clearDatabase()
```

Clears the entire local database.

### Syncing with Firebase

```
Helper.showProgressDialog(context)
val result = MyLogger.syncDatabase()
Helper.dismissProgressDialog()
when (result.code) {
    Codes.DATA_SYNC_DONE.toString() -> Helper.showToast("Synced!")
    Codes.DATA_SYNC_FAIL.toString() -> Helper.showToast("Synced failed!")
    Codes.NOTHING_TO_SYNC.toString() -> Helper.showToast("No new logs to sync!")
    Codes.DST_NOT_CONFIGURED.toString() -> Helper.showToast("Firebase not configured properly!")
}
```

The SyncDatabase function returns a dto _(LibResponseDTO)_. This object will have the following two properties.

| Property | Datatype |                           Possible Values                           | Description                                                                                                                                                                                                            |
| :------: | :------: | :-----------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|  status  | Boolean  |                            true or false                            | True means operation successfully done otherwise not                                                                                                                                                                   |
|   code   |  String  | DATA_SYNC_DONE, DATA_SYNC_FAIL, NOTHING_TO_SYNC, DST_NOT_CONFIGURED | <ul><li>DATA_SYNC_DONE - Successfully done.</li><li>DATA_SYNC_FAIL - Syncing failed.</li><li>NOTHING_TO_SYNC - No entries found for syncing</li><li>DST_NOT_CONFIGURED - Firebase is not configured properly</li></ul> |

Check the src code of the app module within the repo for sample implementation.
