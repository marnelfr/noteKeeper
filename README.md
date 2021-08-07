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


## Activity lifecycle
Common causes of Activity destruction
- Leaving with the back button
- Calling finish method
- System initiated that happens:
    - generally to reclaim resources
    - for prolonged period in the background
    - when the system is experiencing resource pressure - as the currently active activity is asking for more resource.\
    This can even lead to the destruction of entire process running in the background.
    
## Activity lifecycle state
Our application can be either on
- **Foreground lifetime**: the one the user is interact with
- **Visible lifetime**: not longer in foreground but still visible by the user
- **Total lifetime**: not visible at all by the user so in kind of background
There are methods for start/end of each lifetime


## Activity lifecycle method
In the basic order, we have:
- Creator's method:
    - **onCreate**: the first method called **only** when the **activity is launched** (first created), it put the activity in **Total lifetime**
    - **onStart**: is called to make the activity **visible**
    - **onResume**: called to bring the activity on **foreground**. The **activity is then running**, the one the user is interacting with.

- Destroyer's method:
    - **onPause**: called when the activity's state goes from **foreground** to **visible**. 
    At this state, the **onResume** must be rerun before the activity come back to foreground.
    - **onStop**: called when the activity's state go back from **visible** to the **Total lifetime** state. 
    From this state, to bring the activity back in foreground,
        - the **onRestart** method is run first,
        - then the **onStart** method is run
        - before the **onResume** method.
    - **onDestroy**: the last method called before the activity get destroyed. The **Activity is then shut down**.

Those are lifecycle methods. We only override those we need among them.

It's always a good idea to save the **default value** receive by the activity just in case the user cancel changes.
But it's not a bad idea to save changes in the **onPause** method and reset values to the default one when the user cancel
because by the time the app reach the **visible state**, the user may leave the application. Changes should be lost then.


## Activity State Management
Sometimes, when the system is facing resource pressure, it may destroy automatically our activities in Total lifetime state.
But these activities must be restored when the user come back to them at the same state.
For this purpose, we have some mechanism for any instance state associated with an activity to be preserved 
at these times. So **activities provide by the state management,**
- An opportunity to save its state before being destroyed
- and the way to get back the saved state on restore.

## onSaveInstanceState
For **saving state**, we use the **onSaveInstanceState** method and write our activity state to the passed bundle
into the method.

When **restoring state**, the saved bundle is passed into our **onCreate** method. 
But when called initially, that bundle is null.\
Although, **the intent that created the activity remains available on restore**.

So onSaveInstanceState save the application state **when the activity is destroyed due the a switch to 
another application by the user**.\
**But what about when the application is fully destroyed and then recreated ?**\
We could use persistent store to save the state of the app but this is expensive when it comes to 
case like simple configuration change (when switching for a new screen orientation).


### ViewModel
For maintaining state across configuration changes, we use **ViewModel** that stores activity state in-process
separately from the activity. In other words, **ViewModel maintains state when activity 
get destroyed due to configuration changes** such as when the user rotates a device from portrait to landscape.
To set up a viewModel to our activity, we create on that extends **ViewModel** and we customize it 
by adding properties and methods specific to our activity's state requirements.\

And since **viewModels** are managed separately from our activity, they are accessed through
**ViewModelProvider**. It manages the VewModel instances:
- creates new instance of the viewModel when needed and provide it
- retrieves back the existing when available.  

?? **Names for the stored instance state values are app defined** 
















In AS, when a method return a value, we can hit alt+v and the ask for new local variable introduction
Let's take this a little further




























Kotlin is a java better than java.\
It comes with a set of librairy such as the kotlin-runtime.jar needed to run the java code generated from the kotlin code.
In kotlin, we can use function but also classes.

To declare a variable we use **var**\
To declare a constant, we use **val**\
The type of the variable is specify after the variable name separated by a colon (:)\
`var name:String = "Marnel"`\
There is no semi-colon in kotlin.


In kotlin, if is an expression. That mean it return value:
var status = if (user.online) {
"En ligne"
} else {
"Hors ligne"
}

Kotlin don't have switch but when:
when(value){
value1 -> println("this is $value1")
value2 -> println("this is $value2")
is Double -> println("the $value is of type Double")
else -> println("None $value1 and $value2 is not provided")
}

Kotlin try-catch is also an expression
var age = try{
Integer.parseInt(ageStr)
} catch(e:NumberFormatException) {
0
}

Unless a variable is clearly defined as nullable, there is any chance it could be. To declare a variable as nullable,
we use the question mark: var name:String? = "Marnel"

Kotlin allow variable interpolation in string:
var sentence = "The user $name is not currently online."
var alert = "The user $name will be ${age+1} the next year."

While defining classes, we can directly define the constructor value next to the class name:
class Person(var FirstName: String, var LastName: String){
}

