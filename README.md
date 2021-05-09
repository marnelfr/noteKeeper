## NoteKeeper
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
According to the android documentation, an activity is a single, focused thing that a user can do.\
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

## Layout Classes
The activity UIs of an application need to be responsive because device display characteristics vary. So UI must adapt.
Absolute positioning would be limiting. For this purpose, we've got **Layout classes** that provide positioning flexibility. 
- They are responsible to arrange their child views which can also be other layout classes,
- The specific positioning behavior depends on the layout class we're dealing with.

### Common Layout Classes
- **FrameLayout:** provides a blocked-out area and generally has only one direct child.
- **ScrollView:** a derived class of FrameLayout that provides scrolling capability.
- **LinearLayout:** That provides horizontal or vertical arrangement. It can be distributed evenly but also support weighted distribution. 
We can even specify the among of space that a specific item will use and the remaining areas will be divided for others.
- **RelativeLayout:** provides relative positioning that can be relative to one another or to a parent. **It's not use anymore**.

#### New released Layout classes
- **ConstraintLayout:** it's a extremely flexible layout class. 
And it often the only layout class needed. It's has a first-class design-time experience. 
It closely integrated with the Android Studio Designer. It uses constraints leverage by its children.\
We can have 
    - relative size and positioning 
    - ratio-based size and positioning
    - Group size/position distribution known as chains.
    - do weighted relationships (e.g. a centered item but slightly to the left.)
    - guideline-based size/position where view elements are visible at runtime, but they are used to allow users to control positioning within screens.
    
When using it ach view should generally have 
    - at least one horizontal and vertical constraint otherwise, it ends up positioned at 0, 0
    - can set more than one of each (e.g. one on the left side and another on the right side in order to center the view)\
When setting constraints with the designer, views will have a box around them allowing us to interact with them easily within the designer.\
So setting constraints within the designer, 
    - we can drag circles at the midline of the box to create constraint relationship.
    - we can drag squares that that box has at its corners to set fixed sizing within the designer
 


















