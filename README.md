# NoteKeeper

**Avoid using magic value in your code**: always declare them as **constant**

When talking about views, the term attributes and the term properties are often used interchangeably.\
The terms Emulator, Android Virtual Device, and AVD all refer to the same thing.

Android studio provides a complete Android development environment\
There is layout files that are responsible for describing our user interface and they are distinctly separate from the code that manages the user experience.\
And that's where activities came in.

## API Level
Lower API levels are older and are therefore supported by more devices.\
Higher API levels are newer and therefore provides more features.\
The cumulative distribution percentage information shown on the helping screen when creating a new app is not up to date as information provided at [Android Dashboard](https://bit.ly/adroiddashboards)

## Activity
According to the android documentation, **an activity is a single, focused thing that a user can do**.\
> They serve as the place to present a User Interface (UI) 
>> Provide a window 
>> UI is built within that window using a series of classes derived from the base class View.

>They have a lifecycle
>> So they are really more than just a "screen"
>> And that lifecycle is represented in our program by a series of methods that get called  
>> We use the `onCreate` method to initialize an activity then the UI.

### Activity UI
**View:** it's the basic building block of our UI. They are responsible for drawing the UI and dealing with any event handling.\
There are many specialized classes available. 
**ViewGroup:** it's a special kind of view that holds other views.
**Layout:** a derived class of **ViewGroup**'s class used to holds other views. They are **special invisible ViewGroup**. 
They handle View positioning behavior. We've got many specialized classes available.
**fab**: floating action buttons


## Layout Classes
The activity UIs of an application need to be responsive because device display characteristics vary. So UI must adapt.
Absolute positioning would be limiting. For this purpose, we've got **Layout classes** that provide positioning flexibility. 
- They are responsible to arrange their child views which can also be other layout classes,
- The specific positioning behavior depends on the layout class we're dealing with.

### Layout properties
- **layout_with/_height** wrap_content (fixed with) or match_constraints (width based on constraints)

### Common Layout Classes
- **FrameLayout:** provides a blocked-out area and generally has only one direct child.
- **ScrollView:** a derived class of FrameLayout that provides scrolling capability.
- **LinearLayout:** That provides horizontal or vertical arrangement. It can be distributed evenly but also support weighted distribution. 
We can even specify the among of space that a specific item will use and the remaining areas will be divided for others.
- **RelativeLayout:** provides relative positioning that can be relative to one another or to a parent. **It's not use anymore**.

### New released Layout classes
- **ConstraintLayout:** it's a extremely flexible layout class. 
And it's often the only layout class needed. It's has a first-class design-time experience. 
It closely integrated with the Android Studio Designer. It uses constraints leverage by its children.
1. We can have 
    - relative size and positioning 
    - ratio-based size and positioning
    - Group size/position distribution known as chains.
    - do weighted relationships (e.g. a centered item but slightly to the left.)
    - guideline-based size/position where view elements are visible at runtime, but they are used to allow users to control positioning within screens.
1. When using it each view should generally have 
    - at least one horizontal and vertical constraint otherwise, it ends up positioned at 0, 0
    - but we can set more than one of each (e.g. one on the left side and another on the right side in order to center the view).
1. When setting constraints with the designer, views will have a box around them allowing us to interact with them easily within the designer.\
So setting constraints within the designer, 
    - we can drag circles at the midline of the box to create constraint relationship.
    - we can drag squares that that box has at its corners to set fixed sizing within the designer
 
## Activity/Layout Relationship
Activity UI can be create **programmatically** 
- Using Java code to create class instance and 
- Where relationships and properties will be set in code.\
But actually, we mostly use layout files where
- XML files describe View hierarchy
- Usually created using the Android Studio UI Designer

**But there is no implicit relationship between an activity and a layout.**\
That's why we've got:\
**setContentView**: method used by an activity to explicitly load the layout resource it wants to work on.\
Once the layout is loaded, the activity must request references to its views using the `findViewById` method. 
This last method require a parameter (layout file resource or item's id value) provided by the **R class**, 
a generated class. It contains nested classes. The most useful are 
- **R.layout** to load the layout files. It owns an public int attribute for each layout in the project named as it
- **R.id** to load the layout resources. It owns an public int attribute for each view in the project named as it that 
give us back a reference to it.
    - They return a **NullRefernceException** when the layout or id require doesn't exist.


## Spinners
They have two identities:
- The first part that shows the current selection,
- The second part, an area, that's opens up when user type on the spinner's triangle.

For this purpose, they do need to have layouts associated with them. 
- One used to format the current selection 
- Another one to format each of the available selections.

So populating a spinner require 3 tasks: getting data across and managing each of those layouts.\
That's where **Adapter** come out since they are responsible for doing 
the work of 
- moving the data over and 
- managing each of those layouts.

There are different kinds of adapters available: 
- Some manage in-memory data sources like arrays or lists
- Other manage database sources that use cursors.


## Activity Interaction
Android is a component-oriented platform and have a number of different types of component.
The most familiar are Activities. They are, like others component, distinct from one another. 
This means that an activity can't directly create another activity. To do so, 
- it has to create an intent;
- that intent will identify the desired activity using generally class' information for that activity;
- launch the activity matching the intent by calling the **startActivity** which receive the intent. 

E.g.: Passing from NoteListActivity to NoteActivity when the user click on a note will be like:
> The NoteListActivity will create an intent and call startActivity which will then start our NoteActivity. 

## The manifest
The **AndroidManifest.xml** describe the application and its components. Its content start by the **application element**.\
Within the **application element**, we describe the components within the application.
Each Activity is presented by an **activity element**.

The one with the **intent-filter** is indicated to be the launcher activity and then causes 
an icon for it to appear in the application launcher. That's mean will have as a lot of 
icons for our application as a lot of launcher activities. But normally, we should have only one like that.

The **label** of activities does a couple of things.
In case of the launcher activity, it affects the text that appears next to the launcher icon.
For other activities, it affects the text that appears at the top of the activity.

**String resources** allow storing string values separate from where they are referenced. 
Using them avoid us to directly insert our string value in the _manifest_.


## Operations with Intents (Explicit intent)
**Intents** describe a desired **operation** meaning the **target** like an **activity** but with 
sometimes, **additional information** provided by **intent extras**.\
**Intent extras** are nothing more than name-value pairs added to the intent with the **putExtra** overloads.

An activity can access the intent that started it using the **getIntent** method that returns a reference to the intent.
This returned intent has a series of **getXXXExtra** methods that allow us to retrieve extras by their names.\
**E.g.:** 
- getIntExtra
- getStringExtra

**Extras that are value-types require a second argument that provides a default value**

Intent can travel outside of our process (application) and out to an activity in a different process. That the 
reason why intents must be cross-process friendly. That why we have limited allowable extras which are:
- Primitive types and String
- Arrays of supported types
- Some ArrayLists
- A few other special types.

But fundamentally, most reference types are not directly supported. They need to be **flattened** 
meaning **converted to a bunch of bytes** or make it _wire friendly_. 
This can be done using **Java serialization**. Although, serialization is very runtime expensive while very easy to implement.

A better way to do so is using **Parcelable API** which is much more efficient than serialization 
but a bit more complicated to implement. This is because we have to explicitly implement the behavior by these steps:
1. The class must implement the **Parcelable** interface which has 2 methods:
    - **describeContents** which is used to indicate any special behaviors our parceling may require. It generally just return 0.
    - **writeToParcel** receives a **Parcel instance** and use **Parcel.writeXX** to store content from our object.\
    **E.g.**: 
        - parcel.writeString(stringVariable);
        - parcel.writeParcelable(referenceVariable, 0);
        - parcel.writeByte((byte)(booleanVariable ? 1 : 0));
        - parcel.writeTypedList(listVariable);

- Provide a public static final CREATOR field that must be of type **Parcelable.Creator<OurClass>** meaning 
that it is an implementation of the interface **Parcelable.Creator** that has two methods:
    - **newArray**: receive a size and is responsible to create an array of type of the specified size.\
Generally, this is done using an anonymous  class.
    - **createFromParcel** responsible to create a new instance of our type so it receive a reference to a 
Parcel instance and use the **Parcel.readXX** methods to access content and set the values inside of our type.
Here, we use a private constructor of our class to implement this method.\
    **E.g.**:
        - stringVariable = parcel.readString();
        - referenceVariable = parcel.readParcelable(ReferenceClass.class.getClassLoader());
        - booleanVariable = source.readByte() == 1;
        - listOfReferenceVariable = new ArrayList<>(); source.readTypedList(listOfReferenceVariable, ReferenceClass.CREATOR);


**Parcel values must be accessed in the same order they were written** because values written in a Parcel have no identify but an order.\
**A class loader provides information on how to create instances of a type**
**When making a type parcelable, all contained reference types must also be parcelable**

## Application Activity Relationship
Android is component-platform based.
But those components (activities) run in process and we've got one process per application. 
So when the user start an application, its process it started first and the activity is started within it.
And any other activity require by the app will be started in the same process. Once there is no more activity running,
the process itself will exist. 

Now, in the same process, there is no always the need to use parcelable to send info cross activity if those 
activity can access the same DataManager (Unless I didn't get it well :').)


## Implicit intent (Late-binding Components)
Implicit intent allow us to describe an operation desired that may not be defined in our application.
Here the target is implied based on a set of characteristics and the specific intent that's going to be
used is not determined until the runtime: _we're talking about **late-binding**_.\
This is possible thank to **AndroidManifest** provide by every application installed in the android system.
There are loaded up in the system and then, using them, the system identify the best match. So
the specific match depend on apps installed on the user's device. In cases where there's a tie, 
the system prompts user.\
Implicit intents decouple senders and receivers even if those two don't know about each other.

### Implicit intent characteristics
- **Action**: a string that identify the kind of activity we want to start. It's commonly set int the 
intent constructor even if there is a setAction method available.\
**Action** is the only required characteristic of an implicit intent.

- **Category**: provides extended qualification and is not normally used by the sender but the intent
receiver.

- **Data**: provides the URI of the data to be acted upon. There are URIs for contact data,...\
It's set through the `Intent.setData` method.

- **Mime type**: provides to identify the kind of data associated with that URI. There a common or
app-specific mine type.\
It's set through the `Intent.setType` method.\
**E.g.**: **message/rfc2822** is a standard Internet mime type for sending email.


`Intent.setDataAndType` is used in case we need to specify both of them since **setData** and **setType** 
cancel each other.

The most **Common Intents** can be found at [http://bit.ly/commonintents](http://bit.ly/commonintents)


## Activities with Results
Some activity classes return results.\
This is the case, for example, of the **Camera Activity** that
- presents camera functionality
- and returns image thumbnail

or the case of the **Contact Activity** that 
- presents contact UI
- and returns selected contact info.

Those activities are started differently than others using `startActivityForResult` method that receive:
- Intent
- App defined integer identifier to differentiates results within our app since with can start a couple of 
activities in the same time.

**Receiving results** it make by overriding the **onActivityResult** of your Activity class.\
It get back three parameters:
- **An integer** we set as the second parameter to _startActivityForResult_
- **The result code** where **RESULT_OK** indicates success
- **An intent** containing the activity from the other app result.

**E.g.**: using the camera activity
The intent action is **MediaStore.ACTION_IMAGE_CAPTURE**.\
It takes the extra **MediaStore.EXTRA_OUTPUT** passed as url that specify 
the file in which to save the full quality image 


## Task
**A task is a collection of activities that users interact with when performing a certain job**
- It's managed as a stack known as the back stack.
- Activities added going forward.
- Back button removes Activities. 
    - That causes activity to be destroyed then remove from the task
    - The system will then manage the process lifetime (meaning the process will be ended if there is no another activity running on it).
    
    

## Data persistence
When it comes to managing persistent state, Android tends to use what is called an **edit-in-place** model. That
means that changes are saved with no special action but just when the user leave the activity by hitting  the back button.
Then we just handle a the onPause method of our activity. 

When handling new entries, we create that new entry in our activity's **onCreate** method.

**finish()** is used to stop our activity. This run the **onPause** method before leaving.













In intellij, when a method return a value, we can hit alt+v and the ask for new local variable introduction

