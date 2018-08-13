Android UT and UI Test Basic 
============================
To know more info about this check my [Medium blog](https://medium.com/@surya.n1447)

Purpose of UI/UT in development
-------------------------------
The need of UI/UT thing in development

**Well-written tests, on the other hand, will tell you all of these things:**

How to access the API
* What data is supposed to go in and out
* What possible variations of an expected behavior exist
* What kind of exceptions might occur, and what happens if they do
* How individual parts of the system interact with others
* Examples of a working system configuration

Unit Testing Basics:
--------------------
    Unit testing is written for the Presenter, 
    ViewModel and Managers where the code logic is used and to test those functionalities
    of business logic and respective API calls.

    The basic format of the test that can be used to continuous Integration testing.

    This format remains same for both UI and UT test.

![Test flow](https://cdn-images-1.medium.com/max/800/1*f9KkaO8AhaXC3ac9Juua7w.jpeg)

```
class Test {

  @JvmField 
  @Rule
  protected var rule = RxAndroidSchedulersRule() // TestRule

  @BeforeClass
  fun setUpClass(){
    // run once before any of the tests
  }

  @Before
  fun setUp(){
       .... 
      // run before each test
  }

  @Test
  fun testCase(){
        .... 
        // respective validating the test
  }

  @After
  fun tearDown(){
        ....
       // run after each test
  }

  @AfterClass
  fun tearDownClass(){
      // run once after all tests
  }

}
```

Need of Mocking in Continuous Integration testing
-------------------------------------------------

    A unit test should test functionality in isolation. Side effects from other classes or the system should be eliminated for a unit test, if possible.

**This can be done via using test replacements (test doubles) for the real dependencies. Test doubles can be classified like the following:**

* A dummy object is passed around but never used, i.e., its methods are never called. Such an object can for example be used to fill the parameter list of a method.
* Fake objects have working implementations, but are usually simplified. For example, they use an in memory database and not a real database.
* A stub class is an partial implementation for an interface or class with the purpose of using an instance of this stub class during testing. Stubs usually don’t respond to anything outside what’s programmed in for the test. Stubs may also record information about calls.
* A mock object is a dummy implementation for an interface or a class in which you define the output of certain method calls. Mock objects are configured to perform a certain behavior during a test. They typically record the interaction with the system and tests can validate that.

Unit testing (UT) Output:
-------------------

![UT Test](https://cdn-images-1.medium.com/max/800/1*lBRCj2bcqnCNN5cl_7TGPg.gif)

Espresso UI Library
-------------------

Espresso is an instrumentation Testing framework made available by Google for the ease of UI Testing.

To know more about the Espresso library checkout the [Espresso](https://developer.android.com/training/testing/espresso/) link.

**Important things to note is that**

* The activity will be launched using the @Rule before test code begins
* By default the rule will be initialised and the activity will be launched(onCreate, onStart, onResume) before running every @Before method
* Activity will be Destroyed(onPause, onStop, onDestroy) after running the @After method which in turn is called after every @Test Method
* The activity’s launch can be postponed by setting the launchActivity to false in the constructor of ActivityTestRule ,in that case you will have to manually launch the activity before the tests

The Espresso test of a view contains
------------------------------------
![UI test flow](https://cdn-images-1.medium.com/max/800/1*0dk_cB_qRdglNlcnOraegQ.jpeg)

**Steps involved in espresso tests**

1. **Finding a View using a ViewMatcher**

VIew Matchers

Espresso uses onView (Matcher<View> viewMatcher) method to find a particular view among the View hierarchy. onView() method takes a Matcher as argument. Espresso provides a number of these ViewMatchers which can be found in this Espresso Cheat sheet.

Every UI element contains properties or attributes which can be used to find the element.Suppose for finding an element with

![VIEW MATCHER CHEAT SHEET](https://cdn-images-1.medium.com/max/600/1*xOYJ_0AZNaAZrAdbutcRVw.png)

We can write

`Espresso.onView(withId(R.id.login_button))`

2. **Performing actions on the View (View Actions)**

After finding the View you can perform actions on the View or on its Descendant using ViewActions of the Espresso.Some of the Most common actions are click(),clearText() etc.

  Note: In cases where the View isn’t directly visible such as in a case where the view is in a scroll view and isn’t you will have to perform the scrollTo() function first and then perform the action.

![View Action cheat sheet](https://cdn-images-1.medium.com/max/600/1*KLkQDb_6JF8dknK01YqCng.png)

`Espresso.onView(withId(R.id.login_button)).perform(click())`

3. **Checking the ViewAssertions**

After performing an action on the View we will want to see if the view behaves as we want, This can be done using check (ViewAssertion viewAssert)

![ViewAssertion cheat sheet](https://cdn-images-1.medium.com/max/600/1*55ZJunXlNtjKsEvNEAjEWQ.png)

`Espresso.onView(withId(R.id.login_result)).check(matches(withText(R.string.login_success)))`

    Note : The flow of UI test execution is random it will not run in the order of code written hierarchy.

Sometimes the test will ran to execution faster or fails because of animation so I recommend to turn off the animation and transition of the target module/device under test.

Toggle the below fields to Animation off for successful test execution,

**Settings -> Developer Options**

* **Window animation scale**
* **Transition animation scale**
* **Animation duration scale**

User Interface (UI) Test Output
-------------------------------
![Final output](https://im2.ezgif.com/tmp/ezgif-2-994ee44d2b.gif)