While instanciating a class, there is not "new" keyword:
var person:Person? = Person("Marnel", "Gnacadja")

Since person is a nullable variable, we can check if it's null while accessing its attributes:
if(person?.FirstName == "Marnel"){ println("Salut Marnel") }

In kotlin, we have the while and do-while loop.
The for loop is a little different from other languages:
for(i in 1..10){
#go from 1 to 10 included
}

for (i in 1..20 step 2){
#go from 1 to 20 by step 2
}

for (i in 1 until 20){
#go from 1 to 19
}

for (i in 20 downTo 1){
#go from 20 to 1
}
for ((index, value) in listOf(1,2,3,4).withIndexes()){
# here, at the index = 0, value = 1
}

var eleves = TreeMap<String, Int>
eleves["Marnel"] = 15
for((name, avrege) in eleves){
#we can have more than 1 value in eleves
}


## Functions
Function are introduce with the keyword **fun**, can have parameter (even named parameters) and a return type.
A function that doesn't return anything has the return type **Unit**.

We have also **function expression** that can be defined in one line:
````kotlin
fun max(a: Int, b: Int): Int = if(a<b) b else a
````


## Calling from Java
When converting a kotlin code to Java, the compiler create a class named base on the file name.
For exemple, the content of the file Util.kt will be under an auto generated class named **UtilKt**.
Then top level function are created inside that class as static methods.
While compiling, classes created in the same file are sent in separete file.
It's possible to provide the name for the generated class using an annotation at the top of the file:\
@file:JvmName("ClassName")
In this case, to call a method _display_ from the ClassName (in the rsk package), we will have to:
````java
import rsk.*;
public class App{
    public static void main(String[] args) {
        ClassName.display("Hello word");
    }
}
````

## Default Parameters
Here, b has a default value
````kotlin
@JvmOverloads
fun max(a: Int, b: Int = 0): Int = if(a<b) b else a
````
Since Java don't have the concept of default value for the parameter, adding the annotation `@JvmOverloads`
make the compiler overload the function. In this case, we'll have two static methods in java:
- One with two parameter
- One with one parameter **a** where a constant **b** will be set to 0 inside the method.

We also have **named parameters**. When named one parameter, we've got to name all of them while calling the function:
````kotlin
println(max(a = 5, b = 7))
````

## Extension functions
While other function are called **helper functions**, we have these one too.
Here, we can add functions to class we didn't write. They are then added as static functions. So
they don't have access to the internals of the class.
In an extensional function, the instance on which the function will be called is accessed though **this**

````kotlin
fun main() {
	val text = "With     multiple    \t  whitespace"
	println(text.replaceMultipleWhiteSpace())
}

fun String.replaceMultipleWhiteSpace(): String {
    var regex = Regex("\\s+")
	return regex.replace(this, " ")
}
````


## Infix Functions
It's either a member or extension functions, has a single parameter, and marked with the **infix** keyword.
Using this make our functions look like _operator_
````kotlin
var d = 5.5 plus 6.7

infix fun Double.plus(a: Double): Double = this+a
````

Infix function lead us on to operator overloading (get from C++)
But in kotlin, only a set of unary operators can be overloaded.
````kotlin
fun main(args: Array<String>) {
	val h1 = Header("h1")
	val h2 = Header("h2")
	val h3 = h1 + h2
}

class Header(var Name: String) {}

infix operator fun Header.plus(h: Header): Header {
	return Header(this.Name + " " + h.Name)
}
````

Here, we can then use the **+** in the place of using **plus**
This is very useful if we are building **domain specific languages (DSLs) which is one thing kotlin is very good at.



## Tail Recursive Functions
In certain scenarios, when we want to use recursion, making the function **tail recursive** is extremely useful,
and it's something Kotlin supports out of the box.
````kotlin
import java.math.BigIntegerimport
fun main() {
    println(fib(10000, BigInteger("1"), BigInteger("0")))
}

tailrec fun fib(n: Int, a: BigInteger, b: BigInteger): BigInteger {
    return if (n == 0) b else fib(n-1, a+b, a)
}
````


## Interfaces
Here, we don't have **implements** and **extends** keyword. They are stood by **colon**.
Interfaces support as in Java 8 default implementation of methods on the interface.
This allows us to evolve interfaces without breaking existing code.
````kotlin
interface Time {
    fun setTime(hours: Int, mins: Int = 0, secs: Int = 0)
    fun setTime(time: NelTime) = setTime(time.Hours) //default implementation
}

interface EndOfTheWorld {
    fun setTime(time: NelTime) {} // implement the same method as Time interface
}

class NelTime {
    var Hours: Int = 0
}

// there is not implements or extends keyword in kotlin. Only ":" for them both
class AfricaTime: Time, EndOfTheWorld {
    
