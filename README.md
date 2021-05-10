# NoteKeeper

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
And it often the only layout class needed. It's has a first-class design-time experience. 
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
It can be create **programmatically** 
- Using Java code to create class instance and 
- Where relationships and properties will be set in code.\
But actually, we mostly use layout files where
- XML files describe View hierarchy
- Usually created using the Android Studio UI Designer

But there is no implicit relationship between an activity and a layout.\
**setContentView**: method used by an activity to explicitly load the layout resource it wants to work on.\
Once the layout is loaded, the activity must request references to its views using the `findViewById` method. 
This last method require a parameter (layout file resource or item's id value) provided by the **R class**, a generated class. It contains nested classes.
The most useful are 
- **R.layout** to load the layout files. It owns an public int attribute for each layout in the project named as it
- **R.id** to load the layout resources. It owns an public int attribute for each view in the project named as it that give us back a reference to it.
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
the work of moving the data over and managing each of those layouts.\
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
The **AndroidManifest.xml** describe the application and its components. Its content start by the **application** element.\
Within the **application element**, we describe the components within the application.
Each Activity is presented by an **activity element**.

The one with the **intent-filter** is indicated to be the launcher activity and then causes 
an icon for it to appear in the application launcher. That's mean will have as a lot of 
icons for our application as a lot of launcher activities. But normally, we'll have only one like that.

The **label** of activities does a couple of things.
In case of the launcher activity, it affects the text that appears next to the launcher icon.
For other activities, it affects the text that appears at the top of the activity.

**String resources** allow storing string values separate from where they are referenced. 
Using them avoid us to directly insert our string value in the _manifest_.


## Operations with Intents
**Intents** describe a desired **operation** meaning the **target** like an **activity** but with 
sometimes, **additional information** provided by the **intent extras**.\
**Intent extras** are nothing more than name-value pairs added to the intent with the **putExtra** overloads.

An activity can access the intent that started it using the **getIntent** method that returns a reference to the intent.
This returned intent has a series of **getXXXExtra** methods that allow us to retrieve extras by their names.\
**E.g.:** 
- getIntExtra
- getStringExtra

Intent can travel outside of our process (application) and out to an activity in a different process. That the 
reason why intents must be cross-process friendly. That why we have limited allowable extras which are:
- Primitive types and String
- Arrays of supported types
- Some ArrayLists
- A few other special types.

But fundamentally, most reference types are not directly supported. They need to be **flattened** 
meaning **converted to a bunch of bytes** or make it _wire friendly_. 
This can be done using **Java serialization**. Although, serialization is very runtime expensive while very easy to implement.\
A better way to do so is using **Parcelable API** which is much more efficient than serialization but a bit more complicated to implement.
This is because we have to explicitly implement the behavior by these step:
- Implementing the Parcelable interface which has 2 methods:
    - **describeContents** used to indicate any special behaviors our parceling may require. It generally just return 0.
    - **writeToParcel** receives a **Parcel instance** and use **Parcel.writeXX** to store content from our object.

- Provide a public static final CREATOR field that must be of type **Parcelable.Creator** meaning 
that it is an implementation of the interface **Parcelable.Creator** that has
    - **createFromParcel** responsible to create a new instance of our type so it receive a reference to a 
Parcel instance and use the **Parcel.readXX** methods to access content and set the values inside of our type.
    - **newArray**: receive a size and is responsible to create an array of type of the specified size.     
    