	// overridden methods must be clearly specify by the "override" keyword
	override fun setTime(time: NelTime){
		//At least one of them must be called
		super<EndOfTheWorld>.setTime(time)
		super<Time>.setTime(time)
	}
	
    override fun setTime(hours: Int, mins: Int, secs: Int){}
    
}
````


## Class
Here classes are public and final by default. So they can't be inherent.\
Kotlin also support **abstract** classes.\
We also have **sealed classes**. These classes can only have a fixed set of derivations.
It's like enums on steroids.\
Classes can have multiple constructors.\
Here classes are public and final by default. So to inherit a class, it should be made open or abstract
Methods in classes should also be open in order to be overridden. Whilst abstract methods must be overridden,
or the class that inherit our open class must be set as abstract also.\
There is no **package-private** like in Java. Here packages are just a way of collecting things together.\
However, kotlin have the idea of **internal** that makes the classes visible to everything else within the
class' module. The module in this case could be an IntelliJ module. It's basically a compilation of all the things
that are compiled together.
We also have **protected** and **private** here also.
````kotlin
open class Person {
	var First: String = ""
	var Last: String = ""
	abstract fun getName(): String
}

class Student : Person() {
	override fun getName(): String = "$First $Last"
}
````



## Sealed classes
It let us restrict a class hierarchy. We can specify exactly the set of derived classes from an abstract class.
So a sealed classe is an abstract class. But anything outside the sealed class hierarchy is not allowed to derive from it.
That gives us very fine control over what the sealed class can exist.
````kotlin
sealed class Mail {
	var time: String

	fun getTime() {
		return time
	}
	
    class SendMail(id: Int, to: String): Mail()
	class ReceiveMail(id: Int, from: String): Mail() {
	    var receivedAt: String
	    
	    fun getData(): String {
	        return id + from + receivedAt
	    }
	} 
}

fun handleEvent(e:Mail) = when(e) {
    is Mail.SendMail -> print(e.to)
	is Mail.ReceiveMail -> print(e.from)
	// no need to have an else because we do know exactly what all the derived classes are.
	// so we can handle each of the classes as an explicit case.
}
````


## Constructors
The default/primary/main constructor is defined as part of the class definition
by listing its parameter directly after the class name.
````kotlin
class Person(val id: Int, var name: String) {}
````

We can also use the **init** function
````kotlin
class Person(_name: String) {
    val name: String
    init {
        this.name = _name
    }
}
````
But here, we could just simply join the declaration with the assignment
without using **init**. But while using the init function,
we can do a lot of thing such of initializing variable, calling methods,...

When listing the default constructor parameters, without saying that they are
var or val, Kotlin doesn't automatically create property within the class to those
parameters: **we then have to do it manually**

We can also declare a second constructor inside the class using the **constructor**
keyword.

````kotlin
class Person(var name: String) {
	var age: Int = 0
	constructor(_name: String, _age: Int) : this(_name) {
	    this.age = _age
	}
}
````

However, it better to use default value than second constructor
````kotlin
class Person(var name: String, _age = 0){} //inferring the age's type to be an int
````

If we have derivation hierarchy, classes can call superclass constructors.
````kotlin
//first example using primary constructor
class Student(name:String): Person(name) {
    //no need to declare var name here. Already declared in the Person class.
}

//second example using secondary constructor
class Student(_name:String): Person {
    constructor(_name:String): super(_name)
}
````
For derivation, if a class has a default constructor,
we have to call that defautl constructor explicitly
````kotlin
class Student: Person() {}
````

**Private constructors** are also supported.\
It's used typically to inhibit construction, be able to create
an instance of the class differently from within the class.\
For example, if we want a singleton instance of our class, we can have a
private constructor and a factory object that will be used to create the class.\
**However, in kotlin, there is usually a better way to do so**


## Data classes
They are declared using the **data** keyword while declaring the class.
It allows us to store correctly the class in collections by providing
a correct implementation of the **equals** and **hashCode** methods.
It also overrides the **toString** method (for getting some sensible
return from that method rather than just the address of the class).
And it allows us to store data in the class for thing like data transfer objects for
example that are stored in collections. Reason why we need to provide an **equals** and **hashCode**
methods.

So if we mark a class as a data class, that class provides an **equals**, **hashCode**, **copy**, and
**toString** method. So we won't need to implement them.
Also, these classes are **immutable**.
````kotlin
data class Meeting(val name:String, val location:String)

val aMeeting = Meeting("A Meeting", "Porto-Novo")
val anotherMeeting = aMeeting.copy(location = "Cotonou")
if(aMeeting == anotherMeeting) { //work perfectly because of the equals method provided
    print(aMeeting) //work perfectly because of the toString method provided.
}
````








